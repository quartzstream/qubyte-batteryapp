package com.batteryapp.services.web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.batteryapp.services.web.service.AuthenticationService;
import com.batteryapp.services.web.utils.UtilityProperties;
import org.qubyte.base.constants.AppConstants;
import org.qubyte.base.domain.UserInfo;
import org.qubyte.base.logger.BaseLogger;
import org.qubyte.base.requestcontext.IRequestInitiationContext;
import org.qubyte.base.utils.SecurityUtils;


/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UtilityProperties utilityProperties;

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private IRequestInitiationContext requestContext;

	private static final Logger LOGGER = BaseLogger.getLogger(JwtAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			LOGGER.debug("URL: " + request.getRequestURI());
			// LOGGER.debug("Request:"+IOUtils.toString(request.getInputStream()));
		} catch (Exception e) {
			LOGGER.debug("Error in logging Request");
		}

		String jwt = SecurityUtils.getJwtFromRequest(request);
		LOGGER.debug("@@@@@inside doFilterInternal");
		String header = request.getHeader(AppConstants.SecurityConstants.HEADER_STRING);
		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			String username = tokenProvider.getUsernameFromJWT(jwt);
			String authToken = header.replace(AppConstants.SecurityConstants.TOKEN_PREFIX, "");
			boolean requestAllowedForUser = InactiveSessionLimiter.isRequestAllowedForUser(authToken, username,
					utilityProperties.getInactiveAllowedMinutes());
			if (requestAllowedForUser) {
				UserDetails userDetails = authenticationService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = tokenProvider.getAuthentication(authToken,
						SecurityContextHolder.getContext().getAuthentication(), userDetails);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // this line is
																										// not needed
				LOGGER.debug("+++++++++++++Settng authentication inside filer");
				SecurityContextHolder.getContext().setAuthentication(authentication);
				requestContext.setData(getUserInfoFromContext());
				
			} else {
				LOGGER.debug("------INACTIVE NOT THERE-------");
			}
		} else {
			LOGGER.debug("------JWT NOT THERE-------");
		}
		filterChain.doFilter(request, response);
	}

	private UserInfo getUserInfoFromContext() {
		UserInfo userInfo = null;
		if (SecurityContextHolder.getContext() != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				if (auth.getPrincipal() instanceof UserInfo) {
					userInfo = (UserInfo) auth.getPrincipal();
				}
			}
		}
		return userInfo;
	}
}