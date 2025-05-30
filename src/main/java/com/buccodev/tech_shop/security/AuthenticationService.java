package com.buccodev.tech_shop.security;

import com.buccodev.tech_shop.entities.Customer;
import com.buccodev.tech_shop.entities.UserSystem;
import com.buccodev.tech_shop.utils.dtos.login_dto.LoginRequestDto;
import com.buccodev.tech_shop.utils.dtos.login_dto.LoginResponseDto;
import com.buccodev.tech_shop.utils.dtos.login_dto.UserLoggedDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final FindUserForAuthentication findUserForAuthentication;

    public AuthenticationService(AuthenticationManager authenticationManager, TokenService tokenService, FindUserForAuthentication findUserForAuthentication) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.findUserForAuthentication = findUserForAuthentication;
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password());

        var authentication = authenticationManager.authenticate(usernamePassword);
        var user = (UserDetails) authentication.getPrincipal();
        var token = tokenService.generateToken(user);

        var userAuthenticated = findUserForAuthentication.loadUserByLogin(loginRequestDto.email());


        return switch (userAuthenticated) {
            case UserSystem userSystem -> new LoginResponseDto(
                    new UserLoggedDto(userSystem.getId(), userSystem.getName(), userSystem.getEmail(), userSystem.getRole()),
                    token
            );
            case Customer customer -> new LoginResponseDto(
                    new UserLoggedDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getRole()),
                    token
            );
            default -> throw new IllegalArgumentException("Unknown user type: " + userAuthenticated.getClass());
        };

    }


}
