package com.ecommerce.ProductService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        private String userId; // Associate cart with user

        @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<CartItem> items;

        private double totalPrice;
    }

