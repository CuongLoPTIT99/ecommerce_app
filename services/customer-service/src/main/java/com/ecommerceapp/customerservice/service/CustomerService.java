package com.ecommerceapp.customerservice.service;

import com.ecommerceapp.customerservice.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer getCustomerById(String id);
    Customer updateCustomer(Customer customer);
    void deleteCustomer(String id);
    List<Customer> getAllCustomers();
}
