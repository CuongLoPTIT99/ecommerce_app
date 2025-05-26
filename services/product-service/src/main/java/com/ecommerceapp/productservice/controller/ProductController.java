package com.ecommerceapp.productservice.controller;

import com.ecommerceapp.commonmodule.dto.ProductDTO;
import com.ecommerceapp.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/list")
    public Page<ProductDTO> getAll(
            @RequestParam(defaultValue = "") String filterByName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortType
    ) {
        return productService.filterAndPaging(filterByName, page, size, sortBy, sortType);
    }
}
