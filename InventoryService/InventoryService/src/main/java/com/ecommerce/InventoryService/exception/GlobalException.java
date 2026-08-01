package com.ecommerce.InventoryService.exception;

import com.ecommerce.InventoryService.model.ExceptionDTO;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO sellerAlreadyExist(ProductNotFoundException exception) {
        return ExceptionDTO.builder().message(exception.getMessage()).httpStatusCode(HttpStatus.BAD_REQUEST).build();
    }
}