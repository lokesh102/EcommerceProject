package com.ecommerce.ProductService.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerRequestDTO {

    private String sellerName;
    private String email;
    private String phone;
}
