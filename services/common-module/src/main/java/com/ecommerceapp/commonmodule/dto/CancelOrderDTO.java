package com.ecommerceapp.commonmodule.dto;

import lombok.Data;

@Data
public class CancelOrderDTO {
    private Long orderId;
    private String cancelReason;
}
