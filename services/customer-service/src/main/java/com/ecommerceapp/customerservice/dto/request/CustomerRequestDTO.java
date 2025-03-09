package com.ecommerceapp.customerservice.dto.request;

import com.ecommerceapp.customerservice.entity.Address;
import com.ecommerceapp.customerservice.entity.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerRequestDTO {
    private String name;
    private String email;
    private Address address;
    private String phoneNumber;
    private Timestamp dateOfBirth;

    public static CustomerRequestDTO from(Customer customer) {
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
        customerRequestDTO.setName(customer.getName());
        customerRequestDTO.setEmail(customer.getEmail());
        customerRequestDTO.setAddress(customer.getAddress());
        customerRequestDTO.setPhoneNumber(customer.getPhoneNumber());
        customerRequestDTO.setDateOfBirth(customer.getDateOfBirth());
        return customerRequestDTO;
    }

    public static Customer to(CustomerRequestDTO customerRequestDTO) {
        Customer customer = new Customer();
        customer.setName(customerRequestDTO.getName());
        customer.setEmail(customerRequestDTO.getEmail());
        customer.setAddress(customerRequestDTO.getAddress());
        customer.setPhoneNumber(customerRequestDTO.getPhoneNumber());
        customer.setDateOfBirth(customerRequestDTO.getDateOfBirth());
        return customer;
    }
}
