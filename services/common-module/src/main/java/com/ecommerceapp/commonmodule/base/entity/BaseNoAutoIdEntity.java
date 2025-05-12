package com.ecommerceapp.commonmodule.base.entity;

import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class BaseNoAutoIdEntity<Tid> implements BaseEntity<Tid>, Serializable {
    @Id
    private Tid id;
}
