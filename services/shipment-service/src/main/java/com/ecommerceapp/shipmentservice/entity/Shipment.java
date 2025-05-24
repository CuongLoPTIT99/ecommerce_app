package com.ecommerceapp.shipmentservice.entity;

import com.ecommerceapp.commonmodule.base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shipment")
public class Shipment implements BaseEntity<Long>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String city;
    private String district;
    private String ward;
    @Column(name = "street_address")
    private String streetAddress;
}
