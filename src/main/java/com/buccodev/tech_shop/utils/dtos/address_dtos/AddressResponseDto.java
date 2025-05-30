package com.buccodev.tech_shop.utils.dtos.address_dtos;

public record AddressResponseDto(Long id,
    String street,
    String number,
    String neighborhood,
    String city,
    String state,
    String country,
    String zipCode,
    String complement) {
}
