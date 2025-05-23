package com.ecommerceapp.orderservice.service;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.commonmodule.dto.CancelOrderDTO;
import com.ecommerceapp.commonmodule.dto.OrderDTO;
import com.ecommerceapp.commonmodule.enums.OrderEnums;
import com.ecommerceapp.commonmodule.saga.event.OrderCreatedEvent;
import com.ecommerceapp.commonmodule.saga.event.OrderFailedEvent;
import com.ecommerceapp.orderservice.entity.Order;
import com.ecommerceapp.orderservice.mapper.OrderMapper;
import com.ecommerceapp.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService extends BaseService<Order, OrderDTO, OrderDTO, Long> {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public BaseRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    @Override
    public BaseMapper<Order, OrderDTO, OrderDTO> getMapper() {
        return OrderMapper.INSTANCE;
    }

    public List<OrderDTO> getByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(getMapper()::toDTO).collect(Collectors.toList());
    }

    @Override
    public void postCreate(Order obj, OrderDTO input, OrderDTO output) throws RuntimeException {
        // Send order created event to Kafka
        kafkaTemplate.send("order-created", OrderCreatedEvent.builder().orderId(obj.getId()).productId(obj.getProductId()).build());
    }

    public void cancelOrder(CancelOrderDTO dto) {
        orderRepository.findById(dto.getOrderId()).ifPresent(order -> {
            order.setStatus(OrderEnums.OrderStatus.CANCELLED);
            order.setCancelReason(dto.getCancelReason());
            orderRepository.save(order);
        });

        // Publish order cancelled event
        kafkaTemplate.send("order-failed", OrderFailedEvent.builder().orderId(dto.getOrderId()).build());
    }
}
