package com.ecommerceapp.customerservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class Address {
    private String street;
    private String city;
    private String state;
}
