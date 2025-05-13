package com.ecommerceapp.productservice.repository;

import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.dto.ProductDTO;
import com.ecommerceapp.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, String> {
     @Query("SELECT new com.ecommerceapp.commonmodule.dto.ProductDTO(p.id, p.name, p.brand, p.status) FROM Product p")
     Page<ProductDTO> findAllByPaging(Pageable pageable);
}
