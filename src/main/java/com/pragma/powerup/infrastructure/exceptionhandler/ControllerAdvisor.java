package com.pragma.powerup.infrastructure.exceptionhandler;

import com.pragma.powerup.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            DataNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.DATA_NOT_FOUND.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleMethodException(
            MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE,e.getBindingResult().getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList())));
    }


    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleDataAlreadyExistsException(
            DataAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.DATA_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(NotModifiableException.class)
    public ResponseEntity<Map<String, String>> handleNotModifiableException(
            NotModifiableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.NOT_MODIFIABLE.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, String>> handleForbiddenException(
            ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.FORBIDDEN.getMessage()));
    }

    @ExceptionHandler(DataNotValidException.class)
    public ResponseEntity<Map<String, String>> handleDataNotValidException(
            DataNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.DATA_NOT_VALID.getMessage()));
    }

}
