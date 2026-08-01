package com.ecommerce.InventoryService.model;


import jakarta.persistence.Entity;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequestDTO {
    public String sellerId;
    public String productId;
    public int quantity;
}
