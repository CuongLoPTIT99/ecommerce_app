package com.ecommerceapp.inventoryservice.service;

import com.ecommerceapp.commonmodule.base.mapper.BaseMapper;
import com.ecommerceapp.commonmodule.base.repository.BaseRepository;
import com.ecommerceapp.commonmodule.base.service.BaseService;
import com.ecommerceapp.commonmodule.dto.CartDTO;
import com.ecommerceapp.commonmodule.dto.InventoryDTO;
import com.ecommerceapp.commonmodule.dto.NotificationMessageDTO;
import com.ecommerceapp.commonmodule.dto.OrderDTO;
import com.ecommerceapp.commonmodule.saga.event.InventoryFailedEvent;
import com.ecommerceapp.commonmodule.saga.event.InventoryReservedEvent;
import com.ecommerceapp.commonmodule.saga.event.OrderCreatedEvent;
import com.ecommerceapp.commonmodule.saga.event.OrderFailedEvent;
import com.ecommerceapp.inventoryservice.entity.Inventory;
import com.ecommerceapp.inventoryservice.mapper.InventoryMapper;
import com.ecommerceapp.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryService extends BaseService<Inventory, InventoryDTO, InventoryDTO, Long> {
    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public BaseRepository<Inventory, Long> getRepository() {
        return inventoryRepository;
    }

    @Override
    public BaseMapper<Inventory, InventoryDTO, InventoryDTO> getMapper() {
        return InventoryMapper.INSTANCE;
    }

    @Override
    public void postCreate(Inventory obj, InventoryDTO input, InventoryDTO output) throws RuntimeException {
        // Send order created event to Kafka
        kafkaTemplate.send("inventory-reserved", InventoryReservedEvent.builder().orderId(obj.getId()).build());
    }

    @Override
    public void postDeleteById(Inventory obj, Long id) throws RuntimeException {
        kafkaTemplate.send("inventory-failed", InventoryFailedEvent.builder().orderId(obj.getId()).build());
    }

    @KafkaListener(topics = "order-created", groupId = "inventory-group")
    private void reserveStock(OrderCreatedEvent event) {
        Inventory inventory = inventoryRepository.findByProductId(event.getProductId()).orElse(null);
        if (inventory != null) {
            inventory.setQuantity(inventory.getQuantity() - event.getQuantity());
            inventoryRepository.save(inventory);
        }
    }
}
