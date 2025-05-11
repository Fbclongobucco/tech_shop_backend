package com.buccodev.tech_shop.config;

import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.repository.UserSystemRepository;
import com.buccodev.tech_shop.utils.dtos.login_dto.LoginResponseDto;
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
        String username = authentication.getName();

        if (customerRepository.findByEmail(username).isPresent()) {
            response.setContentType("application/json");

            var loginResponseDto = new LoginResponseDto(username);

            response.getWriter().write(
                    objectMapper.writeValueAsString(loginResponseDto)
            );
            return;
        }

        if (userSystemRepository.findByEmail(username).isPresent()) {
            response.setContentType("application/json");
            var loginResponseDto = new LoginResponseDto(username);

            response.getWriter().write(
                    objectMapper.writeValueAsString(loginResponseDto)
            );
            return;
        }
        response.sendRedirect("/tech-shop/login?error");

    }
}