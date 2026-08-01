package com.ecommerce.orderService.models;

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
