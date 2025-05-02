package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.UserSystemService;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemRequestDto;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemResponseDto;
import com.buccodev.tech_shop.utils.dtos.user_system_dto.UserSystemUpdateRequestDto;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<UserSystemResponseDto> createUserSystem(@RequestBody UserSystemRequestDto requestUserSystemDto) {
        var userSystem = userSystemService.createUserSystem(requestUserSystemDto);
        URI uri = URI.create("/tech-shop/user-system/" + userSystem.id());
        return ResponseEntity.created(uri).body(userSystem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserSystem(@PathVariable Long id, @RequestBody UserSystemUpdateRequestDto requestUserSystemDto) {
        userSystemService.updateUserSystem(id, requestUserSystemDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserSystemById(@PathVariable Long id) {
        userSystemService.deleteUserSystemById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSystemResponseDto> getUserSystemById(@PathVariable Long id) {
        var user = userSystemService.getUserSystemById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserSystemResponseDto>> findAllUserSystems(@RequestParam(required = false) Integer page,
                                                                          @RequestParam(required = false)  Integer size) {
        var user = userSystemService.findAllUserSystems(page, size);
        return ResponseEntity.ok(user);
    }

}
