package com.buccodev.tech_shop.config;

import com.buccodev.tech_shop.entities.UserSystem;
import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.repository.UserSystemRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final UserSystemRepository userSystemRepository;

    public AuthenticationService(CustomerRepository customerRepository, UserSystemRepository userSystemRepository) {
        this.customerRepository = customerRepository;
        this.userSystemRepository = userSystemRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var customer = customerRepository.findByEmail(username);
        if (customer.isPresent()) {
            return customer.get();
        }

        var userSystem = userSystemRepository.findByEmail(username);
        if (userSystem.isPresent()) {
            return userSystem.get();
        }


        throw new ResourceNotFoundException("User " + username + " not found");


    }
}
