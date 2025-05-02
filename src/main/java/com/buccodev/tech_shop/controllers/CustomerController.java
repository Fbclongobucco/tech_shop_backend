package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.CustomerService;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerRequestDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerResponseDto;
import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerRequestUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers(@RequestParam(required = false) Integer page,
                                                                     @RequestParam(required = false)  Integer size) {
        return ResponseEntity.ok(customerService.getAllCustomers(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id,
                                               @RequestBody CustomerRequestUpdateDto customerResquestUpdateDto) {
        customerService.updateCustomer(id, customerResquestUpdateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody @Valid CustomerRequestDto customerRequestDto){

        var customer = customerService.createCustomer(customerRequestDto);

        URI uri = URI.create("/tech-shop/customers/" + customer.id());

        return ResponseEntity.created(uri).body(customer);
    }

}
