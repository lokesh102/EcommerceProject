package com.ecommerce.ProductService.exception;

public class SellerAlreadyExistsException extends Exception{

    public SellerAlreadyExistsException(String message){
        super(message);
    }
}
