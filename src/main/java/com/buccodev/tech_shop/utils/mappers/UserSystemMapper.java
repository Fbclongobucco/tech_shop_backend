package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.UserSystem;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemRequestDto;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemResponseDto;

public class UserSystemMapper {

    public static UserSystemResponseDto toUserSystemResponseDto(UserSystem userSystem) {
        return new UserSystemResponseDto(userSystem.getId(), userSystem.getUsername(), userSystem.getEmail(), userSystem.getRole());
    }

    public static UserSystem toUserSystem(UserSystemRequestDto userSystemRequestDto) {
        return new UserSystem(null, userSystemRequestDto.username(), userSystemRequestDto.password(), userSystemRequestDto.email());
    }
}
