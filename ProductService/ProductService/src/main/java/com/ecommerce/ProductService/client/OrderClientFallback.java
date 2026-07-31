package com.ecommerce.ProductService.client;

import com.ecommerce.ProductService.model.OrderDetails;
import com.ecommerce.ProductService.model.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderClientFallback implements OrderClient{
    @Override
    public OrderResponse addOrderDetails(OrderDetails orderDetails) {
        return OrderResponse.builder().status("Not Created").build();
    }
}
