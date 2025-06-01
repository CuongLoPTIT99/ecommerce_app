package com.ecommerceapp.cartservice.repository;

import com.ecommerceapp.cartservice.entity.Cart;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.dto.CartDTO;
import com.ecommerceapp.commonmodule.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends BaseRepository<Cart, Long>  {
    List<Cart> findByCustomerId(Long customerId);
    List<Cart> findByCustomerIdAndProductId(Long customerId, Long productId);
    @Query("SELECT new com.ecommerceapp.commonmodule.dto.CartDTO(c.id, c.customerId, c.productId, c.quantity, c.totalPrice, c.createdAt) FROM Cart c " +
            "where c.customerId = :customerId")
    Page<CartDTO> findByCustomerIdAndPaging(Pageable pageable, Long customerId);
}
