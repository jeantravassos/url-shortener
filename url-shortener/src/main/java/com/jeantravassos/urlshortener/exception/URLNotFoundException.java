package com.jeantravassos.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class URLNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public URLNotFoundException(String message) {
		super(message);
	}
	
	public URLNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}