package com.buccodev.tech_shop.utils.dtos.user_system_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserSystemRequestDto(@NotBlank String name,
                                   @NotBlank @Email(message = "Invalid email") String email,
                                   @NotBlank @Length(min = 8, message = "Invalid password") String password) {
}
