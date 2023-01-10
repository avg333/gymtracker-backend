package org.avillar.gymtracker.base.infrastructure;

import org.avillar.gymtracker.base.application.IncorrectFormException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IncorrectFormException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
            IncorrectFormException ex, WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("message", "City not found");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
