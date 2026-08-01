package com.ecommerce.orderService.client;


import com.ecommerce.orderService.models.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "INVENTORYSERVICE" ,fallback = InventoryClientFallback.class)
public interface InventoryClient {

    @PutMapping("/inventory/decrease/{productId}")
    public InventoryResponse decreaseStock(@PathVariable("productId") String productId);
}
