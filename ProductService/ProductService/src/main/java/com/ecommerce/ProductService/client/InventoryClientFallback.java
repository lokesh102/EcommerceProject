package com.ecommerce.ProductService.client;

import org.springframework.stereotype.Component;

@Component
public class InventoryClientFallback implements InventoryClient{
    @Override
    public Boolean isProductInStock(String productId) {
        return false;
    }
}
