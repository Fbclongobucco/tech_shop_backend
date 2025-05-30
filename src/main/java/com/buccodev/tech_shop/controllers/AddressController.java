package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.AddressService;
import com.buccodev.tech_shop.utils.dtos.address_dtos.AddressRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;


    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{customerId}")
    public void createAddress(@PathVariable Long customerId, @RequestBody AddressRequestDto addressRequestDto) {
        addressService.createAddress(customerId, addressRequestDto);
    }

    @PutMapping("/{addressId}")
    public void updateAddress(@PathVariable Long addressId, @RequestBody AddressRequestDto addressRequestDto) {
        addressService.updateAddress(addressId, addressRequestDto);
    }

    @DeleteMapping("/{addressId}")
    public void deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
    }

    @PutMapping("/default/{addressId}/{customerId}")
    public void setDefaultAddress(@PathVariable Long addressId, @PathVariable Long customerId) {
        addressService.setDefaultAddress(addressId, customerId);
    }
}
