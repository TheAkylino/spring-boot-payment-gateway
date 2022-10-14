package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PaymentTransactionException extends  RuntimeException{

    public PaymentTransactionException(String message) {
        super(message);
    }
}
