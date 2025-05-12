package com.ecommerceapp.commonmodule.base.entity;

public interface BaseEntity<Tid extends Object> {
    void setId(Tid id);

    Tid getId();
}
