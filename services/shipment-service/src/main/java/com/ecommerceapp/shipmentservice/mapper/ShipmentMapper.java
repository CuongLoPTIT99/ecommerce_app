package com.ecommerceapp.shipmentservice.mapper;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.dto.ShipmentDTO;
import com.ecommerceapp.shipmentservice.entity.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShipmentMapper extends BaseMapper<Shipment, ShipmentDTO> {
    ShipmentMapper INSTANCE = Mappers.getMapper(ShipmentMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "district", target = "district")
    @Mapping(source = "ward", target = "ward")
    @Mapping(source = "streetAddress", target = "streetAddress")
    ShipmentDTO toDTO(Shipment shipment);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "district", target = "district")
    @Mapping(source = "ward", target = "ward")
    @Mapping(source = "streetAddress", target = "streetAddress")
    Shipment fromDTO(ShipmentDTO dto);
}
