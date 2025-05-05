package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.CustomerService;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerRequestDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerResponseDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerRequestUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tech-shop/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','BASIC', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(customerService.getCustomerById(id, authentication));
    }

    @PreAuthorize("hasAnyRole('ADMIN','BASIC')")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers(@RequestParam(required = false) Integer page,
                                                                     @RequestParam(required = false)  Integer size) {
        return ResponseEntity.ok(customerService.getAllCustomers(page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id,
                                               @RequestBody CustomerRequestUpdateDto customerRequestUpdateDto,
                                               Authentication authentication) {
        customerService.updateCustomer(id, customerRequestUpdateDto, authentication);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id, Authentication authentication) {
        customerService.deleteCustomerById(id, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDto> getByEmail(@PathVariable String email, Authentication authentication) {
        return ResponseEntity.ok(customerService.getByEmail(email, authentication));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody @Valid CustomerRequestDto customerRequestDto){

        var customer = customerService.createCustomer(customerRequestDto);

        URI uri = URI.create("/tech-shop/customers/" + customer.id());

        return ResponseEntity.created(uri).body(customer);
    }

}
