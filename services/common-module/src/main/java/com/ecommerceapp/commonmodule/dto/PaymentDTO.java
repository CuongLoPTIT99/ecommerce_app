package com.ecommerceapp.commonmodule.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PaymentDTO {
    private Long id;
    private Long orderId;
    private String type;
    private Double amount;
    private String status;

    public PaymentDTO(Long id, Long orderId, String type, Double amount, String status) {
        this.id = id;
        this.orderId = orderId;
        this.type = type;
        this.amount = amount;
        this.status = status;
    }
}
