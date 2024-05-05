package com.batteryapp.services.web.controller;

import com.batteryapp.services.web.domain.AddSuperAdminRequestDomain;
import com.batteryapp.services.web.domain.ChangePasswordRequestDomain;
import com.batteryapp.services.web.service.CommonService;
import com.batteryapp.services.web.utils.ApiEndPoint;
import org.qubyte.base.controller.BaseRequestController;
import org.qubyte.base.domain.BaseApiResponse;
import org.qubyte.base.exception.SystemException;
import org.qubyte.base.logger.BaseLogger;
import org.qubyte.base.requestcontext.IRequestInitiationContext;
import org.qubyte.base.utils.QubyteStatus;
import org.qubyte.base.utils.SecurityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Alok kumar on 30-04-2024
 * @project batteryapp-services-web
 */
@RestController
@RequestMapping(ApiEndPoint.BASE_URL.BASE)
public class ChangePasswordController extends BaseRequestController {

    private static final Logger logger = BaseLogger.getLogger(ChangePasswordController.class);

    @Autowired
    private CommonService commonService;

    @Autowired
    private IRequestInitiationContext irequestContext;

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequestDomain requestDomain,
                                           HttpServletResponse httpResponse, HttpServletRequest httpRequest) {

        BaseApiResponse baseApiResponse = new BaseApiResponse();
        ChangePasswordRequestDomain cleanRequest = SecurityUtils.prepareCleanXSSDomain(requestDomain);

        try{

            Boolean isTimestamp = commonService.isValidTimeStamp(cleanRequest.getTimeStamp());
            if(!isTimestamp) {
                throw new SystemException(QubyteStatus.INVALID_DATETIME.getCode(), QubyteStatus.INVALID_DATETIME.getMessage());
            }

            if(!SecurityUtils.isUserCodeMatch(irequestContext.getUserInfo().getUsername(), cleanRequest.getUserCode())) {
                throw new SystemException(QubyteStatus.INVALID_USERCODE.getCode(), QubyteStatus.INVALID_USERCODE.getMessage());
            }

            if(!cleanRequest.getNewPassword().equals(cleanRequest.getRetypeNewPassword())) {
                throw new SystemException(QubyteStatus.INVALID_USERCODE.getCode(), QubyteStatus.INVALID_USERCODE.getMessage());
            }




        } catch (SystemException se) {

        } catch (Exception e) {

        }
        return null;
    }
}
