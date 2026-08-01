package com.ecommerce.InventoryService.controller;



import com.ecommerce.InventoryService.entity.Inventory;
import com.ecommerce.InventoryService.model.InventoryRequestDTO;
import com.ecommerce.InventoryService.model.InventoryResponse;
import com.ecommerce.InventoryService.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Operation(summary = "Check if product is in stock", description = "Returns true if the product is in stock")
    @ApiResponse(responseCode = "200", description = "Product is in stock")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/product/{productId}")
    public boolean isProductInStock(@Parameter(description = "ID of the product to check stock") @PathVariable String productId) {
        return inventoryService.isInStock(productId);
    }

    @Operation(summary = "Get all inventory", description = "Fetches the complete list of inventory")
    @ApiResponse(responseCode = "200", description = "List of inventory fetched successfully")
    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @Operation(summary = "Get inventory by seller ID", description = "Fetches all inventory items by seller ID")
    @ApiResponse(responseCode = "200", description = "List of inventory for the seller fetched successfully")
    @ApiResponse(responseCode = "404", description = "Seller not found")
    @GetMapping("/seller/{sellerId}")
    public List<Inventory> getAllInventoryBySeller(@Parameter(description = "ID of the seller to fetch inventory") @PathVariable String sellerId) {
        return inventoryService.getAllInventoryBySeller(sellerId);
    }

    @Operation(summary = "Add stock to inventory", description = "Adds a new stock entry to the inventory")
    @ApiResponse(responseCode = "201", description = "Stock added successfully")
    @PostMapping("/add")
    public String addStock(@RequestBody InventoryRequestDTO inventory) {

        inventoryService.addStock(Inventory.builder().quantity(inventory.getQuantity()).sellerId(inventory.sellerId).productId(inventory.getProductId()).build());
        return "Stock added successfully!";
    }

    @Operation(summary = "Update stock", description = "Updates the stock quantity for a product")
    @ApiResponse(responseCode = "200", description = "Stock updated successfully")
    @PutMapping("/update/{productId}/{quantity}")
    public String updateStock(@Parameter(description = "ID of the product") @PathVariable String productId,
                              @Parameter(description = "Quantity of stock to update") @PathVariable int quantity) {
        inventoryService.updateStock(productId, quantity);
        return "Stock updated successfully!";
    }

    @Operation(summary = "Decrease stock", description = "Decreases the stock for a product and returns the updated inventory details")
    @ApiResponse(responseCode = "200", description = "Stock decreased successfully")
    @PutMapping("/decrease/{productId}")
    public InventoryResponse decreaseStock(@Parameter(description = "ID of the product to decrease stock") @PathVariable String productId) {
        return inventoryService.decreaseStock(productId);
    }
}