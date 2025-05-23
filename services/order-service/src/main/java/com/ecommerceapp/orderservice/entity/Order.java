package com.ecommerceapp.orderservice.entity;

import com.ecommerceapp.commonmodule.base.entity.BaseEntity;
import com.ecommerceapp.commonmodule.enums.OrderEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order implements BaseEntity<Long>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "product_id")
    private Long productId;
    private Integer quantity;
    @Column(name = "total_price")
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderEnums.OrderStatus status;
    @Column(name = "cancel_reason")
    private String cancelReason;
}
