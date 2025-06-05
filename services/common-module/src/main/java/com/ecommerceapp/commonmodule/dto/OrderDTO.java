package com.ecommerceapp.commonmodule.dto;

import com.ecommerceapp.commonmodule.enums.OrderEnums;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
    @NotNull(message = "Quantity cannot be null")
    private Long productId;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
    @NotNull(message = "Total price cannot be null")
    @Min(value = 0, message = "Total price must be greater than or equal to 0")
    private Double totalPrice;
    private OrderEnums.OrderStatus status;
    private String cancelReason;
    private ProductDTO product;
    private ShipmentDTO shipment;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public OrderDTO(Long id, @NotNull(message = "Customer ID cannot be null") Long customerId, @NotNull(message = "Quantity cannot be null") Long productId, @NotNull(message = "Quantity cannot be null") Integer quantity, @NotNull(message = "Total price cannot be null") Double totalPrice, OrderEnums.OrderStatus status, String cancelReason, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
        this.cancelReason = cancelReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public OrderDTO(Long id, @NotNull(message = "Customer ID cannot be null") Long customerId, @NotNull(message = "Quantity cannot be null") Long productId, @NotNull(message = "Quantity cannot be null") Integer quantity, @NotNull(message = "Total price cannot be null") Double totalPrice, OrderEnums.OrderStatus status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
