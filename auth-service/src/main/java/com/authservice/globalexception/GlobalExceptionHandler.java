package com.authservice.globalexception;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.authservice.exception.CustomRuntimeException;


@RestControllerAdvice
public class GlobalExceptionHandler {

	
	
	
	@ExceptionHandler(CustomRuntimeException.class)
	public ResponseEntity<String> handleCustomException(
	        CustomRuntimeException ex) {

	    return ResponseEntity
	            .status(ex.getStatusCode())
	            .body(ex.getMessage());
	}
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new ConcurrentHashMap<>();

        ex.getBindingResult().getFieldErrors()
          .forEach(error ->
              errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
