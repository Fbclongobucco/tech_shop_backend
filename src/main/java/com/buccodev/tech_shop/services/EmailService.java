package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.entities.Customer;
import com.buccodev.tech_shop.entities.UserSystem;
import com.buccodev.tech_shop.exceptions.CodeVerificationException;
import com.buccodev.tech_shop.exceptions.CredentialInvalidException;
import com.buccodev.tech_shop.exceptions.EmailException;
import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.repository.UserSystemRepository;
import com.buccodev.tech_shop.utils.dtos.email_dto.Email;
import com.buccodev.tech_shop.utils.dtos.login_dto.PasswordResetDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.UUID;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final VerificationCodeService verificationCodeService;
    private final CustomerRepository customerRepository;
    private final UserSystemRepository userSystemRepository;
    private final TemplateEngine templateEngine;
    private final PasswordEncoder passwordEncoder;

    public EmailService(JavaMailSender javaMailSender, VerificationCodeService verificationCodeService, CustomerRepository customerRepository, UserSystemRepository userSystemRepository, TemplateEngine templateEngine, PasswordEncoder passwordEncoder) {
        this.javaMailSender = javaMailSender;
        this.verificationCodeService = verificationCodeService;
        this.customerRepository = customerRepository;
        this.userSystemRepository = userSystemRepository;
        this.templateEngine = templateEngine;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendEmail(Email email){

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            context.setVariable("name", email.name());
            context.setVariable("code", email.code());

            String htmlContent = templateEngine.process("email-verify-template", context);

            helper.setFrom("noreply@gmail.com");
            helper.setTo(email.to());
            helper.setSubject(email.subject());
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

    public void enabledUser(String code, Long userId, Authentication authentication) {

        validateOrderOwnership(userId, authentication);

        var customer = customerRepository.findById(userId);
        if(customer.isPresent()){
            var verificationCode = verificationCodeService.verifyCode(code, userId);
            if(verificationCode){
                customer.get().setEnabled(true);
                customerRepository.save(customer.get());
                verificationCodeService.deleteVerificationCode(userId);
            } else {
                throw new CodeVerificationException("code is not valid");
            }

        } else {
            var userSystem = userSystemRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found"));
            var verificationCode = verificationCodeService.verifyCode(code, userId);
            if(verificationCode){
                userSystem.setEnabled(true);
                userSystemRepository.save(userSystem);
                verificationCodeService.deleteVerificationCode(userId);
            } else {
                throw new CodeVerificationException("code is not valid");
            }
        }
    }

    public void sendResetPassword(Long userId, Authentication authentication) {
        validateOrderOwnership(userId, authentication);

        String emailTo;
        String name;
        Long id;

        var customerOptional = customerRepository.findById(userId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            emailTo = customer.getEmail();
            name = customer.getName();
            id = customer.getId();
        } else {
            UserSystem userSystem = userSystemRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            emailTo = userSystem.getEmail();
            name = userSystem.getName();
            id = userSystem.getId();
        }

        String code = verificationCodeService.saveVerificationCode(id);

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("code", code);

            String htmlContent = templateEngine.process("email-resetpassword-template", context);

            helper.setFrom("noreply@gmail.com");
            helper.setTo(emailTo);
            helper.setSubject("Redefinição de Senha - Tech Shop");
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailException("Erro ao enviar e-mail de redefinição de senha");
        }
    }


    public void passwordReset(PasswordResetDto passwordResetDto,  Authentication authentication) {

        var code = verificationCodeService.getVerificationCodeByCode(passwordResetDto.code());

        validateOrderOwnership(code.getUserId(), authentication);

        var customer = customerRepository.findById(code.getUserId());
        if(customer.isPresent()){
            var verificationCode = verificationCodeService.verifyCode(passwordResetDto.code(), code.getUserId());
            if(verificationCode) {
                customer.get().setPassword(passwordEncoder.encode(passwordResetDto.password()));
                customerRepository.save(customer.get());
                verificationCodeService.deleteVerificationCode(code.getUserId());
            } else {
                throw new CodeVerificationException("code is not valid");
            }
        } else {
            var userSystem = userSystemRepository.findById(code.getUserId()).orElseThrow(()-> new ResourceNotFoundException("user not found"));
            var verificationCode = verificationCodeService.verifyCode(passwordResetDto.code(), code.getUserId());
            if(verificationCode) {
                userSystem.setPassword(passwordEncoder.encode(passwordResetDto.password()));
                userSystemRepository.save(userSystem);
                verificationCodeService.deleteVerificationCode(code.getUserId());
            } else {
                throw new CodeVerificationException("code is not valid");
            }
        }
    }

    private void validateOrderOwnership(Long userId, Authentication authentication) {
       var user = (UserDetails) authentication.getPrincipal();

        if(user instanceof Customer userCustomer) {

            if (!userCustomer.getId().equals(userId)) {
                throw new CredentialInvalidException("You don't have permission");
            }
        }
        else if(user instanceof UserSystem userSystem) {
            if (!userSystem.getId().equals(userId)) {
                throw new CredentialInvalidException("You don't have permission");
            }
        }
    }
}
