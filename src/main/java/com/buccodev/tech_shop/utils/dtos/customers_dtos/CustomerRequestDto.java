package com.buccodev.tech_shop.utils.dtos.customers_dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerRequestDto(@NotBlank(message = "Name is required")
                                 String name,
                                 @NotBlank(message = "Email is required") @Email(message = "Invalid email")
                                 String email,
                                 @NotBlank(message = "Password is required") @Size(min = 8, message = "Invalid password")
                                 String password,
                                 @NotBlank(message = "Phone is required") @Pattern(regexp = "^[0-9]+$", message = "Invalid phone") @Size(max = 15, message = "Invalid phone")
                                 String phone) {
}
