package com.ecommerceapp.cartservice.service;

import com.ecommerceapp.cartservice.entity.Cart;
import com.ecommerceapp.cartservice.mapper.CartMapper;
import com.ecommerceapp.cartservice.repository.CartRepository;
import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.commonmodule.dto.CartDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService extends BaseService<Cart, CartDTO, CartDTO, Long> {
    private final CartRepository cartRepository;
    @Override
    public BaseRepository<Cart, Long> getRepository() {
        return cartRepository;
    }

    @Override
    public BaseMapper<Cart, CartDTO, CartDTO> getMapper() {
        return CartMapper.INSTANCE;
    }

    public List<CartDTO> getByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId).stream()
                .map(getMapper()::toDTO).collect(Collectors.toList());
    }
}
