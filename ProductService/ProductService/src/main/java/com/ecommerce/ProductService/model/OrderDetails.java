package com.ecommerce.ProductService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class OrderDetails {

        private Long orderId ;
        private String userId;
        private double totalAmount;

        @OneToMany(mappedBy = "orderDetails", cascade = CascadeType.ALL)
        private List<OrderItem> orderItems;


}
