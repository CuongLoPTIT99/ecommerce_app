package com.ecommerceapp.inventoryservice.repository;

import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.inventoryservice.entity.Inventory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends BaseRepository<Inventory, Long>  {
    Optional<Inventory> findByProductId(Long productId);
}
