package com.viniciusps2.bank.shared;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApplicationError extends RuntimeException {
	public ApplicationError(String message) {
		super(message);
	}
}