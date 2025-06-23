package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.dto.validation.LoginDTO;
import com.juaracoding.smartpro_rest_api.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * Author: Michael, 2025-05-31
 */

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        return authService.login(authService.parseToModel(loginDTO), request);
    }
}
