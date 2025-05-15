package com.ecommerceapp.commonmodule.base.mapper;

import com.ecommerceapp.commonmodule.base.entity.BaseEntity;

public interface BaseMapper<T extends BaseEntity, R extends Object, S extends Object> {
    T fromDTO(R dto);

    S toDTO(T obj);
}
