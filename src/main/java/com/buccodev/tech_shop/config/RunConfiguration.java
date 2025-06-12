package com.buccodev.tech_shop.config;

import com.buccodev.tech_shop.entities.Roles;
import com.buccodev.tech_shop.entities.UserSystem;
import com.buccodev.tech_shop.repository.UserSystemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class RunConfiguration implements CommandLineRunner {

    private final UserSystemRepository userSystemRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${email_admin}")
    private  String emailAdmin;

    @Value("${password_admin}")
    private  String passwordAdmin;

    public RunConfiguration(UserSystemRepository userSystemRepository, PasswordEncoder passwordEncoder) {
        this.userSystemRepository = userSystemRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userSystemRepository.findByEmail(emailAdmin).isEmpty()) {
            var userSystem = new UserSystem();
            userSystem.setEmail(emailAdmin);
            userSystem.setPassword(passwordEncoder.encode(passwordAdmin));
            userSystem.setUsername("longobucco");
            userSystem.setRole(Roles.ADMIN);
            userSystem.setEnabled(true);
            userSystemRepository.save(userSystem);
        }
    }
}
