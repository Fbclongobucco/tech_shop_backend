package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.entities.Customer;
import com.buccodev.tech_shop.entities.Roles;
import com.buccodev.tech_shop.exceptions.CredentialInvalidException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.security.TokenService;
import com.buccodev.tech_shop.utils.dtos.login_dto.LoginResponseDto;
import com.buccodev.tech_shop.utils.dtos.login_dto.UserLoggedDto;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class GoogleTokenVerifierService {

    private final GoogleIdTokenVerifier verifier;
    private final TokenService tokenService;
    private final CustomerRepository customerRepository;

    public GoogleTokenVerifierService(TokenService tokenService, CustomerRepository customerRepository) {
        this.tokenService = tokenService;
        this.customerRepository = customerRepository;
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(List.of("908996494082-7597olnu6s7njrkpk2e6jv4mo4p4qmki.apps.googleusercontent.com"))
                .build();
    }

    public LoginResponseDto login(Map<String, String> request) {
        String idToken = request.get("idToken");

        GoogleIdToken.Payload payload = this.verify(idToken);
        if (payload == null) {
            throw new CredentialInvalidException("Invalid Google token");
        }
        String email = payload.getEmail();

        Customer customer = customerRepository.findByEmail(email)
                .orElseGet(() -> createNewCustomerGoogle(payload));

        String jwt = tokenService.generateToken(customer);

        var UserLogged = new UserLoggedDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getRole());

        return new LoginResponseDto(UserLogged, jwt);

    }

    private  GoogleIdToken.Payload verify(String idTokenString) {
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                return idToken.getPayload();
            }
        } catch (Exception e) {
            throw new CredentialInvalidException("Invalid Google token");
        }
        return null;
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
