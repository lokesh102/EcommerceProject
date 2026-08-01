package com.ecommerce.orderService.service;

import com.ecommerce.orderService.entity.OrderDetails;
import com.ecommerce.orderService.models.OrderResponse;

import java.util.List;

public interface OrderDetailsService {

    OrderResponse addOrderDetails(OrderDetails orderDetails);

    OrderResponse getOrderDetailsById(String id);

    List<OrderResponse> getOrderDetailsByUser(String userId);

    void updateOrderDetailsStatus(String id, String productId, String orderStatus);
}
