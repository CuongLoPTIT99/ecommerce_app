package com.ecommerceapp.commonmodule.dto;

import com.ecommerceapp.commonmodule.enums.OrderEnums;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class OrderDTO {
    private Long id;
    @NonNull
    private Long customerId;
    @NonNull
    private Long productId;
    @NotBlank(message = "Quantity cannot be blank")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
    @NotBlank(message = "Total price cannot be blank")
    @Min(value = 0, message = "Total price must be greater than or equal to 0")
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
