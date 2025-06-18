package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.EmailService;
import com.buccodev.tech_shop.services.VerificationCodeService;
import com.buccodev.tech_shop.utils.dtos.email_dto.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @GetMapping("/verify")
    public ResponseEntity<Void> sendEmail(@RequestParam String code, @RequestParam Long userId) {
        emailService.enabledUser(code, userId);
        return ResponseEntity.ok().build();
    }
}
