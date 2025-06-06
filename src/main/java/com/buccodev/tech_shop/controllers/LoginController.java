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
    private final GoogleTokenVerifierService googleTokenVerifier;
    private final CustomerRepository customerRepository;
    private final TokenService tokenService;

    public LoginController(AuthenticationService authenticationService,
                           GoogleTokenVerifierService googleTokenVerifier,
                           CustomerRepository customerRepository,
                           TokenService tokenService) {
        this.authenticationService = authenticationService;
        this.googleTokenVerifier = googleTokenVerifier;
        this.customerRepository = customerRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        var loginResponseDto = authenticationService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> request) {
        String idToken = request.get("idToken");

        GoogleIdToken.Payload payload = googleTokenVerifier.verify(idToken);
        if (payload == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        String email = payload.getEmail();


        Customer customer = customerRepository.findByEmail(email)
                .orElseGet(() -> createNewCustomerGoogle(payload));

        String jwt = tokenService.generateToken(customer);

        var UserLogged = new UserLoggedDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getRole());

        LoginResponseDto responseDto = new LoginResponseDto(UserLogged, jwt);

        return ResponseEntity.ok(responseDto);
    }

    private Customer createNewCustomerGoogle(GoogleIdToken.Payload payload) {
        Customer customer = new Customer();
        customer.setEmail(payload.getEmail());
        customer.setName((String) payload.get("name"));
        customer.setCreatedAt(LocalDateTime.now());
        customer.setPhone("00000000000");
        customer.setPassword("password123");
        customer.setRole(Roles.CUSTOMER);
        return customerRepository.save(customer);
    }
}
