package org.qubyte.base.exception;

/**
 * 
 * @author Alok kumar
 * 
 */
public class SystemException extends BaseException {

	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String respCode;
	public SystemException() {
		super();
	}
	
	public SystemException(String message) {
        super(message);
    }
	
	public SystemException(String message, Throwable cause) {
	        super(message, cause);
	}
	
	public SystemException(Throwable cause) {
        super(cause);
    }
	
	public SystemException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
	public SystemException(String respCode, String message) {
        super(message);
        this.respCode = respCode;
    }

	public int getErrorCode() {
		return errorCode;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	@Override
	public String toString() {
		return "SystemException{" +
				"errorCode=" + errorCode +
				", respCode='" + respCode + '\'' +
				", message='" + getMessage() + '\'' +
				'}';
	}
}
