package com.ecommerceapp.customerservice.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@Getter
@Setter
@Document(collection = "customer")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String id;
    private String name;
    private String email;
    private Address address;
    private String phoneNumber;
    private Timestamp dateOfBirth;
}
