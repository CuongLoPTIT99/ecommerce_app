package com.ecommerceapp.commonmodule.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class CartDTO {
    private Long id;
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
    @NotNull(message = "Product ID cannot be null")
    private Long productId;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
    private Double totalPrice;
    private Timestamp createdAt;
    private ProductDTO product;

    public CartDTO(Long id, Long customerId, Long productId, Integer quantity, Double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public CartDTO(Long id, @NotNull(message = "Customer ID cannot be null") Long customerId, @NotNull(message = "Product ID cannot be null") Long productId, @NotNull(message = "Quantity cannot be null") Integer quantity, Double totalPrice, Timestamp createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }
}
