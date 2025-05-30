package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.entities.Customer;
import com.buccodev.tech_shop.exceptions.CredentialInvalidException;
import com.buccodev.tech_shop.exceptions.ResourceDuplicateException;
import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerRequestUpdateDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerRequestDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerResponseDto;
import com.buccodev.tech_shop.utils.mappers.CustomerMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponseDto createCustomer(CustomerRequestDto requestCustomerDto) {

        if (customerRepository.existsByEmail(requestCustomerDto.email())) {
            throw new ResourceDuplicateException("Customer with email " + requestCustomerDto.email() + " already exists");
        }

        var customer = CustomerMapper.toCustomer(requestCustomerDto);
        customer.setPassword(passwordEncoder.encode(requestCustomerDto.password()));
        customer.setCreatedAt(LocalDateTime.now());

        return CustomerMapper.toResponseCustomerDto(customerRepository.save(customer));
    }

    @Transactional
    public void updateCustomer(Long id, CustomerRequestUpdateDto requestUpdateDto, Authentication authentication) {
        validateOrderOwnership(id, authentication);
        var customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customer.setName(requestUpdateDto.name());
        customer.setPhone(requestUpdateDto.phone());
        customerRepository.save(customer);
    }

    public void deleteCustomerById (Long id, Authentication authentication) {
        validateOrderOwnership(id, authentication);
        var customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customerRepository.deleteById(customer.getId());
    }

    public CustomerResponseDto getCustomerById(Long id, Authentication authentication) {
        validateOrderOwnership(id, authentication);
        var customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return CustomerMapper.toResponseCustomerDto(customer);
    }

    public List<CustomerResponseDto> getAllCustomers(Integer page, Integer size) {

        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size < 1) {
            size = 10;
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return customerRepository.findAll(pageRequest).stream().map(CustomerMapper::toResponseCustomerDto).toList();
    }

    public CustomerResponseDto getByEmail(String email, Authentication authentication) {
        var customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CredentialInvalidException("Customer not found"));
        validateOrderOwnership(customer.getId(), authentication);
        return CustomerMapper.toResponseCustomerDto(customer);
    }



    private void validateOrderOwnership(Long userId, Authentication authentication) {

        var user = (UserDetails) authentication.getPrincipal();

        if(user instanceof Customer userCustomer) {

            if (!userCustomer.getId().equals(userId)) {
                throw new CredentialInvalidException("You don't have permission");
            }
        }
    }
}
