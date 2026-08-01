package com.ecommerce.orderService.service;

import com.ecommerce.orderService.client.InventoryClient;
import com.ecommerce.orderService.entity.OrderDetails;
import com.ecommerce.orderService.entity.OrderItem;
import com.ecommerce.orderService.exception.OrderDetailsNotFound;
import com.ecommerce.orderService.models.CommonEnum;
import com.ecommerce.orderService.models.InventoryResponse;
import com.ecommerce.orderService.models.OrderItemResponse;
import com.ecommerce.orderService.models.OrderResponse;
import com.ecommerce.orderService.repository.OrderDetailsRepository;
import com.ecommerce.orderService.repository.OrderItemRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService{

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Transactional
    @Override
    public OrderResponse addOrderDetails(OrderDetails orderDetails) {
        List<OrderItem> listInventoryResponse = new ArrayList<>();
        orderDetails.getOrderItems().forEach(orderItem -> {
            InventoryResponse inventoryResponse= inventoryClient.decreaseStock(orderItem.getProductID());
            if(inventoryResponse.getMessage().equals(CommonEnum.In_Stock.name())){
                orderItem.setStatus(CommonEnum.Created.toString());
                listInventoryResponse.add(orderItem);
            }
        });
        listInventoryResponse.forEach(item -> item.setOrderDetails(orderDetails));
        orderDetails.setOrderItems(listInventoryResponse);
        OrderDetails orderDetail = orderDetailsRepository.save(orderDetails);
        return OrderResponse.builder()
                .orderId(orderDetail.getOrderId())
                .orderItemResponseList(orderDetail.getOrderItems().stream().map(orderItem -> {
                    OrderItemResponse orderItemResponse = new OrderItemResponse();
                    orderItemResponse.setProductId(orderItem.getProductID());
                    orderItemResponse.setProductName(orderItem.getProductName());
                    orderItemResponse.setStatus(CommonEnum.Created.name());
                    return orderItemResponse;
                }).collect(Collectors.toList()))
                .status(CommonEnum.Created.name())
                .build();
    }

    @Override
    public OrderResponse getOrderDetailsById(String id) {
        OrderDetails orderDetails =  orderDetailsRepository.findById(id).orElseThrow( () -> new OrderDetailsNotFound("Id not found -> " +id));
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(orderDetails.getOrderId());
        orderResponse.setStatus(CommonEnum.Created.name());
        List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
        orderDetails.getOrderItems().forEach(item ->{
           OrderItemResponse orderItemResponse = new OrderItemResponse();
           orderItemResponse.setStatus(item.getStatus());
           orderItemResponse.setProductId(item.getProductID());
           orderItemResponse.setProductName(item.getProductName());
           orderItemResponseList.add(orderItemResponse);
        });
        orderResponse.setOrderItemResponseList(orderItemResponseList);
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getOrderDetailsByUser(String userId) {
        List<OrderDetails> orderDetailsList = orderDetailsRepository.getOrderDetailsByUserId(userId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        orderDetailsList.forEach(orderDetails->{
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(orderDetails.getOrderId());
            orderResponse.setStatus(CommonEnum.Created.name());
            List<OrderItemResponse> orderItemResponseList = new ArrayList<>();
            orderDetails.getOrderItems().forEach(item ->{
                OrderItemResponse orderItemResponse = new OrderItemResponse();
                orderItemResponse.setStatus(item.getStatus());
                orderItemResponse.setProductId(item.getProductID());
                orderItemResponse.setProductName(item.getProductName());
                orderItemResponseList.add(orderItemResponse);
            });
            orderResponse.setOrderItemResponseList(orderItemResponseList);
            orderResponses.add(orderResponse);
        });
        return orderResponses;
    }

    @Override
    public void updateOrderDetailsStatus(String id, String productId, String orderStatus) {
        Optional<OrderDetails> optionalOrderDetails = orderDetailsRepository.findById(id);
        if (optionalOrderDetails.isEmpty()) {
            throw new OrderDetailsNotFound("OrderDetails not found for id: " + id);
        }

        OrderDetails orderDetails = optionalOrderDetails.get();

        OrderItem orderItem = orderDetails.getOrderItems()
                .stream()
                .filter(item -> productId.equals(item.getProductID()))
                .findFirst()
                .orElseThrow(() -> new OrderDetailsNotFound("OrderItem not found for productId: " + productId));

        orderItem.setStatus(orderStatus);
        orderItemRepository.save(orderItem);
    }
}
