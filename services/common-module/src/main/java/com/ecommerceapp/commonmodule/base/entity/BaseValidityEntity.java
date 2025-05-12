package com.ecommerceapp.commonmodule.base.entity;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public abstract class BaseValidityEntity implements Serializable {
    @Column(name = "valid_from")
    private Timestamp validFrom;
    @Column(name = "valid_to")
    private Timestamp validTo;
}
