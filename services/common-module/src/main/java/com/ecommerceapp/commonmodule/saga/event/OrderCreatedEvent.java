package com.ecommerceapp.commonmodule.saga.event;

import com.ecommerceapp.commonmodule.dto.ShipmentDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCreatedEvent {
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private ShipmentDTO shipment;
}
