package com.batteryapp.services.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.qubyte.base.utils.QubyteStatus;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handlerMethodArgumentResolverException(MethodArgumentNotValidException ex) {
		
		Map finalResponse = new HashMap<>();
		Map<String , String> resp = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			
			resp.put(fieldName, message);
		});
		
		finalResponse.put("code", QubyteStatus.BAD_REQUEST.getCode());
		finalResponse.put("status", QubyteStatus.STATUS_FAILD.getMessage());
		finalResponse.put("message", QubyteStatus.BAD_REQUEST.getMessage());
		finalResponse.put("errors", resp);
		
		return new ResponseEntity<Map<String,String>>(finalResponse,HttpStatus.BAD_REQUEST);
	}
}
