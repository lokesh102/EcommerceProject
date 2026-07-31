package com.ecommerce.ProductService.exception;


import com.ecommerce.ProductService.model.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExpectionHandler {


    @ExceptionHandler(SellerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO sellerAlreadyExist(SellerAlreadyExistsException  exception){
        return ExceptionDTO.builder().message(exception.getMessage()).httpStatusCode(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(SellerNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO sellerNotFound(SellerNotFoundException  exception){
        return ExceptionDTO.builder().message(exception.getMessage()).httpStatusCode(HttpStatus.BAD_REQUEST).build();
    }


    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO productNotFound(ProductNotFoundException  exception){
        return ExceptionDTO.builder().message(exception.getMessage()).httpStatusCode(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO cartNotFound(CartNotFoundException  exception){
        return ExceptionDTO.builder().message(exception.getMessage()).httpStatusCode(HttpStatus.BAD_REQUEST).build();
    }
}
