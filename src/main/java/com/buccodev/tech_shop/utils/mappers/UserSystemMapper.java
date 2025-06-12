package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.UserSystem;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemRequestDto;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemResponseDto;

public class UserSystemMapper {

    public static UserSystemResponseDto toUserSystemResponseDto(UserSystem userSystem) {
        return new UserSystemResponseDto(userSystem.getId(),
                userSystem.getName(),
                userSystem.getEmail(),
                userSystem.getRole(),
                userSystem.isEnabled());
    }

    public static UserSystem toUserSystem(UserSystemRequestDto userSystemRequestDto) {
        return new UserSystem(null, userSystemRequestDto.name(), userSystemRequestDto.password(), userSystemRequestDto.email());
    }
}
