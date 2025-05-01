package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.entities.Customer;
import com.buccodev.tech_shop.exceptions.CredentialInvalidException;
import com.buccodev.tech_shop.exceptions.ResourceDuplicateException;
import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerResquestUpdateDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerRequestDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerResponseDto;
import com.buccodev.tech_shop.utils.mappers.CustomerMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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

        var password = passwordEncoder.encode(requestCustomerDto.password());

        var customer = CustomerMapper.customerRequestDtoToCustomer(requestCustomerDto);

        customer.setPassword(password);

        return CustomerMapper.customerToResponseCustomerDto(customerRepository.save(customer));
    }

    @Transactional
    public void updateCustomer(Long id, CustomerResquestUpdateDto requestUpdateDto) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        customer.setName(requestUpdateDto.name());
        customer.setPhone(requestUpdateDto.phone());
        customerRepository.save(customer);
    }

    public void deleteCustomerById (Long id) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customerRepository.deleteById(customer.getId());
    }

    public CustomerResponseDto getCustomerById(Long id) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return CustomerMapper.customerToResponseCustomerDto(customer);
    }

    public List<CustomerResponseDto> getAllCustomers(Integer page, Integer size) {

        if (page == null || page < 0) {
            page = 0;
        }

        if (size == null || size < 1) {
            size = 10;
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return customerRepository.findAll(pageRequest).stream().map(CustomerMapper::customerToResponseCustomerDto).toList();
    }

    public Customer getByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new CredentialInvalidException("Invalid credentials"));
    }


}
