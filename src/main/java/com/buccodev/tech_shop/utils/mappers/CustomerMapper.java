package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.Customer;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerRequestDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerResponseDto;

public class CustomerMapper {

    public static CustomerResponseDto customerToResponseCustomerDto(Customer customer) {
        return new CustomerResponseDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone());
    }

    public static Customer customerRequestDtoToCustomer(CustomerRequestDto requestCustomerDto) {
        return new Customer(null, requestCustomerDto.name(), requestCustomerDto.email(), requestCustomerDto.password(), requestCustomerDto.phone());
    }
}
