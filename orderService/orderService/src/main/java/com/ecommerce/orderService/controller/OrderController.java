package com.ecommerce.orderService.controller;

import com.ecommerce.orderService.entity.OrderDetails;
import com.ecommerce.orderService.models.OrderResponse;
import com.ecommerce.orderService.models.OrderStatus;
import com.ecommerce.orderService.service.OrderDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller", description = "APIs for managing order details")
public class OrderController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Operation(summary = "Get Order by ID", description = "Retrieve order details using the order ID")
    @GetMapping("/{id}")
    public OrderResponse getOrderDetailsByOrderId(
            @Parameter(description = "Order ID") @PathVariable("id") String id) {
        return orderDetailsService.getOrderDetailsById(id);
    }

    @Operation(summary = "Get Orders by User ID", description = "Retrieve all orders placed by a specific user")
    @GetMapping("/user/{userId}")
    public List<OrderResponse> getOrderDetailsByUser(
            @Parameter(description = "User ID") @PathVariable("userId") String userId) {
        return orderDetailsService.getOrderDetailsByUser(userId);
    }

    @Operation(summary = "Add New Order", description = "Create a new order and return the created details")
    @PostMapping
    public OrderResponse addOrderDetails(
            @RequestBody OrderDetails orderDetails) {
        return orderDetailsService.addOrderDetails(orderDetails);
    }

    @Operation(summary = "Update Order Item Status", description = "Update the status of an order item within an order")
    @PutMapping("/{orderId}/{productId}/{status}")
    public String updateOrderDetailsStatus(
            @Parameter(description = "Order ID") @PathVariable("orderId") String orderId,
            @Parameter(description = "Product ID") @PathVariable("productId") String productId,
            @Parameter(
                    description = "New Status",
                    required = true,
                    example = "IN_PROGRESS"
            ) @PathVariable("status") OrderStatus status) {
        orderDetailsService.updateOrderDetailsStatus(orderId, productId, status.toString());
        return "Details updated Successfully";
    }
}