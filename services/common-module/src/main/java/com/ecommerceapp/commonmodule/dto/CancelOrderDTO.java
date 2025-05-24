package com.ecommerceapp.commonmodule.dto;

import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
public class CancelOrderDTO {
    @NotBlank(message = "Order ID cannot be blank")
    private Long orderId;
    @NotBlank(message = "Cancel reason cannot be blank")
    private String cancelReason;
}
