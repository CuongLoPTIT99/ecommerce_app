package com.ecommerceapp.shipmentservice.repository;

import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.shipmentservice.entity.Shipment;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends BaseRepository<Shipment, Long>  {
}
