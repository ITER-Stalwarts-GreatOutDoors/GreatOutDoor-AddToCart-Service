package com.cg.iter.greatoutdooraddtocart.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

	private long currentTimeMillis = System.currentTimeMillis();
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorMessage> somethingWentWrong(Exception ex){
		
		ErrorMessage exceptionResponse =
				new ErrorMessage(ex.getMessage(), 
						"Some thing went wrong!",currentTimeMillis);
		return new ResponseEntity<ErrorMessage>(exceptionResponse,
				new HttpHeaders(),HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public final ResponseEntity<ErrorMessage> orderNotFound(OrderNotFoundException ex){

		ErrorMessage exceptionResponse =
				new ErrorMessage(ex.getMessage(), 
						"Some thing went wrong!",currentTimeMillis);
		return new ResponseEntity<ErrorMessage>(exceptionResponse,
				new HttpHeaders(),HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(NullParameterException.class)
	public final ResponseEntity<ErrorMessage> nullParameter(NullParameterException ex){

		ErrorMessage exceptionResponse =
				new ErrorMessage(ex.getMessage(), 
						"Some thing went wrong!",currentTimeMillis);
		return new ResponseEntity<ErrorMessage>(exceptionResponse,
				new HttpHeaders(),HttpStatus.NOT_FOUND);
	}
	
	
}


class ErrorMessage{
	private String message;
	private String details;
	private long timestamp;
	
	public ErrorMessage() {}
	
	
	
	public ErrorMessage(String message, String details, long timestamp) {
		super();
		this.message = message;
		this.details = details;
		this.timestamp = timestamp;
	}



	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}



	public long getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	
}