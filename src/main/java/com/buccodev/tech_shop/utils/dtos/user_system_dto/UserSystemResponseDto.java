package com.buccodev.tech_shop.utils.dtos.user_system_dto;

import com.buccodev.tech_shop.entities.Roles;

import javax.management.relation.Role;

public record UserSystemResponseDto(Long id, String name, String email, String role) {
}
