package com.ecommerceapp.orderservice.service;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.commonmodule.dto.CartDTO;
import com.ecommerceapp.commonmodule.dto.InventoryDTO;
import com.ecommerceapp.commonmodule.dto.OrderDTO;
import com.ecommerceapp.orderservice.entity.Order;
import com.ecommerceapp.orderservice.mapper.OrderMapper;
import com.ecommerceapp.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService extends BaseService<Order, OrderDTO, Long> {
    private final OrderRepository orderRepository;

    @Override
    public BaseRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    @Override
    public BaseMapper<Order, OrderDTO> getMapper() {
        return OrderMapper.INSTANCE;
    }

    public List<OrderDTO> getByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(getMapper()::toDTO).collect(Collectors.toList());
    }
}
