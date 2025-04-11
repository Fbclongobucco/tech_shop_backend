package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.Customer;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.RequestCustomerDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.ResponseCustomerDto;

public class CustomerMapper {

    public static ResponseCustomerDto customerToResponseCustomerDto(Customer customer) {
        return new ResponseCustomerDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone());
    }

    public static Customer customerRequestDtoToCustomer(RequestCustomerDto requestCustomerDto) {
        return new Customer(null, requestCustomerDto.name(), requestCustomerDto.email(), requestCustomerDto.password(), requestCustomerDto.phone());
    }
}
