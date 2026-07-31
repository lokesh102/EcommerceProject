package com.ecommerce.ProductService.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {
    private Long id;
    private String productId;
    private String productName;
    private double price;
    private int quantity;
    private String sellerId;
}
