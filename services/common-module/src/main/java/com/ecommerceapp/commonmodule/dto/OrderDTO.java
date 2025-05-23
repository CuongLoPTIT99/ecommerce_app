package com.ecommerceapp.commonmodule.dto;

import com.ecommerceapp.commonmodule.enums.OrderEnums;
import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private Long customerId;
    private Long productId;
    private Integer quantity;
    private Double totalPrice;
    private OrderEnums.OrderStatus status;
    private String cancelReason;
    private ShipmentDTO shipment;

    public OrderDTO(Long id, Long customerId, Long productId, Integer quantity, Double totalPrice, OrderEnums.OrderStatus status, String cancelReason, ShipmentDTO shipment) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
        this.cancelReason = cancelReason;
        this.shipment = shipment;
    }
}
