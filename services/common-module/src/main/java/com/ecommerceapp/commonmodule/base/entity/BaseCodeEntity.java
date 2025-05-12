package com.ecommerceapp.commonmodule.base.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseCodeEntity implements Serializable {
    private String code;
}
