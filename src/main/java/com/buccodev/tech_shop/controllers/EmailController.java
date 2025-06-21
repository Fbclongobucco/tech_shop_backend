package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.EmailService;
import com.buccodev.tech_shop.services.VerificationCodeService;
import com.buccodev.tech_shop.utils.dtos.email_dto.Email;
import com.buccodev.tech_shop.utils.dtos.login_dto.PasswordResetDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }
    @PreAuthorize("hasAnyRole('ADMIN','BASIC','CUSTOMER')")
    @GetMapping("/verify")
    public ResponseEntity<Void> sendEmail(@RequestParam String code, @RequestParam Long userId, Authentication authentication) {
        emailService.enabledUser(code, userId, authentication);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','BASIC','CUSTOMER')")
    @GetMapping("/password-reset/{userId}")
    public ResponseEntity<Void> passwordReset(@PathVariable Long userId, Authentication authentication) {
        emailService.sendResetPassword(userId, authentication);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PostMapping("/password-reset")
    public ResponseEntity<Void> newPassword(@RequestBody PasswordResetDto passwordResetDto, Authentication authentication) {
        emailService.passwordReset(passwordResetDto, authentication);
        return ResponseEntity.ok().build();
    }
}
