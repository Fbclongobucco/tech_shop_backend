package com.buccodev.tech_shop.config;

import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.repository.UserSystemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final CustomerRepository customerRepository;
    private final UserSystemRepository userSystemRepository;
    private final ObjectMapper objectMapper;


    public CustomLoginSuccessHandler(CustomerRepository customerRepository, UserSystemRepository userSystemRepository, ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.userSystemRepository = userSystemRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setContentType("application/json");

        String username = authentication.getName();

        boolean isCustomer = customerRepository.findByEmail(username).isPresent();
        boolean isSystemUser = userSystemRepository.findByEmail(username).isPresent();

        if (isCustomer || isSystemUser) {
            Map<String, String> jsonResponse = new HashMap<>();
            jsonResponse.put("emailLogged", username);

            response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
            return;
        }

        response.sendRedirect("/login?error");

    }
}