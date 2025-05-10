package com.buccodev.tech_shop.config;

import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.repository.UserSystemRepository;
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


    public CustomLoginSuccessHandler(CustomerRepository customerRepository, UserSystemRepository userSystemRepository) {
        this.customerRepository = customerRepository;
        this.userSystemRepository = userSystemRepository;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();

        if (customerRepository.findByEmail(username).isPresent()) {
            response.setContentType("application/json");
            response.getWriter().write(
                    String.format("{\"message\": \"User %s logged in\"}", username)
            );
            return;
        }

        if (userSystemRepository.findByEmail(username).isPresent()) {
            response.setContentType("application/json");
            response.getWriter().write(
                    String.format("{\"message\": \"System user %s logged in\"}", username)
            );
            return;
        }
        response.sendRedirect("/login?error");

    }
}