package com.ecommerce.InventoryService.service;

import com.ecommerce.InventoryService.exception.ProductNotFoundException;
import com.ecommerce.InventoryService.entity.Inventory;
import com.ecommerce.InventoryService.model.InventoryMessage;
import com.ecommerce.InventoryService.model.InventoryResponse;
import com.ecommerce.InventoryService.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService{

        @Autowired
        private InventoryRepository inventoryRepository;

        @Override
        public boolean isInStock(String productId) {
            Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);
            return inventory.isPresent() && inventory.get().getQuantity() > 0;
        }

        @Override
        public List<Inventory> getAllInventory() {
            return inventoryRepository.findAll();
        }

        @Override
        public void addStock(Inventory inventory) {
            inventoryRepository.save(inventory);
        }

        @Override
        public void updateStock(String productId, int quantity) {
            Optional<Inventory> inventoryOptional = inventoryRepository.findByProductId(productId);
            if (inventoryOptional.isPresent()) {
                Inventory inventory = inventoryOptional.get();
                inventory.setQuantity(inventory.getQuantity() + quantity);
                inventoryRepository.save(inventory);
            } else {
                throw new ProductNotFoundException("Product not found in inventory");
            }
        }

    public InventoryResponse decreaseStock(String productId) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findByProductId(productId);
        InventoryResponse inventoryResponse = new InventoryResponse();
        if (inventoryOptional.isPresent()) {
            Inventory inventory = inventoryOptional.get();
            if(inventory.getQuantity()>1){
                inventoryResponse.setQuantity(inventory.getQuantity());
                inventoryResponse.setMessage(InventoryMessage.In_Stock.name());
                inventory.setQuantity(inventory.getQuantity()-1);
                inventoryRepository.save(inventory);
                return InventoryResponse.builder().quantity(inventory.getQuantity()).message(InventoryMessage.In_Stock.name()).build();
            }

        } else {
            throw new ProductNotFoundException("Product out of Stock in inventory");
        }

        return InventoryResponse.builder().quantity(0).message(InventoryMessage.Out_of_Stock.name()).build();
    }

    @Override
    public List<Inventory> getAllInventoryBySeller(String sellerId) {
        List<Inventory> inventoryOptional = inventoryRepository.getAllInventoryBySeller(sellerId);
        return inventoryOptional;
    }
}
