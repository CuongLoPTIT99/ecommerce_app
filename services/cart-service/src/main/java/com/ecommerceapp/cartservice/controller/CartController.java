package com.ecommerceapp.cartservice.controller;

import com.ecommerceapp.cartservice.entity.Cart;
import com.ecommerceapp.cartservice.service.CartService;
import com.ecommerceapp.commonmodule.dto.CartDTO;
import com.ecommerceapp.commonmodule.dto.ProductDTO;
import com.ecommerceapp.commonmodule.network.api.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {
    private final CartService cartService;

    @PostMapping("")
    public ResponseEntity<ResponseWrapper> create(@Valid @RequestBody CartDTO cartDTO) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            CartDTO created = cartService.create(cartDTO);
            rw.setData(created);
            rw.setMessage("Cart created successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody  CartDTO cartDTO) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            CartDTO updated = cartService.update(cartDTO);
            rw.setData(updated);
            rw.setMessage("Cart updated successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            cartService.deleteById(id);
            rw.setMessage("Cart deleted successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getByCustomer(@PathVariable Long customerId) {
        ResponseWrapper rw = new ResponseWrapper();
        try {
            List<CartDTO> result = cartService.getByCustomerId(customerId);
            rw.setData(result);
            rw.setMessage("Get list carts for customer successfully");
            rw.setIsSuccess(true);
        } catch (Exception e) {
            rw.setMessage(e.getMessage());
            rw.setIsSuccess(false);
        }
        return ResponseEntity.ok(rw);
    }

    @GetMapping("/my-cart")
    public Page<CartDTO> getAll(
            @RequestParam(defaultValue = "") Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return cartService.getByCustomerIdAndPaging(customerId, page, size);
    }
}
