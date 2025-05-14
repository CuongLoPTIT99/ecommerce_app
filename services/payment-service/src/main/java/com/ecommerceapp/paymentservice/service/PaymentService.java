package com.ecommerceapp.paymentservice.service;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.commonmodule.dto.InventoryDTO;
import com.ecommerceapp.commonmodule.dto.PaymentDTO;
import com.ecommerceapp.paymentservice.entity.Payment;
import com.ecommerceapp.paymentservice.mapper.PaymentMapper;
import com.ecommerceapp.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService extends BaseService<Payment, PaymentDTO, Long> {
    private final PaymentRepository paymentRepository;

    @Override
    public BaseRepository<Payment, Long> getRepository() {
        return paymentRepository;
    }

    @Override
    public BaseMapper<Payment, PaymentDTO> getMapper() {
        return PaymentMapper.INSTANCE;
    }
}
