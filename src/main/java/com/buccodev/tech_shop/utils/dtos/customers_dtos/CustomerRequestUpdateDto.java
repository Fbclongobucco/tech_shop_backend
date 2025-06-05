package com.buccodev.tech_shop.utils.dtos.customers_dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record CustomerRequestUpdateDto(
        @NotBlank String name,

        @NotBlank
        @Pattern(regexp = "^\\d{10,11}$",
                message = "Phone number must contain only digits, including area code (DDD), with a total of 10 or 11 digits.")
        String phone
) {
}