package com.buccodev.tech_shop.repository;

import com.buccodev.tech_shop.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
