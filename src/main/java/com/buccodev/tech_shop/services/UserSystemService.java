package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.entities.Customer;
import com.buccodev.tech_shop.entities.Roles;
import com.buccodev.tech_shop.exceptions.CredentialInvalidException;
import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.UserSystemRepository;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemRequestDto;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemResponseDto;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemUpdateRequestDto;
import com.buccodev.tech_shop.utils.mappers.UserSystemMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSystemService {

    private final UserSystemRepository userSystemRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSystemService(UserSystemRepository userSystemRepository, PasswordEncoder passwordEncoder) {
        this.userSystemRepository = userSystemRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserSystemResponseDto createUserSystem(UserSystemRequestDto requestUserSystemDto) {
        var userSystem = UserSystemMapper.toUserSystem(requestUserSystemDto);
        userSystem.setPassword(passwordEncoder.encode(requestUserSystemDto.password()));
        return UserSystemMapper.toUserSystemResponseDto(userSystemRepository.save(userSystem));
    }

    public UserSystemResponseDto createUserSystemAdmin(UserSystemRequestDto requestUserSystemDto) {
        var userSystem = UserSystemMapper.toUserSystem(requestUserSystemDto);
        userSystem.setRole(Roles.ADMIN);
        userSystem.setPassword(passwordEncoder.encode(requestUserSystemDto.password()));
        return UserSystemMapper.toUserSystemResponseDto(userSystemRepository.save(userSystem));
    }

    public void updateUserSystem(Long id, UserSystemUpdateRequestDto requestUserSystemDto, Authentication authentication) {
        var userSystem = userSystemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validateOrderOwnership(userSystem.getId(), authentication);
        userSystem.setUsername(requestUserSystemDto.username());
        userSystem.setRole(requestUserSystemDto.role());
        userSystemRepository.save(userSystem);
    }

    public void deleteUserSystemById(Long id, Authentication authentication) {

        var userSystem = userSystemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validateOrderOwnership(userSystem.getId(), authentication);

        userSystemRepository.deleteById(userSystem.getId());
    }

    public UserSystemResponseDto getUserSystemById(Long id, Authentication authentication) {

        var userSystem = userSystemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validateOrderOwnership(userSystem.getId(), authentication);

        return UserSystemMapper.toUserSystemResponseDto(userSystem);
    }

    public List<UserSystemResponseDto> findAllUserSystems(Integer page, Integer size) {

        if (page == null || page < 0) {
            page = 0;
        }

        if (size == null || size < 1) {
            size = 10;
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return userSystemRepository.findAll(pageRequest).stream().map(UserSystemMapper::toUserSystemResponseDto).toList();
    }

    public UserSystemResponseDto findUserSystemsByName(String name, Authentication authentication) {

        var userSystem = userSystemRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        validateOrderOwnership(userSystem.getId(), authentication);
        return UserSystemMapper.toUserSystemResponseDto(userSystem);
    }

    private void validateOrderOwnership(Long userId, Authentication authentication) {

        var user = (UserDetails) authentication.getPrincipal();

        if(user instanceof Customer userCustomer) {

            if (!userCustomer.getId().equals(userId)) {
                throw new CredentialInvalidException("You don't have permission");
            }
        }
    }

}
