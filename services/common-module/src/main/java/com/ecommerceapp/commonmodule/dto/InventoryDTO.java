package com.ecommerceapp.commonmodule.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class InventoryDTO {
    private Long id;
    private Long productId;
    private Integer quantity;

    public InventoryDTO(Long id, Long productId, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }
}
