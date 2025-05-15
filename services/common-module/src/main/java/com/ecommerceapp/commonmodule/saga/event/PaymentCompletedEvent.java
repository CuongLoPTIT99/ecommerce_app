package com.ecommerceapp.commonmodule.saga.event;

import lombok.Data;

@Data
public class PaymentCompletedEvent {
    private String orderId;
}
