package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.exceptions.CodeVerificationException;
import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.repository.UserSystemRepository;
import com.buccodev.tech_shop.utils.dtos.email_dto.Email;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final VerificationCodeService verificationCodeService;
    private final CustomerRepository customerRepository;
    private final UserSystemRepository userSystemRepository;

    public EmailService(JavaMailSender javaMailSender, VerificationCodeService verificationCodeService, CustomerRepository customerRepository, UserSystemRepository userSystemRepository) {
        this.javaMailSender = javaMailSender;
        this.verificationCodeService = verificationCodeService;
        this.customerRepository = customerRepository;
        this.userSystemRepository = userSystemRepository;
    }

    public void sendEmail(Email email){
        var message = new SimpleMailMessage();

        message.setFrom("noreply@gmail.com");
        message.setTo(email.to());
        message.setSubject(email.subject());
        message.setText(email.body());
        javaMailSender.send(message);
    }

    public void enabledUser(String code, Long userId){
       var customer = customerRepository.findById(userId);
        if(customer.isPresent()){
            var verificationCode = verificationCodeService.verifyCode(code, userId);
            if(verificationCode){
                customer.get().setEnabled(true);
                customerRepository.save(customer.get());
            } else {
                throw new CodeVerificationException("code is not valid");
            }

        } else {
            var userSystem = userSystemRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found"));
            var verificationCode = verificationCodeService.verifyCode(code, userId);
            if(verificationCode){
                userSystem.setEnabled(true);
                userSystemRepository.save(userSystem);
            } else {
                throw new CodeVerificationException("code is not valid");
            }
        }
    }
}
