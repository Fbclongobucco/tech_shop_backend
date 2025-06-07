package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.entities.Customer;
import com.buccodev.tech_shop.entities.Roles;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.security.AuthenticationService;
import com.buccodev.tech_shop.security.TokenService;
import com.buccodev.tech_shop.services.GoogleTokenVerifierService;
import com.buccodev.tech_shop.utils.dtos.login_dto.LoginRequestDto;
import com.buccodev.tech_shop.utils.dtos.login_dto.LoginResponseDto;
import com.buccodev.tech_shop.utils.dtos.login_dto.UserLoggedDto;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationService authenticationService;
    private final GoogleTokenVerifierService googleTokenVerifierService;


    public LoginController(AuthenticationService authenticationService,
                           GoogleTokenVerifierService googleTokenVerifierService
                           ) {
        this.authenticationService = authenticationService;

        this.googleTokenVerifierService = googleTokenVerifierService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        var loginResponseDto = authenticationService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> request) {

        var responseDto = googleTokenVerifierService.login(request);

        return ResponseEntity.ok(responseDto);
    }

}
