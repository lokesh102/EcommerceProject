package com.ecommerce.InventoryService.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    int quantity;
    String message;
}
