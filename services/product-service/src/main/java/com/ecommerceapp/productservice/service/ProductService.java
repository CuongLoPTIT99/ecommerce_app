package com.ecommerceapp.productservice.service;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.commonmodule.dto.InventoryDTO;
import com.ecommerceapp.commonmodule.dto.ProductDTO;
import com.ecommerceapp.productservice.entity.Product;
import com.ecommerceapp.productservice.mapper.ProductMapper;
import com.ecommerceapp.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService extends BaseService<Product, ProductDTO, ProductDTO, Long> {
    private final ProductRepository productRepository;

    @Override
    public BaseRepository<Product, Long> getRepository() {
        return productRepository;
    }

    @Override
    public BaseMapper<Product, ProductDTO, ProductDTO> getMapper() {
        return ProductMapper.INSTANCE;
    }

    public Page<ProductDTO> filterAndPaging(String filterText, int page, int size, String sortBy, String sortType) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return productRepository.findAllByPaging(pageable);
    }
}
