package com.ecommerce.project.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public APIException(){

    }

    public APIException(String message){
        super(message);
    }
}
