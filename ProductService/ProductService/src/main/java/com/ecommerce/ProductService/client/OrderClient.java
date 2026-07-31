package com.ecommerce.ProductService.client;

import com.ecommerce.ProductService.model.OrderDetails;
import com.ecommerce.ProductService.model.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ORDERSERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {

    @PostMapping("/orders")
    OrderResponse addOrderDetails(@RequestBody OrderDetails orderDetails);

}
