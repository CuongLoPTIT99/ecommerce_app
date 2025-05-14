package com.ecommerceapp.cartservice.repository;

import com.ecommerceapp.cartservice.entity.Cart;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends BaseRepository<Cart, Long>  {
    List<Cart> findByCustomerId(Long customerId);
}
