package com.ecommerceapp.orderservice.controller;

import com.ecommerceapp.commonmodule.dto.CancelOrderDTO;
import com.ecommerceapp.commonmodule.dto.CartDTO;
import com.ecommerceapp.commonmodule.dto.OrderDTO;
import com.ecommerceapp.commonmodule.network.api.ResponseWrapper;
import com.ecommerceapp.orderservice.mapper.OrderMapper;
import com.ecommerceapp.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<ResponseWrapper> create(@Valid @RequestBody OrderDTO orderDTO) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            OrderDTO created = orderService.create(orderDTO);
            rw.setData(created);
            rw.setMessage("Order created successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@Valid @RequestBody OrderDTO orderDTO) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            OrderDTO updated = orderService.update(orderDTO);
            rw.setData(updated);
            rw.setMessage("Order updated successfully");
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
            orderService.deleteById(id);
            rw.setMessage("Order deleted successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getByCustomer(@PathVariable Long customerId) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            List<OrderDTO> result = orderService.getByCustomerId(customerId);
            rw.setData(result);
            rw.setMessage("Get list orders for customer successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }

    @PostMapping("/cancel")
    public ResponseEntity<ResponseWrapper> cancel(@Valid @RequestBody CancelOrderDTO dto) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            orderService.cancelOrder(dto);
            rw.setMessage("Order canceled successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }
}
