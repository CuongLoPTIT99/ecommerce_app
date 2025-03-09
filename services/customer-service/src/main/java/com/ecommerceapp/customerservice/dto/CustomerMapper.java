package com.ecommerceapp.customerservice.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

//    @Mapping(source = "maDuLieu", target = "maDuLieu")
//    @Mapping(source = "tenHienThi", target = "tenDuLieu")
//    @Mapping(source = "giaTri", target = "giaTri", qualifiedByName = "stringToLong")
//    CustomerResponseDTO toGioiTinhDTO(Customer customer);

}
