package com.ecommerce.ProductService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OrderItem{

    private Long id;
    private String sellerID;
    private String productID;
    private String productName;
    private double amount;
    private int quantity;
    private String status;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderDetails orderDetails;

}
