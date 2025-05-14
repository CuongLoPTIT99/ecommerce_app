package com.ecommerceapp.orderservice.repository;

import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.orderservice.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long>  {
    List<Order> findByCustomerId(Long customerId);
}
