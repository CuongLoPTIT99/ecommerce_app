package com.ecommerceapp.orderservice.repository;

import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.orderservice.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order, String>  {
}
