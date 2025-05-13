package com.ecommerceapp.commonmodule.dto;

import lombok.Data;

@Data
public class InventoryDTO {
    private Long id;
    private Long customerId;
    private Long productId;
    private Integer quantity;
    private Double totalPrice;

    public InventoryDTO(Long id, Long customerId, Long productId, Integer quantity, Double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
