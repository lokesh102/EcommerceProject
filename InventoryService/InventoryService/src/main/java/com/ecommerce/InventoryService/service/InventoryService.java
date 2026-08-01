package com.ecommerce.InventoryService.service;

import com.ecommerce.InventoryService.entity.Inventory;
import com.ecommerce.InventoryService.model.InventoryResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryService {
    boolean isInStock(String productId);
    List<Inventory> getAllInventory();
    void addStock(Inventory inventory);
    void updateStock(String productId, int quantity);

    InventoryResponse decreaseStock(String productId);

    List<Inventory> getAllInventoryBySeller(@Param("sellerId") String sellerId);
}
