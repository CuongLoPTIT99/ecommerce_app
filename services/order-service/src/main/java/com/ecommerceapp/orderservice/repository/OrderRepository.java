package com.ecommerceapp.orderservice.repository;

import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.dto.CartDTO;
import com.ecommerceapp.commonmodule.dto.OrderDTO;
import com.ecommerceapp.orderservice.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long>  {
    List<Order> findByCustomerId(Long customerId);

    @Query("SELECT new com.ecommerceapp.commonmodule.dto.OrderDTO(o.id, o.customerId, o.productId, o.quantity, o.totalPrice, o.status, o.createdAt, o.updatedAt) FROM Order o " +
            "where o.customerId = :customerId and o.status <> 'CANCELLED'")
    Page<OrderDTO> findByCustomerIdAndPaging(Pageable pageable, Long customerId);
}
