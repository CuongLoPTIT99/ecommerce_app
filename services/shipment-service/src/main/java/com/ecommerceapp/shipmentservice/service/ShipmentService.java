package com.ecommerceapp.shipmentservice.service;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.commonmodule.dto.InventoryDTO;
import com.ecommerceapp.commonmodule.dto.ShipmentDTO;
import com.ecommerceapp.shipmentservice.entity.Shipment;
import com.ecommerceapp.shipmentservice.mapper.ShipmentMapper;
import com.ecommerceapp.shipmentservice.repository.ShipmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ShipmentService extends BaseService<Shipment, ShipmentDTO, Long> {
    private final ShipmentRepository shipmentRepository;

    @Override
    public BaseRepository<Shipment, Long> getRepository() {
        return shipmentRepository;
    }

    @Override
    public BaseMapper<Shipment, ShipmentDTO> getMapper() {
        return ShipmentMapper.INSTANCE;
    }
}
