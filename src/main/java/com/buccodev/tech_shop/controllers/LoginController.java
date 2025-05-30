package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.security.AuthenticationService;
import com.buccodev.tech_shop.utils.dtos.login_dto.LoginRequestDto;
import com.buccodev.tech_shop.utils.dtos.login_dto.LoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        var loginResponseDto = authenticationService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }
}