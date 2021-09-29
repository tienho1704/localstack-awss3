package com.localstack.awss3.handlers;

import com.localstack.awss3.execptions.PersonAgeException;
import com.localstack.awss3.models.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PersonAgeExceptionHandler {

    @ExceptionHandler(PersonAgeException.class)
    public ResponseEntity<ErrorMessage> personAgeExceptionHandler(PersonAgeException personAgeException) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), 1704, personAgeException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
