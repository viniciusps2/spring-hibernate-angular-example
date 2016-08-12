package com.viniciusps2.bank.shared;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerValidationHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseMessage processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		FieldError error = result.getFieldError();

		return processFieldError(error);
	}

	private ResponseMessage processFieldError(FieldError error) {
		ResponseMessage message = null;
		if (error != null) {
			message = new ResponseMessage(error.getDefaultMessage(), ResponseMessageStatus.ERROR);
		}
		return message;
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseMessage processRuntimeException(RuntimeException error) {
		error.printStackTrace();
		return new ResponseMessage("Unknown error", ResponseMessageStatus.ERROR);
	}
	
	@ExceptionHandler(ApplicationError.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseMessage processApplicationError(RuntimeException error) {
		return new ResponseMessage(error.getMessage(), ResponseMessageStatus.ERROR);
	}


}