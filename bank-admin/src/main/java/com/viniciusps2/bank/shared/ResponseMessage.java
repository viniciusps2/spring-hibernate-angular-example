package com.viniciusps2.bank.shared;

public class ResponseMessage<T> {

	private String message;
	private ResponseMessageStatus status;
	private T payload;

	public ResponseMessage() {
		super();
	}

	public ResponseMessage(String message, ResponseMessageStatus status) {
		super();
		this.message = message;
		this.status = status;
	}
	
	public ResponseMessage(T payload) {
		super();
		this.status = ResponseMessageStatus.SUCCESS;
		this.payload = payload;
	}

	public ResponseMessage(T payload, ResponseMessageStatus status) {
		super();
		this.status = status;
		this.payload = payload;
	}

	public ResponseMessageStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseMessageStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}
	
	
}
