package com.batteryapp.services.web.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.qubyte.base.domain.BaseApiResponse;

import lombok.Data;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Data
public class AuthenticationResponseDomain extends BaseApiResponse implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	@JsonProperty(index = 1, value = "usercode")
	private String userCode;

	@JsonProperty(index = 2, value = "password")
	private String password;

	@JsonProperty(index = 3, value = "mapped_org_id")
	private String mappedOrgId;

	@JsonProperty(index = 4, value = "email")
	private String email;

	@JsonProperty(index = 5, value = "contact_number")
	private String contactNumber;

	@JsonProperty(index = 6, value = "user_type")
	private String userType;

	@JsonProperty(index = 7, value = "first_name")
	private String firstName;

	@JsonProperty(index = 8, value = "last_name")
	private String lastName;

	@JsonProperty(index = 9, value = "login_date_time")
	private String loginDateTime;
	
	@JsonProperty(index = 10, value = "token_prefix")
	private String tokenPrefix;

	@JsonProperty(index = 11, value = "auth_token")
	private String authToken;

	@SuppressWarnings("rawtypes")
	@JsonProperty(index = 12, value = "roles")
	private List roles;

}
