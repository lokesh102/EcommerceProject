package com.ecommerce.orderService.exception;

public class OrderDetailsNotFound extends RuntimeException{

    public OrderDetailsNotFound(String message){
        super(message);
    }
}
