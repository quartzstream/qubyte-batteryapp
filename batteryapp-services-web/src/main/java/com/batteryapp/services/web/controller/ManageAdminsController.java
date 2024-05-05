package com.batteryapp.services.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.batteryapp.services.web.service.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.batteryapp.services.web.domain.AddSuperAdminRequestDomain;
import com.batteryapp.services.web.domain.AddSuperAdminResponseDomain;
import com.batteryapp.services.web.domain.ViewSuperAdminRequestDomain;
import com.batteryapp.services.web.domain.ViewSuperAdminResponseDomain;
import com.batteryapp.services.web.service.ManageAdminsService;
import com.batteryapp.services.web.utils.ApiEndPoint;
import com.batteryapp.services.web.utils.SortColumnMap;
import org.qubyte.base.controller.BaseRequestController;
import org.qubyte.base.domain.QueryParamHolder;
import org.qubyte.base.exception.SystemException;
import org.qubyte.base.logger.BaseLogger;
import org.qubyte.base.requestcontext.IRequestInitiationContext;
import org.qubyte.base.utils.DataTableUtils;
import org.qubyte.base.utils.QubyteStatus;
import org.qubyte.base.utils.SecurityUtils;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@RestController
@RequestMapping(ApiEndPoint.BASE_URL.BASE)
public class ManageAdminsController extends BaseRequestController {

	private static final Logger logger = BaseLogger.getLogger(ManageAdminsController.class);

	@Autowired
	private ManageAdminsService manageAdminsService;

	@Autowired
	private IRequestInitiationContext irequestContext;

	@Autowired
	private CommonService commonService;

	@RequestMapping(value = ApiEndPoint.URL.ADD_ADMIN, method = RequestMethod.POST)
	public ResponseEntity<?> addSuperAdmin(@Valid @RequestBody AddSuperAdminRequestDomain requestDomain,
			HttpServletResponse httpResponse, HttpServletRequest httpRequest) {

		AddSuperAdminResponseDomain responseDomain = new AddSuperAdminResponseDomain();

		AddSuperAdminRequestDomain cleanRequest = SecurityUtils.prepareCleanXSSDomain(requestDomain);

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

			if (manageAdminsService.isMobileExist(cleanRequest.getMobileNo())) {
				throw new SystemException(QubyteStatus.EXIST_MOBILE.getCode(), QubyteStatus.EXIST_MOBILE.getMessage());
			}

			manageAdminsService.saveUserDtls(cleanRequest);

			responseDomain.setCode(QubyteStatus.SUCCESS.getCode());
			responseDomain.setStatus(QubyteStatus.STATUS_SUCCESS.getMessage());
			responseDomain.setMessage(QubyteStatus.SUCCESS.getMessage());
			responseDomain.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.ADD_ADMIN);
		} catch (SystemException se) {
			se.printStackTrace();
			responseDomain.setCode(se.getErrorCode());
			responseDomain.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
			responseDomain.setMessage(se.getMessage());
			responseDomain.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.ADD_ADMIN);
		} catch (Exception e) {
			e.printStackTrace();
			responseDomain.setCode(QubyteStatus.INTERNAL_ERROR.getCode());
			responseDomain.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
			responseDomain.setMessage(QubyteStatus.INTERNAL_ERROR.getMessage() + " : " + e.getMessage());
			responseDomain.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.ADD_ADMIN);
		}

		return ResponseEntity.status(responseDomain.getCode()).body(responseDomain);

	}

	@RequestMapping(value = ApiEndPoint.URL.VIEW_ADMIN, method = RequestMethod.POST)
	public ResponseEntity<?> viewSuperAdmin(@Valid @RequestBody ViewSuperAdminRequestDomain viewDomain,
			HttpServletResponse httpResponse, HttpServletRequest httpRequest) {

		QueryParamHolder queryParamHolder = new QueryParamHolder();
		ViewSuperAdminResponseDomain responseDomain = new ViewSuperAdminResponseDomain();
		Map<String, Object> sortMap = SortColumnMap.getSuperAdminSearchColumnMap();
		
		ViewSuperAdminRequestDomain cleanRequest = SecurityUtils.prepareCleanXSSDomain(viewDomain);

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

			if (StringUtils.isNotBlank(cleanRequest.getColumn()) || StringUtils.isNotBlank(cleanRequest.getValue())) {
				if (StringUtils.isBlank(cleanRequest.getColumn()) || StringUtils.isBlank(cleanRequest.getValue())) {
					throw new SystemException(QubyteStatus.MEDNATROY_FIELD.getCode(),
							QubyteStatus.MEDNATROY_FIELD.getMessage());
				} else {
					if (sortMap.containsKey(cleanRequest.getColumn())) {
						String columnVal = (String) sortMap.get(cleanRequest.getColumn());
						cleanRequest.setColumn(columnVal);
						cleanRequest.setValue(cleanRequest.getValue());
					} else {
						throw new SystemException(QubyteStatus.SEARCH_COLUMN.getCode(),
								QubyteStatus.SEARCH_COLUMN.getMessage() + sortMap.keySet());
					}
				}
			}

			DataTableUtils.setDtMap(httpRequest, queryParamHolder);
			queryParamHolder.setNative(true);
			int colNo = 0;
			try {
				String sColumn = queryParamHolder.getISortCol_0();
				colNo = Integer.parseInt(sColumn);
			} catch (Exception e) {
				colNo = 0;
				logger.error(e.toString());
			}
			queryParamHolder.setOrderByColumn(SortColumnMap.getSuperAdminShortColumnMap(colNo));

			queryParamHolder = manageAdminsService.getSuperAdminDetail(cleanRequest,
					queryParamHolder);

			responseDomain.setITotalRecords(queryParamHolder.getITotalRecords());
			responseDomain.setITotalDisplayRecords(queryParamHolder.getITotalDisplayRecords());
			responseDomain.setDatatTableSize(queryParamHolder.getDatatTableSize());
			responseDomain.setAaData(queryParamHolder.getAaData());
			if (queryParamHolder.getDatatTableSize() > 0) {
				responseDomain.setCode(QubyteStatus.SUCCESS.getCode());
				responseDomain.setStatus(QubyteStatus.STATUS_SUCCESS.getMessage());
				responseDomain.setMessage(QubyteStatus.SUCCESS.getMessage());
				responseDomain.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.VIEW_ADMIN);
			} else {
				responseDomain.setCode(QubyteStatus.RECORD_NOT_FOUND.getCode());
				responseDomain.setStatus(QubyteStatus.STATUS_SUCCESS.getMessage());
				responseDomain.setMessage(QubyteStatus.RECORD_NOT_FOUND.getMessage());
				responseDomain.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.VIEW_ADMIN);
			}

		} catch (SystemException se) {
			se.printStackTrace();
			responseDomain.setCode(se.getErrorCode());
			responseDomain.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
			responseDomain.setMessage(se.getMessage());
			responseDomain.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.VIEW_ADMIN);
		} catch (Exception e) {
			e.printStackTrace();
			responseDomain.setCode(QubyteStatus.INTERNAL_ERROR.getCode());
			responseDomain.setStatus(QubyteStatus.STATUS_FAILD.getMessage());
			responseDomain.setMessage(QubyteStatus.INTERNAL_ERROR.getMessage() + " : " + e.getMessage());
			responseDomain.setEndpoint(ApiEndPoint.BASE_URL.BASE + ApiEndPoint.URL.VIEW_ADMIN);
		}

		return ResponseEntity.status(responseDomain.getCode()).body(responseDomain);
	}
}
