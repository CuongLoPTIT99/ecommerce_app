package com.ecommerceapp.cartservice.service;

import com.ecommerceapp.cartservice.entity.Cart;
import com.ecommerceapp.cartservice.mapper.CartMapper;
import com.ecommerceapp.cartservice.repository.CartRepository;
import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.commonmodule.dto.CartDTO;
import com.ecommerceapp.commonmodule.dto.ProductDTO;
import com.ecommerceapp.commonmodule.network.api.service.APIProductService;
import com.ecommerceapp.commonmodule.util.CommonUtil;
import com.ecommerceapp.commonmodule.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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
    private final APIProductService apiProductService;
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
        } else {
            // set created time if create first time
            obj.setCreatedAt(DateTimeUtil.getCurrentTimeStamp());
        }
        return obj;
    }

    @Override
    public Cart preUpdate(Cart obj, Cart current, CartDTO input) throws RuntimeException {
        BeanUtils.copyProperties(current, obj, "quantity");
        return obj;
    }

    public List<CartDTO> getByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId).stream()
                .map(getMapper()::toDTO).collect(Collectors.toList());
    }

    public Page<CartDTO> getByCustomerIdAndPaging(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<CartDTO> cartPage = cartRepository.findByCustomerIdAndPaging(pageable, customerId);
        List<Long> productIds = cartPage.getContent().stream()
                .map(CartDTO::getProductId).collect(Collectors.toList());

        // get list product by list id
        List<ProductDTO> productDTOList = apiProductService.getByListId(productIds);

        // set product to cartDTO
        if (!CommonUtil.isNullOrEmpty(productDTOList)) {
            cartPage.getContent().forEach(cartDTO -> {
                Optional<ProductDTO> productDTO = productDTOList.stream()
                        .filter(p -> p.getId().equals(cartDTO.getProductId())).findFirst();
                if (productDTO.isPresent()) {
                    cartDTO.setProduct(productDTO.get());
                }
            });
        }

        return cartPage;
    }
}
