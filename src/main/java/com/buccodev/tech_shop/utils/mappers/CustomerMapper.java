package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.Customer;
import com.buccodev.tech_shop.utils.dtos.address_dtos.AddressResponseDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerRequestDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerResponseDto;

import java.time.format.DateTimeFormatter;

public class CustomerMapper {

    public static CustomerResponseDto toResponseCustomerDto(Customer customer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = (customer.getCreatedAt() != null)
                ? customer.getCreatedAt().format(formatter)
                : null;

        AddressResponseDto addressResponse = (customer.getDefaultAddress() != null)
                ? AddressMapper.toAddressResponseDto(customer.getDefaultAddress())
                : null;

        return new CustomerResponseDto(customer.getId(), customer.getName(), customer.getEmail(),
                customer.getPhone(),customer.isEnabled() ,formattedDate, addressResponse);
    }

    public static Customer toCustomer(CustomerRequestDto requestCustomerDto) {
        return new Customer(null, requestCustomerDto.name(), requestCustomerDto.email(), requestCustomerDto.password(), requestCustomerDto.phone());
    }

    public static CustomerResponseDto toResponseCustomerDtoToOrder(Customer customer) {
        return new CustomerResponseDto(customer.getId(), customer.getName(), customer.getEmail(),
                customer.getPhone(), customer.isEnabled(), null, null);
    }
}
