package com.batteryapp.services.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.batteryapp.services.web.domain.AuthenticationRequestDomain;
import com.batteryapp.services.web.domain.AuthenticationResponseDomain;
import com.batteryapp.services.web.entity.UserMaster;
import com.batteryapp.services.web.security.JwtTokenProvider;
import com.batteryapp.services.web.service.AuthenticationService;
import com.batteryapp.services.web.service.CommonService;
import com.batteryapp.services.web.utils.ApiEndPoint;
import com.batteryapp.services.web.utils.UtilityProperties;
import org.qubyte.base.constants.AppConstants;
import org.qubyte.base.exception.AppException;
import org.qubyte.base.exception.SystemException;
import org.qubyte.base.logger.BaseLogger;
import org.qubyte.base.utils.CommonUtils;
import org.qubyte.base.utils.DateTimeUtils;
import org.qubyte.base.utils.QubyteStatus;
import org.qubyte.base.utils.SecurityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.qubyte.base.controller.BaseRequestController;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@RestController
@RequestMapping(ApiEndPoint.BASE_URL.BASE)
public class AuthenticationController extends BaseRequestController {

	private static final Logger logger = BaseLogger.getLogger(AuthenticationController.class);

	@Autowired
	private UtilityProperties utilityProperties;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CommonService commonService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationService authenticationService;

