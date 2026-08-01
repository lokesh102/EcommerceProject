package com.ecommerce.orderService.exception;

import com.ecommerce.orderService.models.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(OrderDetailsNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO sellerAlreadyExist(OrderDetailsNotFound exception) {
        return ExceptionDTO.builder().message(exception.getMessage()).httpStatusCode(HttpStatus.BAD_REQUEST).build();
    }
}