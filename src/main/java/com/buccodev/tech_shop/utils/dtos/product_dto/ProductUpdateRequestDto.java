package com.buccodev.tech_shop.utils.dtos.product_dto;

import com.buccodev.tech_shop.utils.dtos.category_dtos.CategoryResponseDto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductUpdateRequestDto(
                                      @Size(min = 2, max = 100, message = "Name must be between {min} and {max} characters")
                                      String name,
                                      @Size(min = 10, max = 500, message = "Description must be between {min} and {max} characters")
                                      String description,
                                      @DecimalMin(value = "0.01", message = "Price must be greater than {value}")
                                      @Digits(integer = 6, fraction = 2, message = "Price must have up to {integer} integer and {fraction} decimal digits")
                                      BigDecimal price,
                                      @Size(min = 2, message = "Category must have at least {min} characters")
                                      String category,
                                      @Min(value = 0, message = "Stock quantity cannot be negative")
                                      @Max(value = 9999, message = "Stock quantity cannot exceed {value}")
                                      Integer quantityStock) {}
