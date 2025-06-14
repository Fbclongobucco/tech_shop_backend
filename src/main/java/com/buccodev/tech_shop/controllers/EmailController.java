package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.EmailService;
import com.buccodev.tech_shop.utils.dtos.email_dto.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/send")
    public ResponseEntity<Void> sendEmail(@RequestBody Email email) {
        emailService.sendEmail(email);
        return ResponseEntity.ok().build();
    }
}
