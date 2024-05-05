package com.batteryapp.services.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.batteryapp.services.web.domain.AddVendorsAdminRequestDomain;
import com.batteryapp.services.web.domain.AddVendorsAdminResponseDomain;
import com.batteryapp.services.web.service.CommonService;
import com.batteryapp.services.web.service.ManageVendorsService;
import org.qubyte.base.exception.SystemException;
import org.qubyte.base.utils.QubyteStatus;
import org.qubyte.base.utils.SecurityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.batteryapp.services.web.utils.ApiEndPoint;
import org.qubyte.base.controller.BaseRequestController;
import org.qubyte.base.logger.BaseLogger;
import org.qubyte.base.requestcontext.IRequestInitiationContext;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@RestController
@RequestMapping(ApiEndPoint.BASE_URL.BASE)
public class ManageVendorsController extends BaseRequestController {

	private static final Logger logger = BaseLogger.getLogger(ManageVendorsController.class);
	
	@Autowired
	private IRequestInitiationContext irequestContext;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ManageVendorsService manageVendorsService;

	@RequestMapping(value = ApiEndPoint.URL.ADD_VENDORS, method = RequestMethod.POST)
	public ResponseEntity<?> addVendorsAdmin(@Valid @RequestBody AddVendorsAdminRequestDomain requestDomain,
			HttpServletResponse httpResponse, HttpServletRequest httpRequest) {

		AddVendorsAdminResponseDomain responseDomain = new AddVendorsAdminResponseDomain();

		AddVendorsAdminRequestDomain cleanRequest = SecurityUtils.prepareCleanXSSDomain(requestDomain);

		try {

			Boolean isTimestamp = commonService.isValidTimeStamp(cleanRequest.getTimeStamp());
			if(!isTimestamp) {

				throw new SystemException(QubyteStatus.INVALID_DATETIME.getCode(), QubyteStatus.INVALID_DATETIME.getMessage());
			}

			if(!SecurityUtils.isUserCodeMatch(irequestContext.getUserInfo().getUsername(), cleanRequest.getUserCode())) {

				throw new SystemException(QubyteStatus.INVALID_USERCODE.getCode(), QubyteStatus.INVALID_USERCODE.getMessage());

			}
			if (!"SA".equalsIgnoreCase(irequestContext.getUserInfo().getUserType())) {

				throw new SystemException(QubyteStatus.DIFFERENT_USER_TYPE.getCode(), QubyteStatus.DIFFERENT_USER_TYPE.getMessage());
			}

			if (manageVendorsService.isMobileExist(cleanRequest.getMobileNo())) {
				throw new SystemException(QubyteStatus.EXIST_MOBILE.getCode(), QubyteStatus.EXIST_MOBILE.getMessage());
			}

			manageVendorsService.saveUserDtls(cleanRequest);

			responseDomain.setCode(QubyteStatus.SUCCESS.getCode());
			responseDomain.setStatus(QubyteStatus.STATUS_SUCCESS.getMessage());
			responseDomain.setMessage(QubyteStatus.SUCCESS.getMessage());
			responseDomain.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.ADD_VENDORS);
		} catch (SystemException se) {
			se.printStackTrace();
			responseDomain.setCode(se.getErrorCode());
			responseDomain.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
			responseDomain.setMessage(se.getMessage());
			responseDomain.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.ADD_VENDORS);
		} catch (Exception e) {
			e.printStackTrace();
			responseDomain.setCode(QubyteStatus.INTERNAL_ERROR.getCode());
			responseDomain.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
			responseDomain.setMessage(QubyteStatus.INTERNAL_ERROR.getMessage() + " : " + e.getMessage());
			responseDomain.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.ADD_VENDORS);
		}

		return ResponseEntity.status(responseDomain.getCode()).body(responseDomain);

	}
}
