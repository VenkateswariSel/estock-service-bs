package com.estock.estockmanager.exception;

import com.estock.estockmanager.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class StockInfoControllerAdvice {

    @ExceptionHandler({StockNotFoundException.class})
    public ResponseEntity<Response> stockNotFoundException(){
        Response response = new Response("Stock not found exception", HttpStatus.NOT_FOUND.value(), ZonedDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
