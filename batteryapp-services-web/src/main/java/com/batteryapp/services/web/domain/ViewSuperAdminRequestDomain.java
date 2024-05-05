package com.batteryapp.services.web.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.qubyte.base.domain.BaseApiRequest;

import lombok.Data;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Data
public class ViewSuperAdminRequestDomain extends BaseApiRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	@NotNull(message = "Column should not be null.")
	@JsonProperty("column")
	private String column;

	@NotNull(message = "Value should not be null.")
	@JsonProperty("value")
	private String value;
}
