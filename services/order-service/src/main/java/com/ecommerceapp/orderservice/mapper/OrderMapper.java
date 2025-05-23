package com.ecommerceapp.orderservice.mapper;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.dto.CancelOrderDTO;
import com.ecommerceapp.commonmodule.dto.OrderDTO;
import com.ecommerceapp.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper extends BaseMapper<Order, OrderDTO, OrderDTO> {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "cancelReason", target = "cancelReason")
    OrderDTO toDTO(Order order);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "cancelReason", target = "cancelReason")
    Order fromDTO(OrderDTO dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "cancelReason", target = "cancelReason")
    Order fromCancelOrderDTO(CancelOrderDTO dto);
}
