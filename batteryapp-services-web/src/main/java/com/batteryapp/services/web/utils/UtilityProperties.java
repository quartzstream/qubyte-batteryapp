package com.batteryapp.services.web.utils;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Component
public class UtilityProperties implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	@Value("${password.secret.key}")
	private String passwordSecretKey;

	@Value("${is.to.validate.captcha}")
	private String isToValidateCaptcha;

	@Value("${inactiveAllowedMinutes}")
	private Long inactiveAllowedMinutes;
	
	@Value("${password.expiry.days}")
	private int passwordExpDays;
	
	@Value("${expiry.days}")
	private int expiryDays;

	@Value("${maxLoginAttemptAllowed}")
	private Long maxLoginAttemptAllowed;
	
	@Value("${numberOfPreviousPassword}")
	private String numberOfPreviousPassword;
	
	@Value("${passwordExpiryPeriodInDays}")
	private int passwordExpiryPeriodInDays;
	
	@Value("${minusServerTime}")
	private int minusServerTime;
	
	@Value("${plusServerTime}")
	private int plusServerTime;

}
