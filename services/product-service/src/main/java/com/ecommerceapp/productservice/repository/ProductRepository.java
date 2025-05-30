package com.ecommerceapp.productservice.repository;

import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.dto.ProductDTO;
import com.ecommerceapp.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
     @Query("SELECT new com.ecommerceapp.commonmodule.dto.ProductDTO(p.id, p.name, p.brand, p.description, p.price, p.imageUrl, p.status) FROM Product p " +
             "where (:filterByName is null or :filterByName = '' or lower(p.name) like lower(concat('%', lower(:filterByName), '%')))")
     Page<ProductDTO> findAllByPaging(Pageable pageable, String filterByName);

     List<ProductDTO> findAllByIdIn(List<Long> id);
}
