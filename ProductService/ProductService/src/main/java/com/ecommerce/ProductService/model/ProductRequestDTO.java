package com.ecommerce.ProductService.model;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    private String description;
    private String name;
    private double price;
    private String category;
    private String sellerEmailId;
}
