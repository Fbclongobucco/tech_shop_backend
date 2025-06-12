package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.entities.VerificationCode;
import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.repository.UserSystemRepository;
import com.buccodev.tech_shop.repository.VerificationCodeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final CustomerRepository customerRepository;
    private final UserSystemRepository userSystemRepository;

    public VerificationCodeService(VerificationCodeRepository verificationCodeRepository, CustomerRepository customerRepository, UserSystemRepository userSystemRepository) {
        this.verificationCodeRepository = verificationCodeRepository;
        this.customerRepository = customerRepository;
        this.userSystemRepository = userSystemRepository;
    }

    public String saveVerificationCode(Long userId) {

        var customer = customerRepository.findById(userId);
        var code = UUID.randomUUID().toString();
        if(customer.isPresent()){
            var verificationCode = new VerificationCode();
            verificationCode.setCode(code);
            verificationCode.setUserId(customer.get().getId());
            verificationCode.setExpirationTime(LocalDateTime.now().plusMinutes(10));
            verificationCodeRepository.save(verificationCode);
        }

         var userSystem = userSystemRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found"));
         var verificationCode = new VerificationCode();
         verificationCode.setCode(code);
         verificationCode.setUserId(userSystem.getId());
         verificationCode.setExpirationTime(LocalDateTime.now().plusMinutes(10));
         verificationCodeRepository.save(verificationCode);

        return code;
    }

    public boolean verifyCode(String code, Long userId) {
        var verificationCode = verificationCodeRepository.findByUserId(userId);
        if(verificationCode.isPresent()){
            var codeEntity = verificationCode.get();
            return codeEntity.getCode().equals(code) && codeEntity.getExpirationTime().isAfter(LocalDateTime.now());
        }
        return false;
    }

    public void deleteVerificationCode(Long userId) {
        var verificationCode = verificationCodeRepository.findByUserId(userId);
        verificationCode.ifPresent(verificationCodeRepository::delete);
    }
}
