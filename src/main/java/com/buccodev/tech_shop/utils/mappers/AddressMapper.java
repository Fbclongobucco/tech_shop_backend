package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.Address;
import com.buccodev.tech_shop.utils.dtos.address_dtos.AddressRequestDto;
import com.buccodev.tech_shop.utils.dtos.address_dtos.AddressResponseDto;

public class AddressMapper {

    public static Address toAddress(AddressRequestDto addressRequestDto) {
        Address address = new Address();
        address.setStreet(addressRequestDto.street());
        address.setNumber(addressRequestDto.number());
        address.setNeighborhood(addressRequestDto.neighborhood());
        address.setCity(addressRequestDto.city());
        address.setState(addressRequestDto.state());
        address.setCountry(addressRequestDto.country());
        address.setZipCode(addressRequestDto.zipCode());
        address.setComplement(addressRequestDto.complement());
        return address;
    }

    public static AddressResponseDto toAddressResponseDto(Address address) {

        return new AddressResponseDto(
            address.getId(),
            address.getStreet(),
            address.getNumber(),
            address.getNeighborhood(),
            address.getCity(),
            address.getState(),
            address.getCountry(),
            address.getZipCode(),
            address.getComplement());
    }
}
