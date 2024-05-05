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
public class ViewSuperAdminResponseDomain extends BaseApiResponse implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	@JsonProperty(index = 1, value = "iTotalRecords")
	private int iTotalRecords;
	
	@JsonProperty(index = 2, value = "iTotalDisplayRecords")
	private int iTotalDisplayRecords;
	
	@JsonProperty(index = 3, value = "datatTableSize")
	private int datatTableSize;
	
	@JsonProperty(index = 4, value = "aaData")
	private List<?> aaData;
}
