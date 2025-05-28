package com.buccodev.tech_shop.security;

import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.repository.UserSystemRepository;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class FindUserForAuthentication {

    private final UserSystemRepository userSystemRepository;
    private final CustomerRepository customerRepository;

    public FindUserForAuthentication(UserSystemRepository userSystemRepository, CustomerRepository customerRepository) {
        this.userSystemRepository = userSystemRepository;
        this.customerRepository = customerRepository;
    }

    public UserDetails loadUserByLogin(String login) {

        var user = userSystemRepository.findByEmail(login);
        if (user.isPresent()) {
            return user.get();
        }
        return customerRepository.findByEmail(login).orElseThrow(()-> new ResourceNotFoundException("User not found"));

    }

}
