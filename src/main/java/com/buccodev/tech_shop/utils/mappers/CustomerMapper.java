package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.Customer;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerRequestDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerResponseDto;

import java.time.format.DateTimeFormatter;

public class CustomerMapper {

    public static CustomerResponseDto customerToResponseCustomerDto(Customer customer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = (customer.getCreatedAt() != null)
                ? customer.getCreatedAt().format(formatter)
                : null;
        return new CustomerResponseDto(customer.getId(), customer.getName(), customer.getEmail(),
                customer.getPhone(), formattedDate);
    }

    public static Customer customerRequestDtoToCustomer(CustomerRequestDto requestCustomerDto) {
        return new Customer(null, requestCustomerDto.name(), requestCustomerDto.email(), requestCustomerDto.password(), requestCustomerDto.phone());
    }
}