	@RequestMapping(value = ApiEndPoint.URL.LOGIN_AUTH, method = RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequestDomain requestDomain,
			HttpServletResponse httpResponse, HttpServletRequest httpRequest) {

		AuthenticationResponseDomain baseApiResponse = null;
		
		AuthenticationRequestDomain loginRequest = SecurityUtils.prepareCleanXSSDomain(requestDomain);

		try {
			if (loginRequest != null) {
				if (loginRequest.getUserCode() != null && loginRequest.getPassword() != null
						&& loginRequest.getTimeStamp() != null) {
					// String key = loginRequest.getUserName() + loginRequest.getTimeStamp();
					try {
						if ("Y".equalsIgnoreCase(utilityProperties.getIsToValidateCaptcha())) {
							// IssLoginRequest issLoginRequest = new IssLoginRequest();
							// GeneralUtils.copyObject(issLoginRequest, loginRequest);
							// captchaService.validateCaptcha(issLoginRequest);
						}

						Boolean isTimestamp = commonService.isValidTimeStamp(loginRequest.getTimeStamp());
						if(!isTimestamp) {

							baseApiResponse = new AuthenticationResponseDomain();
							baseApiResponse.setCode(QubyteStatus.INVALID_DATETIME.getCode());
							baseApiResponse.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
							baseApiResponse.setMessage(QubyteStatus.INVALID_DATETIME.getMessage());
							baseApiResponse.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.LOGIN_AUTH);
							logger.debug("authenticateUser : Invalid Timestamp");

							return ResponseEntity.status(baseApiResponse.getCode()).body(baseApiResponse);
						}

						Authentication authentication = authenticationManager
								.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserCode(),
										loginRequest.getPassword()));

						logger.debug("#####################  set auth");
						SecurityContextHolder.getContext().setAuthentication(authentication);
						// requestContext.setData(getUserInfoFromContext());
						Collection<? extends GrantedAuthority> authorties = authentication.getAuthorities();
						List<String> roles = new ArrayList<String>();

						for (GrantedAuthority authority : authorties) {
							roles.add(authority.getAuthority());
						}

						UserMaster userMst = commonService.getUserByUserCd(loginRequest.getUserCode());
						/*
						 * if (!"AA".equals(userMst.getUserType())) { throw new
						 * BadCredentialsException(AppConstants.ErrorTypes.SIGN_IN_ERROR); }
						 */

						String jwt = jwtTokenProvider.generateToken(authentication);
						baseApiResponse = getSuccessResponse(jwt, userMst, roles);
						httpResponse.setHeader("Access-Control-Allow-Headers",
								"Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
										+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
						httpResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
						httpResponse.setHeader(AppConstants.Common.TOKEN_HEADER,
								AppConstants.SecurityConstants.TOKEN_PREFIX + jwt);
						// if(user.getLoginFailedCounts()>0){
						userMst.setLockStatus("U");
						updateLoginAttempCount(userMst, 0L);
						// }
						String loginDateTime = DateTimeUtils.getCurrentDate("ddMMyyyyHHmmss");
						baseApiResponse.setLoginDateTime(loginDateTime);
						baseApiResponse.setTokenPrefix(AppConstants.SecurityConstants.TOKEN_PREFIX);
						baseApiResponse.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.LOGIN_AUTH);

						return ResponseEntity.status(baseApiResponse.getCode()).body(baseApiResponse);

					} catch (BadCredentialsException e) {
						UserMaster userMst = commonService.getUserByUserCd(loginRequest.getUserCode());
						if (userMst != null) { // && "AA".equals(userMst.getUserType())
							Long attempDone = userMst.getLoginFailedCounts() == null ? 1L
									: userMst.getLoginFailedCounts() + 1;
							Long maxAttempt = utilityProperties.getMaxLoginAttemptAllowed();
							Long attempLeft = maxAttempt - attempDone;

							if (attempLeft <= 0) {

								unlockUser(userMst, "L");
								baseApiResponse = new AuthenticationResponseDomain();
								baseApiResponse.setCode(QubyteStatus.USER_ACCOUNT_LOCKED.getCode());
								// baseApiResponse.setStatus(AppConstants.Status.FAILURE_STATUS);
								baseApiResponse.setMessage(QubyteStatus.USER_ACCOUNT_LOCKED.getMessage());
								baseApiResponse.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.LOGIN_AUTH);

								return ResponseEntity.status(baseApiResponse.getCode()).body(baseApiResponse);
							} else {
								updateLoginAttempCount(userMst, attempDone);
								baseApiResponse = new AuthenticationResponseDomain();
								baseApiResponse.setCode(QubyteStatus.INVALID_LOGIN.getCode());
								baseApiResponse.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
								baseApiResponse.setMessage(QubyteStatus.INVALID_LOGIN.getMessage() + " " + (attempLeft)
										+ " attempt(s) left.");
								baseApiResponse.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.LOGIN_AUTH);

								return ResponseEntity.status(baseApiResponse.getCode()).body(baseApiResponse);
							}
						}
						baseApiResponse = new AuthenticationResponseDomain();
						baseApiResponse.setCode(QubyteStatus.INVALID_LOGIN.getCode());
						baseApiResponse.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
						baseApiResponse.setMessage(QubyteStatus.INVALID_LOGIN.getMessage());
						baseApiResponse.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.LOGIN_AUTH);

						return ResponseEntity.status(baseApiResponse.getCode()).body(baseApiResponse);

					} catch (SystemException e) {
						e.printStackTrace();
						baseApiResponse = new AuthenticationResponseDomain();
						baseApiResponse.setCode(e.getErrorCode());
						baseApiResponse.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
						baseApiResponse.setMessage(e.getMessage());
						baseApiResponse.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.LOGIN_AUTH);
						logger.debug(e.getMessage());

						return ResponseEntity.status(baseApiResponse.getCode()).body(baseApiResponse);

					} catch (Exception e) {
						e.printStackTrace();
						baseApiResponse = new AuthenticationResponseDomain();
						baseApiResponse.setCode(QubyteStatus.INVALID_CREDENTIALS_ERROR.getCode());
						baseApiResponse.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
						baseApiResponse.setMessage(e.getMessage());
						baseApiResponse.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.LOGIN_AUTH);
						logger.debug(e.getMessage());

						return ResponseEntity.status(baseApiResponse.getCode()).body(baseApiResponse);

					}
				} else

					baseApiResponse = new AuthenticationResponseDomain();
				baseApiResponse.setCode(QubyteStatus.BAD_REQUEST.getCode());
				baseApiResponse.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
				baseApiResponse.setMessage(QubyteStatus.BAD_REQUEST.getMessage());
				baseApiResponse.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.LOGIN_AUTH);

				return ResponseEntity.status(baseApiResponse.getCode()).body(baseApiResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			baseApiResponse = new AuthenticationResponseDomain();
			baseApiResponse.setCode(QubyteStatus.SIGN_IN_ERROR.getCode());
			baseApiResponse.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
			baseApiResponse.setMessage(e.getMessage());
			baseApiResponse.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.LOGIN_AUTH);
			logger.debug(e.getMessage());

			return ResponseEntity.status(baseApiResponse.getCode()).body(baseApiResponse);
		}

		baseApiResponse = new AuthenticationResponseDomain();
		baseApiResponse.setCode(QubyteStatus.INTERNAL_ERROR.getCode());
		baseApiResponse.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
		baseApiResponse.setMessage(QubyteStatus.INTERNAL_ERROR.getMessage());
		baseApiResponse.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.LOGIN_AUTH);

		printResponseAsJSON(baseApiResponse);

		return ResponseEntity.status(baseApiResponse.getCode()).body(baseApiResponse);
	}

	private void unlockUser(UserMaster userMst, String userLockStatus) {
		try {
			Boolean isStatusUpdate = authenticationService.updateUserStatus(userMst, userLockStatus, "A",
					utilityProperties.getExpiryDays());
			if (isStatusUpdate) {
				logger.debug("***********User unlocked Successfully************************");
				authenticationService.clearUserManagementUserListCache(); // clearing
				// cache
			} else {
				throwException(QubyteStatus.USER_NO_ROLE.getCode(), QubyteStatus.USER_NO_ROLE.getMessage());
			}
		} catch (Exception e) {
			throwException(QubyteStatus.USER_NO_ROLE.getCode(), QubyteStatus.USER_NO_ROLE.getMessage());
		}
	}

	private void throwException(int code, String errMsg) {
		throw new SystemException(code, errMsg);
	}

	private void updateLoginAttempCount(UserMaster userMst, long count) {
		userMst.setLoginFailedCounts(count);
		userMst.setLastLoginDate(new Date());
		if (count <= 0) {
			userMst.setExpiryDate(CommonUtils.addDaysInCurrentDay(utilityProperties.getExpiryDays()));
		}
		authenticationService.updateUserMaster(userMst);
		authenticationService.clearUserManagementUserListCache(); // clearing cache
	}

	/*
	 * private UserInfo getUserInfoFromContext() { UserInfo userInfo = null; if
	 * (SecurityContextHolder.getContext() != null) { Authentication auth =
	 * SecurityContextHolder.getContext().getAuthentication(); if (auth != null) {
	 * if (auth.getPrincipal() instanceof UserInfo) { userInfo = (UserInfo)
	 * auth.getPrincipal(); } } } return userInfo; }
	 */

	@SuppressWarnings("rawtypes")
	private AuthenticationResponseDomain getSuccessResponse(String token, UserMaster userMst, List roles)
			throws AppException {

		AuthenticationResponseDomain baseApiResponse = new AuthenticationResponseDomain();
		baseApiResponse.setCode(QubyteStatus.SUCCESS.getCode());
		baseApiResponse.setStatus(QubyteStatus.STATUS_SUCCESS.getMessage());
		baseApiResponse.setMessage(QubyteStatus.SUCCESS.getMessage());
		baseApiResponse.setMappedOrgId(userMst.getMappedOrgId());
		baseApiResponse.setUserType(userMst.getUserType());
		baseApiResponse.setUserCode(userMst.getUserCd());
		baseApiResponse.setAuthToken(token);
		baseApiResponse.setRoles(roles);
		baseApiResponse.setFirstName(userMst.getUserFirstName());
		baseApiResponse.setLastName(userMst.getUserLastName());

		return baseApiResponse;
	}

	@SuppressWarnings("unused")
	private AuthenticationResponseDomain getFaliureResponse(String failureMsg) {

		AuthenticationResponseDomain baseApiResponse = new AuthenticationResponseDomain();
		baseApiResponse.setCode(QubyteStatus.SIGN_IN_ERROR.getCode());
		baseApiResponse.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
		baseApiResponse.setMessage(QubyteStatus.SIGN_IN_ERROR.getMessage() + " : " + failureMsg);
		baseApiResponse.setAuthToken("");

		return baseApiResponse;
	}
}
