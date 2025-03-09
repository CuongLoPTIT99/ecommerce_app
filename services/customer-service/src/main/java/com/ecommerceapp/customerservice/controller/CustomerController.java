package com.ecommerceapp.customerservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
//    private final CustomerService customerService;

//    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
//        return ResponseEntity.ok(customerService.createCustomer(customerRequestDTO));
//    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
//        return ResponseEntity.ok(customerService.getCustomerById(id));
        return ResponseEntity.ok("hello");
    }

//    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
//        return ResponseEntity.ok(customerService.updateCustomer(customerRequestDTO));
//    }

}
