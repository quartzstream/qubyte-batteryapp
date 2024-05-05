package org.qubyte.base.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 
 * @author Alok kumar
 * 
 */
@Data
public class BaseApiRequest {
	
	@NotEmpty(message = "User code should be mandatory.")
	@Pattern(regexp = "[0-9]+", message = "User code should be number only.")
	@Size(min = 10, max = 10, message = "User code size must be 10 characters.")
	@JsonProperty("usercode")
	private String userCode;
	
	@NotEmpty(message = "Timestamp should be mandatory.")
	@Pattern(regexp = "\\d{14}", message = "Timestamp format should be ddMMyyyyHHmmss")
	@JsonProperty("timestamp")
	private String timeStamp;

}
