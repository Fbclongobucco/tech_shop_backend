package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.exceptions.ResourceDuplicateException;
import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.RequestCustomerUpdateDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.RequestCustomerDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.ResponseCustomerDto;
import com.buccodev.tech_shop.utils.mappers.CustomerMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public ResponseCustomerDto createCustomer(RequestCustomerDto requestCustomerDto) {

        if (customerRepository.existsByEmail(requestCustomerDto.email())) {
            throw new ResourceDuplicateException("Customer with email " + requestCustomerDto.email() + " already exists");
        }

        var customer = CustomerMapper.customerRequestDtoToCustomer(requestCustomerDto);
        return CustomerMapper.customerToResponseCustomerDto(customerRepository.save(customer));
    }

    @Transactional
    public void updateCustomer(Long id, RequestCustomerUpdateDto requestUpdateDto) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        customer.setName(requestUpdateDto.name());
        customer.setPhone(requestUpdateDto.phone());
        customerRepository.save(customer);
    }

    public void deleteCustomerById (Long id) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customerRepository.deleteById(customer.getId());
    }

    public ResponseCustomerDto getCustomerById(Long id) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return CustomerMapper.customerToResponseCustomerDto(customer);
    }

    public List<ResponseCustomerDto> getAllCustomers(Integer page, Integer size) {

        if (page == null || page < 0) {
            page = 0;
        }

        if (size == null || size < 1) {
            size = 10;
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return customerRepository.findAll(pageRequest).stream().map(CustomerMapper::customerToResponseCustomerDto).toList();
    }

}
