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

import com.salespoint.api.exceptions.customs.user.InvalidPasswordLengthException;
import com.salespoint.api.exceptions.customs.user.InvalidUsernameLengthException;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    private int ACCEPTABLE_USERNAME_LENGTH = 3;
    private int ACCEPTABLE_PASSWORD_LENGTH = 8;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {

        loginUserDto.setUsername(loginUserDto.getUsername().trim());
        loginUserDto.setPassword(loginUserDto.getPassword().trim());

        if (loginUserDto.getUsername().length() < ACCEPTABLE_USERNAME_LENGTH) {
            throw new InvalidUsernameLengthException("Username's length is less than " + ACCEPTABLE_USERNAME_LENGTH);
        }

        if (loginUserDto.getPassword().length() < ACCEPTABLE_PASSWORD_LENGTH) {
            throw new InvalidPasswordLengthException("Password's length is less than " + ACCEPTABLE_PASSWORD_LENGTH);
        }

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
