package com.buccodev.tech_shop.utils.dtos.customers_dtos;


import com.buccodev.tech_shop.utils.dtos.address_dtos.AddressResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

public record CustomerResponseDto(Long id, String name, String email, String phone, boolean isEnabled,
                                  @JsonInclude(JsonInclude.Include.NON_NULL) String createdAt,
                                  @JsonInclude(JsonInclude.Include.NON_NULL) AddressResponseDto address) {
}
