package org.qubyte.base.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 
 * @author Alok kumar
 * 
 */
@JsonInclude(Include.NON_NULL)
@Data
public class BaseApiResponse {

	@JsonProperty(index = -3, value = "code")
	private int code;

	@JsonProperty(index = -2, value = "status")
	private String status;
	
	@JsonProperty(index = -1, value = "message")
	private String message;
	
	@JsonProperty(index = 0, value = "endpoint")
	private String endpoint;

}
