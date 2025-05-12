package com.ecommerceapp.commonmodule.base.entity;

import jakarta.persistence.Column;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class BaseTimeRecordEntity implements Serializable {
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
