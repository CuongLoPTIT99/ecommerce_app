package com.ecommerceapp.commonmodule.saga.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentFailedEvent {
    private Long orderId;
}
