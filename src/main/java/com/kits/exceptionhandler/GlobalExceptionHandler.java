package com.kits.exceptionhandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.azure.cosmos.CosmosException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Value(value = "${data.exception.message1}")
    private String message1;
    @Value(value = "${data.exception.message2}")
    private String message2;
    @Value(value = "${data.exception.message3}")
    private String message3;
    @Value(value = "${data.exception.message4}")
    private String message4;
    
    @ExceptionHandler(value = AggregateServiceException.class)
    public ResponseEntity<String> blogNotFoundException(AggregateServiceException notFoundException) {
        return new ResponseEntity<String>(message2, HttpStatus.NOT_FOUND);
    }
//   @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<String> databaseConnectionFailsException( exception) {
//        return new ResponseEntity<String>(message3, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
   
   @ExceptionHandler(value = Exception.class)
   public ResponseEntity<String> databaseConnectionFailsException(CosmosException exception) {
       return new ResponseEntity<String>(message4, HttpStatus.INTERNAL_SERVER_ERROR);
   }
}