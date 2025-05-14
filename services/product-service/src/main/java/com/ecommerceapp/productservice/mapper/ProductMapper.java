package com.ecommerceapp.productservice.mapper;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.dto.ProductDTO;
import com.ecommerceapp.productservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper extends BaseMapper<Product, ProductDTO> {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "status", target = "status")
    ProductDTO toDTO(Product product);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "status", target = "status")
    ProductDTO fromDTO(Product product);
}
