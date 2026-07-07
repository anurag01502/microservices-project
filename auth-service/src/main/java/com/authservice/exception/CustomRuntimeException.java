package com.authservice.exception;

import org.springframework.http.HttpStatus;

public class CustomRuntimeException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final HttpStatus status;

    public CustomRuntimeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatusCode() {
        return status;
    }
}