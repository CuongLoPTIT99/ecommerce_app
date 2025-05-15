package com.ecommerceapp.shipmentservice.controller;

import com.ecommerceapp.commonmodule.dto.ShipmentDTO;
import com.ecommerceapp.commonmodule.network.api.ResponseWrapper;
import com.ecommerceapp.shipmentservice.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipment")
@RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService shipmentService;

    @PostMapping("")
    public ResponseEntity<ResponseWrapper> create(@RequestBody ShipmentDTO shipmentDTO) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            ShipmentDTO created = shipmentService.create(shipmentDTO);
            rw.setData(created);
            rw.setMessage("Shipment created successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody  ShipmentDTO shipmentDTO) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            ShipmentDTO updated = shipmentService.update(shipmentDTO);
            rw.setData(updated);
            rw.setMessage("Shipment updated successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            shipmentService.deleteById(id);
            rw.setMessage("Shipment deleted successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }
}
