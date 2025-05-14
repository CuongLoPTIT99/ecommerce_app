package com.ecommerceapp.inventoryservice.repository;

import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.inventoryservice.entity.Inventory;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends BaseRepository<Inventory, Long>  {
}
