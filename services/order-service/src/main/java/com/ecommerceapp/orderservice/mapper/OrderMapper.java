package com.ecommerceapp.orderservice.mapper;

import com.ecommerceapp.commonmodule.dto.OrderDTO;
import com.ecommerceapp.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "status", target = "status")
    OrderDTO toOrderDTO(Order order);
}
