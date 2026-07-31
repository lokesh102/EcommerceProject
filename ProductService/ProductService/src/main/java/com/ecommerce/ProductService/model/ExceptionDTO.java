package com.ecommerce.ProductService.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@Builder
public class ExceptionDTO {

    String message;
    HttpStatusCode httpStatusCode;

}
