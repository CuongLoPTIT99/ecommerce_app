package com.ecommerceapp.inventoryservice.controller;

import com.ecommerceapp.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
}
