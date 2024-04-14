package com.capgemini.customerservice.customer;

import com.capgemini.customerservice.exception.ResourceNotFoundException;
import com.capgemini.customerservice.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomerExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ResponseEntity<ErrorDto> handleNotFoundExceptions(ResourceNotFoundException ex) {
    ErrorDto errorDto = new ErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorDto> handleValidationExceptions(ValidationException ex) {
    ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
  }
}
