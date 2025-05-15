package com.ecommerceapp.inventoryservice.service;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.commonmodule.dto.CartDTO;
import com.ecommerceapp.commonmodule.dto.InventoryDTO;
import com.ecommerceapp.inventoryservice.entity.Inventory;
import com.ecommerceapp.inventoryservice.mapper.InventoryMapper;
import com.ecommerceapp.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryService extends BaseService<Inventory, InventoryDTO, InventoryDTO, Long> {
    private final InventoryRepository inventoryRepository;
    @Override
    public BaseRepository<Inventory, Long> getRepository() {
        return inventoryRepository;
    }

    @Override
    public BaseMapper<Inventory, InventoryDTO, InventoryDTO> getMapper() {
        return InventoryMapper.INSTANCE;
    }
}
