package com.juaracoding.smartpro_rest_api.security;

import com.juaracoding.smartpro_rest_api.config.JwtConfig;
import com.juaracoding.smartpro_rest_api.service.AuthService;
import com.juaracoding.smartpro_rest_api.util.LoggingFile;
import com.juaracoding.smartpro_rest_api.util.RequestCapture;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/***
 * Author: Michael, 2025-05-30
 * Last updated: 2025-06-01
 */

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthService authService;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/auth/");
     }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        authorization = authorization == null ? "" : authorization;
        String token = "";
        String username = "";

        try {
            if (!"".equals(authorization) && authorization.startsWith("Bearer ") && authorization.length() > 7) {
                token = authorization.substring(7);
                if (JwtConfig.getTokenEncryptEnable().equals("y")) {
                    token = Crypto.performDecrypt(token);
                }

                username = jwtUtility.getUsernameFromToken(token);
                String strContentType = request.getContentType() == null ? "" : request.getContentType();

                if (!strContentType.startsWith("multipart/form-data") || "".equals(strContentType)) {
                    request = new MyHttpServletRequestWrapper(request);
                }

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtility.validateToken(token)) {
                        UserDetails userDetails = authService.loadUserByUsername(username);

                        /** persiapan konteks permission / izin / hak aksesnya saat didorong ke controller nantinya */
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }
        }
        catch (Exception e) {
            LoggingFile.logException("JwtFilter", "doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) " + RequestCapture.allRequest(request), e);
        }

        filterChain.doFilter(request, response);
    }
}
