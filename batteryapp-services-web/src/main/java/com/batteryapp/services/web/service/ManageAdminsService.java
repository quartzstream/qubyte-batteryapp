package com.batteryapp.services.web.service;

import com.batteryapp.services.web.domain.AddSuperAdminRequestDomain;
import com.batteryapp.services.web.domain.ViewSuperAdminRequestDomain;
import org.qubyte.base.domain.QueryParamHolder;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
public interface ManageAdminsService {

	boolean isMobileExist(String mobileNo);

	void saveUserDtls(AddSuperAdminRequestDomain cleanRequest);

	QueryParamHolder getSuperAdminDetail(ViewSuperAdminRequestDomain viewDomain, QueryParamHolder queryParamHolder);


}
