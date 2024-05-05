package org.qubyte.base.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Alok kumar
 * 
 */
@JsonIgnoreProperties(value = {"cause","stackTrace","suppressed","localizedMessage"})
public class AppException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	
	private String errorType;
	
	private int errorCode;
	
	private String message;

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public AppException(String errorType, int errorCode, String message) {
		super();
		this.errorType = errorType;
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public AppException(){
		super();
	}
	
	
}

