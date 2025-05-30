package com.juaracoding.smartpro_rest_api.util;

import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestCapture {
    public static String allRequest(WebRequest webRequest)
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return processingData(request);
    }

    public static String allRequest(HttpServletRequest request)
    {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        return processingData(requestWrapper);
    }

    private static String processingData(HttpServletRequest request){
        String headerName = "";
        String paramName = "";
        Map<String, Object> reqBody = new HashMap<>();
        Map<String, Object> reqParam = new HashMap<>();
        Map<String, Object> reqHeader = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            headerName = headerNames.nextElement();
            reqHeader.put(headerName,request.getHeader(headerName));
        }

        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()){
            paramName = params.nextElement();
            reqParam.put(paramName,request.getParameter(paramName));
        }
        reqBody.put("authType", request.getAuthType());
        reqBody.put("method", request.getMethod());
        reqBody.put("serverName", request.getServerName());
        reqBody.put("session", request.getSession());
        reqBody.put("queryString", request.getQueryString());
        reqBody.put("remoteAddr", request.getRemoteAddr());
        reqBody.put("requestSessionId", request.getRequestedSessionId());
        reqBody.put("serverPort", request.getServerPort());
        reqBody.put("pathInfo", request.getPathInfo());
        reqBody.put("remoteHost", request.getRemoteHost());
        reqBody.put("locale", request.getLocale());
        reqBody.put("principal", request.getUserPrincipal());
        reqBody.put("isSecure", request.isSecure());
        reqBody.put("reqHeader", reqHeader);
        reqBody.put("reqParam", reqParam);
        try {
            System.out.println("Request Get Method : " + request.getMethod());
            if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod()) || "PATCH".equalsIgnoreCase(request.getMethod()))
            {
                reqBody.put("reqBody", readInputString(request));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String strValue = new JSONObject(reqBody).toString();
        return strValue;

    }

    private static String readInputString(HttpServletRequest request)
    {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        String requestBody;

        try {
            reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            requestBody = sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return requestBody;
    }
}
