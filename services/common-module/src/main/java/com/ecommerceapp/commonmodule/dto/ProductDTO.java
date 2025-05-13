package com.ecommerceapp.commonmodule.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private String status;

    public ProductDTO(Long id, String name, String brand, String status) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.status = status;
    }
}
