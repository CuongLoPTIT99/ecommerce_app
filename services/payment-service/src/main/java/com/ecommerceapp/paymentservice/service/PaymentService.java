package com.ecommerceapp.paymentservice.service;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.commonmodule.dto.InventoryDTO;
import com.ecommerceapp.commonmodule.dto.OrderDTO;
import com.ecommerceapp.commonmodule.dto.PaymentDTO;
import com.ecommerceapp.commonmodule.saga.event.OrderCreatedEvent;
import com.ecommerceapp.commonmodule.saga.event.OrderFailedEvent;
import com.ecommerceapp.commonmodule.saga.event.PaymentCompletedEvent;
import com.ecommerceapp.commonmodule.saga.event.PaymentFailedEvent;
import com.ecommerceapp.paymentservice.entity.Payment;
import com.ecommerceapp.paymentservice.mapper.PaymentMapper;
import com.ecommerceapp.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService extends BaseService<Payment, PaymentDTO, PaymentDTO, Long> {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public BaseRepository<Payment, Long> getRepository() {
        return paymentRepository;
    }

    @Override
    public BaseMapper<Payment, PaymentDTO, PaymentDTO> getMapper() {
        return paymentMapper;
    }

    @Override
    public PaymentDTO postCreate(Payment obj, PaymentDTO input, PaymentDTO output) throws RuntimeException {
        // Send order created event to Kafka
        kafkaTemplate.send("payment-completed", PaymentCompletedEvent.builder().orderId(obj.getOrderId()).build());
        return output;
    }

    @Override
    public void postDeleteById(Payment obj, Long id) throws RuntimeException {
        kafkaTemplate.send("payment-failed", PaymentFailedEvent.builder().orderId(obj.getId()).build());
    }
}
