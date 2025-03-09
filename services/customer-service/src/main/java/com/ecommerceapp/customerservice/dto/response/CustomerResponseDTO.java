package com.ecommerceapp.customerservice.dto.response;

import com.ecommerceapp.customerservice.entity.Address;
import com.ecommerceapp.customerservice.entity.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponseDTO {
    private String name;
    private String email;
    private Address address;
    private String phoneNumber;
    private Timestamp dateOfBirth;

    public static CustomerResponseDTO from(Customer customer) {
        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        responseDTO.setName(customer.getName());
        responseDTO.setEmail(customer.getEmail());
        responseDTO.setAddress(customer.getAddress());
        responseDTO.setPhoneNumber(customer.getPhoneNumber());
        responseDTO.setDateOfBirth(customer.getDateOfBirth());
        return responseDTO;
    }

    public static Customer to(CustomerResponseDTO responseDTO) {
        Customer customer = new Customer();
        customer.setName(responseDTO.getName());
        customer.setEmail(responseDTO.getEmail());
        customer.setAddress(responseDTO.getAddress());
        customer.setPhoneNumber(responseDTO.getPhoneNumber());
        customer.setDateOfBirth(responseDTO.getDateOfBirth());
        return customer;
    }
}
