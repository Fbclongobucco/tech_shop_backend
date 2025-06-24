package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.AddressRepository;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.utils.dtos.address_dtos.AddressRequestDto;
import com.buccodev.tech_shop.utils.dtos.address_dtos.AddressResponseDto;
import com.buccodev.tech_shop.utils.mappers.AddressMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void createAddress(Long customerId, AddressRequestDto addressRequestDto) {

        var customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        var addressRecovered = customer.getDefaultAddress();

        if (addressRecovered == null) {
            var address = AddressMapper.toAddress(addressRequestDto);
            address.setCustomer(customer);
            addressRepository.save(address);
            customer.setDefaultAddress(address);
        }

        customerRepository.save(customer);
    }

    @Transactional
    public void updateAddress(Long addressId, AddressRequestDto addressRequestDto) {

        var address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        address.setStreet(addressRequestDto.street());
        address.setNumber(addressRequestDto.number());
        address.setCity(addressRequestDto.city());
        address.setNeighborhood(addressRequestDto.neighborhood());
        address.setState(addressRequestDto.state());
        address.setCountry(addressRequestDto.country());
        address.setZipCode(addressRequestDto.zipCode());
        address.setComplement(addressRequestDto.complement());

        addressRepository.save(address);
    }

    @Transactional
    public void deleteAddress(Long addressId) {

        var address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        addressRepository.delete(address);
    }

    public void setDefaultAddress(Long addressId, Long customerId) {

        var address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        var customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        var addressRecovered = customer.getDefaultAddress();

        if (addressRecovered != null) {
            addressRecovered.setDefault(false);
            addressRepository.save(addressRecovered);
        }

        address.setDefault(true);
        addressRepository.save(address);
        customer.setDefaultAddress(address);
        customerRepository.save(customer);
    }

    public List<AddressResponseDto> getAddressesByCustomer(Long customerId) {

        var customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return customer.getAddresses().stream()
                .map(AddressMapper::toAddressResponseDto)
                .toList();
    }

}
