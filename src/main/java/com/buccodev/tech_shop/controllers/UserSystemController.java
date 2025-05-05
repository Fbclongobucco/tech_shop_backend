package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.UserSystemService;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemRequestDto;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemResponseDto;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemUpdateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tech-shop/user-system")
public class UserSystemController {

    private final UserSystemService userSystemService;

    public UserSystemController(UserSystemService userSystemService) {
        this.userSystemService = userSystemService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserSystemResponseDto> createUserSystem(@RequestBody UserSystemRequestDto requestUserSystemDto) {
        var userSystem = userSystemService.createUserSystem(requestUserSystemDto);
        URI uri = URI.create("/tech-shop/user-system/" + userSystem.id());
        return ResponseEntity.created(uri).body(userSystem);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<UserSystemResponseDto> createUserSystemAdmin(@RequestBody UserSystemRequestDto requestUserSystemDto) {
        var userSystem = userSystemService.createUserSystemAdmin(requestUserSystemDto);
        URI uri = URI.create("/tech-shop/user-system/" + userSystem.id());
        return ResponseEntity.created(uri).body(userSystem);
    }

    @PreAuthorize("hasAnyRole('ADMIN','BASIC')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserSystem(@PathVariable Long id,
                                                 @RequestBody UserSystemUpdateRequestDto requestUserSystemDto,
                                                 Authentication authentication) {

        userSystemService.updateUserSystem(id, requestUserSystemDto, authentication);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserSystemById(@PathVariable Long id, Authentication authentication) {
        userSystemService.deleteUserSystemById(id, authentication);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','BASIC')")
    @GetMapping("/{id}")
    public ResponseEntity<UserSystemResponseDto> getUserSystemById(@PathVariable Long id, Authentication authentication) {
        var user = userSystemService.getUserSystemById(id, authentication);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserSystemResponseDto>> findAllUserSystems(@RequestParam(required = false) Integer page,
                                                                          @RequestParam(required = false)  Integer size) {
        var user = userSystemService.findAllUserSystems(page, size);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/name/{name}")
    public ResponseEntity<UserSystemResponseDto> findUserSystemsByName(@PathVariable String name, Authentication authentication) {
        var user = userSystemService.findUserSystemsByName(name, authentication);
        return ResponseEntity.ok(user);
    }

}
