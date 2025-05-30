package com.ecommerceapp.cartservice.service;

import com.ecommerceapp.cartservice.entity.Cart;
import com.ecommerceapp.cartservice.mapper.CartMapper;
import com.ecommerceapp.cartservice.repository.CartRepository;
import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.commonmodule.dto.CartDTO;
import com.ecommerceapp.commonmodule.util.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService extends BaseService<Cart, CartDTO, CartDTO, Long> {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    @Override
    public CartRepository getRepository() {
        return cartRepository;
    }

    @Override
    public BaseMapper<Cart, CartDTO, CartDTO> getMapper() {return cartMapper;}

    @Override
    public Cart preCreate(Cart obj, CartDTO input) throws RuntimeException {
        List<Cart> cart = cartRepository.findByCustomerIdAndProductId(input.getCustomerId(), input.getProductId());
        if (!CommonUtil.isNullOrEmpty(cart)) {
            obj = cart.get(0);
            obj.setQuantity(obj.getQuantity() + input.getQuantity());
        }
        return obj;
    }

    public List<CartDTO> getByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId).stream()
                .map(getMapper()::toDTO).collect(Collectors.toList());
    }

    public Page<CartDTO> getByCustomerIdAndPaging(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createAt").descending());
        List<Long> productIds = cartRepository.findByCustomerIdAndPaging(pageable, customerId).getContent().stream()
                .map(CartDTO::getProductId).collect(Collectors.toList());
        return cartRepository.findByCustomerIdAndPaging(pageable, customerId);
    }
}
