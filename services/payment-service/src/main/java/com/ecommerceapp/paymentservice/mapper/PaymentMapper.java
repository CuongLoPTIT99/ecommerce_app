package com.ecommerceapp.paymentservice.mapper;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.dto.PaymentDTO;
import com.ecommerceapp.paymentservice.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper extends BaseMapper<Payment, PaymentDTO> {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "status", target = "status")
    PaymentDTO toDTO(Payment payment);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "status", target = "status")
    Payment fromDTO(PaymentDTO dto);
}
