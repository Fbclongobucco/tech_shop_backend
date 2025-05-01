package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.UserSystemRepository;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemRequestDto;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemResponseDto;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemUpdateRequestDto;
import com.buccodev.tech_shop.utils.mappers.UserSystemMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSystemService {

    private final UserSystemRepository userSystemRepository;

    public UserSystemService(UserSystemRepository userSystemRepository) {
        this.userSystemRepository = userSystemRepository;
    }

    public UserSystemResponseDto createUserSystem(UserSystemRequestDto requestUserSystemDto) {
        var userSystem = UserSystemMapper.userSystemRequestDtoToUserSystem(requestUserSystemDto);
        return UserSystemMapper.userSystemToUserSystemResponseDto(userSystemRepository.save(userSystem));
    }

    public void updateUserSystem(Long id, UserSystemUpdateRequestDto requestUserSystemDto) {
        var userSystem = userSystemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userSystem.setUsername(requestUserSystemDto.username());
        userSystem.setEmail(requestUserSystemDto.email());
        userSystem.setRole(requestUserSystemDto.role());
        userSystemRepository.save(userSystem);
    }

    public void deleteUserSystemById(Long id) {

        var userSystem = userSystemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userSystemRepository.deleteById(userSystem.getId());
    }

    public UserSystemResponseDto getUserSystemById(Long id) {

        var userSystem = userSystemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserSystemMapper.userSystemToUserSystemResponseDto(userSystem);
    }

    public List<UserSystemResponseDto> findAllUserSystems(Integer page, Integer size) {

        if (page == null || page < 0) {
            page = 0;
        }

        if (size == null || size < 1) {
            size = 10;
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return userSystemRepository.findAll(pageRequest).stream().map(UserSystemMapper::userSystemToUserSystemResponseDto).toList();
    }

    public UserSystemResponseDto findUserSystemsByName(String name) {

        var userSystem = userSystemRepository.findByUsername(name).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserSystemMapper.userSystemToUserSystemResponseDto(userSystem);
    }




}
