package com.ecommerceapp.cartservice.mapper;

import com.ecommerceapp.cartservice.entity.Cart;
import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.dto.CartDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper extends BaseMapper<Cart, CartDTO, CartDTO> {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "status", target = "status")
    CartDTO toDTO(Cart cart);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "status", target = "status")
    Cart fromDTO(CartDTO dto);
}
