package com.ecommerceapp.orderservice.service;

import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.orderservice.entity.Order;
import com.ecommerceapp.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService extends BaseService<Order, String> {
    private final OrderRepository orderRepository;
    @Override
    public BaseRepository<Order, String> getRepository() {
        return orderRepository;
    }
}
