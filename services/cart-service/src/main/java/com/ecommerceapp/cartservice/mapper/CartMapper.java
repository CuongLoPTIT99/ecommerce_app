package com.ecommerceapp.cartservice.mapper;

import com.ecommerceapp.cartservice.entity.Cart;
import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.dto.CartDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartMapper extends BaseMapper<Cart, CartDTO, CartDTO> {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "createAt", target = "createAt")
    CartDTO toDTO(Cart cart);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "createAt", target = "createAt")
    Cart fromDTO(CartDTO dto);
}
