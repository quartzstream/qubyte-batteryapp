package com.batteryapp.services.web.dao;

import java.util.List;

import com.batteryapp.services.web.domain.ViewSuperAdminRequestDomain;
import com.batteryapp.services.web.entity.UserMaster;
import org.qubyte.base.dao.JPADAO;
import org.qubyte.base.domain.QueryParamHolder;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
public interface ManageAdminsDao extends JPADAO {

	List<UserMaster> isMobileExist(String mobileNo);

	QueryParamHolder getSuperAdminDetail(ViewSuperAdminRequestDomain viewDomain, QueryParamHolder queryParamHolder);


}
