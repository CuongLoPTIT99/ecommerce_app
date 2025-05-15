package com.ecommerceapp.commonmodule.saga.event;

import lombok.Data;

@Data
public class InventoryFailedEvent {
    private String orderId;
}
