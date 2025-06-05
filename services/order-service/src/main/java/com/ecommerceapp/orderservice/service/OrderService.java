package com.ecommerceapp.orderservice.service;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.commonmodule.base.service.MailService;
import com.ecommerceapp.commonmodule.base.service.NotificationService;
import com.ecommerceapp.commonmodule.dto.*;
import com.ecommerceapp.commonmodule.enums.OrderEnums;
import com.ecommerceapp.commonmodule.network.api.service.APIProductService;
import com.ecommerceapp.commonmodule.saga.event.OrderCreatedEvent;
import com.ecommerceapp.commonmodule.saga.event.OrderFailedEvent;
import com.ecommerceapp.commonmodule.util.CommonUtil;
import com.ecommerceapp.commonmodule.util.DateTimeUtil;
import com.ecommerceapp.orderservice.entity.Order;
import com.ecommerceapp.orderservice.mapper.OrderMapper;
import com.ecommerceapp.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService extends BaseService<Order, OrderDTO, OrderDTO, Long> {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final APIProductService apiProductService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final NotificationService notificationService;
    private final MailService mailService;

    @Override
    public BaseRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    @Override
    public BaseMapper<Order, OrderDTO, OrderDTO> getMapper() {
        return orderMapper;
    }

    public List<OrderDTO> getByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(getMapper()::toDTO).collect(Collectors.toList());
    }

    @Override
    public Order preCreate(Order obj, OrderDTO input) throws RuntimeException {
        obj.setCreatedAt(DateTimeUtil.getCurrentTimeStamp());
        obj.setStatus(OrderEnums.OrderStatus.CREATED);
        return obj;
    }

    @Override
    public OrderDTO postCreate(Order obj, OrderDTO input, OrderDTO output) throws RuntimeException {
        // Send order created event to Kafka
        kafkaTemplate.send("order-created", OrderCreatedEvent.builder().orderId(obj.getId()).productId(obj.getProductId()).build());

        // Send notification of order created
//        notificationService.sendPushNotification(
//                NotificationMessageDTO.builder()
//                        .title("Order Created")
//                        .content("Your order has been created successfully.")
//                        .recipientId(obj.getCustomerId())
//                        .createdAt(Timestamp.from(Instant.now())).build()
//        );
        return output;
    }

    @Override
    public Order preUpdate(Order obj, Order current, OrderDTO input) throws RuntimeException {
        obj.setUpdatedAt(DateTimeUtil.getCurrentTimeStamp());
        return obj;
    }

    public void cancelOrder(CancelOrderDTO dto) {
        orderRepository.findById(dto.getOrderId()).ifPresent(order -> {
            order.setStatus(OrderEnums.OrderStatus.CANCELLED);
            order.setCancelReason(dto.getCancelReason());
            order.setUpdatedAt(DateTimeUtil.getCurrentTimeStamp());
            orderRepository.save(order);
        });

        // Publish order cancelled event
//        kafkaTemplate.send("order-failed", OrderFailedEvent.builder().orderId(dto.getOrderId()).build());
    }

    public Page<OrderDTO> getByCustomerIdAndPaging(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<OrderDTO> orderPage = orderRepository.findByCustomerIdAndPaging(pageable, customerId);
        List<Long> productIds = orderPage.getContent().stream()
                .map(OrderDTO::getProductId).collect(Collectors.toList());

        // get list product by list id
        List<ProductDTO> productDTOList = apiProductService.getByListId(productIds);

        // set product to cartDTO
        if (!CommonUtil.isNullOrEmpty(productDTOList)) {
            orderPage.getContent().forEach(orderDTO -> {
                Optional<ProductDTO> productDTO = productDTOList.stream()
                        .filter(c -> c.getId().equals(orderDTO.getProductId())).findFirst();
                if (productDTO.isPresent()) {
                    orderDTO.setProduct(productDTO.get());
                }
            });
        }

        return orderPage;
    }
}
