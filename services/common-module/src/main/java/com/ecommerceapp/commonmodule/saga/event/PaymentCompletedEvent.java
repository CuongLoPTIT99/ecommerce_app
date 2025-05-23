package com.ecommerceapp.commonmodule.saga.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCompletedEvent {
    private Long orderId;
}
