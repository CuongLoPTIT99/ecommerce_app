package com.ecommerceapp.commonmodule.base.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseStatusEntity implements Serializable {
    private String status;
}
