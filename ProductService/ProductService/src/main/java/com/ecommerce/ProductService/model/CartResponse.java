package com.ecommerce.ProductService.model;

import com.ecommerce.ProductService.entity.CartItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

    private String userId; // Associate cart with user

    private List<CartItemResponse> items;

    private double totalPrice;
}
