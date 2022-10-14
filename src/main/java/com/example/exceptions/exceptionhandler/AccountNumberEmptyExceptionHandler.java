package com.example.exceptions.exceptionhandler;

import com.example.exceptions.PaymentTransactionException;
import com.example.exceptions.generic.GenericExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import utils.Utils;

import java.util.Date;

@ControllerAdvice
@RestController
public class AccountNumberEmptyExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PaymentTransactionException.class)
    public final ResponseEntity<Object> configExceptionHandler(PaymentTransactionException ex, WebRequest request) {
        return new ResponseEntity(new GenericExceptionResponse(Utils.getTime(), ex.getMessage(),
                request.getDescription(false)), HttpStatus.NOT_FOUND);
    }
}
