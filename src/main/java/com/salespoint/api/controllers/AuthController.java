package com.salespoint.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salespoint.api.dtos.LoginUserDto;
import com.salespoint.api.entities.UserEntity;
import com.salespoint.api.security.jwt.JwtUtils;
import com.salespoint.api.services.UserService;
import com.salespoint.api.utils.response.LoginResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        UserEntity authenticatedUser = userService.authenticate(loginUserDto);

        String jwtToken = jwtUtils.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse
                .builder()
                .token(jwtToken)
                .expiresIn(jwtUtils.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }

}
