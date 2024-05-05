package com.batteryapp.services.web.security;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.qubyte.base.constants.AppConstants;
import org.qubyte.base.domain.BaseApiResponse;
import org.qubyte.base.exception.AppException;
import org.qubyte.base.utils.QubyteStatus;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@SuppressWarnings("unused")
	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {

		BaseApiResponse baseApiResponse = new BaseApiResponse();
		AppException exception = new AppException(AppConstants.Types.INVALID_TOKEN_ERROR,
				QubyteStatus.INVALID_TOKEN_ERROR.getCode(),
				QubyteStatus.INVALID_TOKEN_ERROR.getMessage());
		
		baseApiResponse.setCode(QubyteStatus.INVALID_TOKEN_ERROR.getCode());
		baseApiResponse.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
		baseApiResponse.setMessage(QubyteStatus.INVALID_TOKEN_ERROR.getMessage());
		
		httpServletResponse.setStatus(QubyteStatus.INVALID_TOKEN_ERROR.getCode());
		OutputStream out = httpServletResponse.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, baseApiResponse);
		out.flush();
	}
}