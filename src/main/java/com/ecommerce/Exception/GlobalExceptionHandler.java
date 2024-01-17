package com.ecommerce.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<MyErrorDetails> UserException(UserException ue, WebRequest wr){

        MyErrorDetails error = new MyErrorDetails();
        error.setLocalDateTime(LocalDateTime.now());
        error.setMsg(ue.getMessage());
        error.setDetails(wr.getDescription(false));

        return new  ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(AddressException.class)
    public ResponseEntity<MyErrorDetails> AddressException(AddressException ae, WebRequest wr){

        MyErrorDetails error = new MyErrorDetails();
        error.setLocalDateTime(LocalDateTime.now());
        error.setMsg(ae.getMessage());
        error.setDetails(wr.getDescription(false));

        return new  ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(ProductException.class)
    public ResponseEntity<MyErrorDetails> productException(ProductException pe, WebRequest wr){

        MyErrorDetails error = new MyErrorDetails();
        error.setLocalDateTime(LocalDateTime.now());
        error.setMsg(pe.getMessage());
        error.setDetails(wr.getDescription(false));

        return new  ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyErrorDetails> exception(Exception e, WebRequest wr){

        MyErrorDetails error = new MyErrorDetails();
        error.setLocalDateTime(LocalDateTime.now());
        error.setMsg(e.getMessage());
        error.setDetails(wr.getDescription(false));

        return new  ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(WalletException.class)
    public ResponseEntity<MyErrorDetails> walletException(Exception e, WebRequest wr){

        MyErrorDetails error = new MyErrorDetails();
        error.setLocalDateTime(LocalDateTime.now());
        error.setMsg(e.getMessage());
        error.setDetails(wr.getDescription(false));

        return new  ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(OrderException.class)
    public ResponseEntity<MyErrorDetails> orderException(Exception e, WebRequest wr){

        MyErrorDetails error = new MyErrorDetails();
        error.setLocalDateTime(LocalDateTime.now());
        error.setMsg(e.getMessage());
        error.setDetails(wr.getDescription(false));

        return new  ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<MyErrorDetails> orderPayment(Exception e, WebRequest wr){

        MyErrorDetails error = new MyErrorDetails();
        error.setLocalDateTime(LocalDateTime.now());
        error.setMsg(e.getMessage());
        error.setDetails(wr.getDescription(false));

        return new  ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MyErrorDetails> orderException(MethodArgumentNotValidException e, WebRequest wr){

        MyErrorDetails error = new MyErrorDetails();
        error.setLocalDateTime(LocalDateTime.now());
        error.setMsg(String.valueOf(e.getBindingResult().getFieldError().getDefaultMessage()));
        error.setDetails(wr.getDescription(false));

        return new  ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }




}
