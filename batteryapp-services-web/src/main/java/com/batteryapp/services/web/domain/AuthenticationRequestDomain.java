package com.batteryapp.services.web.domain;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Data
public class AuthenticationRequestDomain implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	@NotEmpty(message = "User code should be mandatory.")
	@Pattern(regexp = "[0-9]+", message = "User code should be number only.")
	@Size(min = 10, max = 10, message = "User code size must be 10 characters.")
	@JsonProperty("usercode")
	private String userCode;

	@NotEmpty(message = "Password should be mandatory.")
	//@Pattern(regexp = "[0-9]+", message = "Only Numbers are allowed for Username.")
	@Size(min = 6, max = 10, message = "Password size must be between 6 and 10 characters.")
	@JsonProperty("password")
	private String password;

	@NotEmpty(message = "Timestamp should be mandatory.")
	@JsonProperty("timestamp")
	private String timeStamp;

}
