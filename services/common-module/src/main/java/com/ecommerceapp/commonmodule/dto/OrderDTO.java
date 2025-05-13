package com.ecommerceapp.commonmodule.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private Long customerId;
    private Long productId;
    private Integer quantity;
    private Double totalPrice;

    public OrderDTO(Long id, Long customerId, Long productId, Integer quantity, Double totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
