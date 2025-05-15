package com.ecommerceapp.paymentservice.controller;

import com.ecommerceapp.commonmodule.dto.PaymentDTO;
import com.ecommerceapp.commonmodule.network.api.ResponseWrapper;
import com.ecommerceapp.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<ResponseWrapper> create(@RequestBody PaymentDTO paymentDTO) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            PaymentDTO created = paymentService.create(paymentDTO);
            rw.setData(created);
            rw.setMessage("Payment created successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody  PaymentDTO paymentDTO) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            PaymentDTO updated = paymentService.update(paymentDTO);
            rw.setData(updated);
            rw.setMessage("Payment updated successfully");
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
            paymentService.deleteById(id);
            rw.setMessage("Payment deleted successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }
}
