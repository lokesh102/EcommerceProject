package com.ecommerce.ProductService.model;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    String status;
    String productName;
    String productId;
}
