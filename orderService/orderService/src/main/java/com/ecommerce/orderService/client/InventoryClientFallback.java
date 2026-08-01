package com.ecommerce.orderService.client;

import com.ecommerce.orderService.models.InventoryResponse;
import org.springframework.stereotype.Component;

@Component
public class InventoryClientFallback implements InventoryClient{

    @Override
    public InventoryResponse decreaseStock(String productId) {
        return InventoryResponse.builder().message("Unexpected Response from Inventory Service").build();
    }
}
