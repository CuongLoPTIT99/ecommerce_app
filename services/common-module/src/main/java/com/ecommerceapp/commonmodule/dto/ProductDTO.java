package com.ecommerceapp.commonmodule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private Double price;
    private String imageUrl;
    private String status;

    public ProductDTO(Long id, String name, String brand, String description, Double price, String imageUrl, String status) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
    }
}
