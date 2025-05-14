package com.ecommerceapp.commonmodule.network.api;

import lombok.Data;

@Data
public class ResponseWrapper {
    private String message;
    private Object data;
    private Boolean isSuccess;
}
