package com.ecommerceapp.commonmodule.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Data
public class CartDTO {
    private Long id;
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
    @NotNull(message = "Product ID cannot be null")
    private Long productId;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
    @NotNull(message = "Total price cannot be null")
    @Min(value = 0, message = "Total price must be greater than or equal to 0")
    private Double totalPrice;
    private Timestamp createAt;
    private ProductDTO product;

    public CartDTO(Long id, Long customerId, Long productId, Integer quantity, Double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public CartDTO(Long id, @NotNull(message = "Customer ID cannot be null") Long customerId, @NotNull(message = "Product ID cannot be null") Long productId, @NotNull(message = "Quantity cannot be null") Integer quantity, @NotNull(message = "Total price cannot be null") Double totalPrice, Timestamp createAt) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createAt = createAt;
    }
}
