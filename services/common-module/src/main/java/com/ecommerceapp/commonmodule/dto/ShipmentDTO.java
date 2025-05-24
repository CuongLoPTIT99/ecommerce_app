package com.ecommerceapp.commonmodule.dto;

import lombok.Data;

@Data
public class ShipmentDTO {
    private Long id;
    private Long orderId;
    private String phoneNumber;
    private String city;
    private String district;
    private String ward;
    private String streetAddress;

    public ShipmentDTO(Long id, Long orderId, String phoneNumber, String city, String district, String ward, String streetAddress) {
        this.id = id;
        this.orderId = orderId;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.streetAddress = streetAddress;
    }
}
