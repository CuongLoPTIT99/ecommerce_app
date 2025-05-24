package com.ecommerceapp.commonmodule.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CartDTO {
    private Long id;
    @NotBlank(message = "Customer ID cannot be blank")
    private Long customerId;
    @NotBlank(message = "Product ID cannot be blank")
    private Long productId;
    @NotBlank(message = "Quantity cannot be blank")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
    @NotBlank(message = "Total price cannot be blank")
    @Min(value = 0, message = "Total price must be greater than or equal to 0")
    private Double totalPrice;

    public CartDTO(Long id, Long customerId, Long productId, Integer quantity, Double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
