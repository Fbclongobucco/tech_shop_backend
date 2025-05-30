package com.buccodev.tech_shop.security;

import com.buccodev.tech_shop.exceptions.CredentialInvalidException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderImp implements AuthenticationProvider {

    private final FindUserForAuthentication findUserForAuthentication;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationProviderImp(FindUserForAuthentication findUserForAuthentication, PasswordEncoder passwordEncoder) {
        this.findUserForAuthentication = findUserForAuthentication;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var login = authentication.getName();
        var pass = authentication.getCredentials().toString();

        var user = findUserForAuthentication.loadUserByLogin(login);

        if(!passwordEncoder.matches(pass, user.getPassword())) {
            throw new CredentialInvalidException("Invalid credentials");
        }

        var authenticated = new UsernamePasswordAuthenticationToken(user ,null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticated);


        return authenticated;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
