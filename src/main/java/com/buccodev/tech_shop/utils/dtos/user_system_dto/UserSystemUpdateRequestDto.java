package com.buccodev.tech_shop.utils.dtos.user_system_dto;

import com.buccodev.tech_shop.entities.Roles;

public record UserSystemUpdateRequestDto(String username, String email, Roles role) {
}
