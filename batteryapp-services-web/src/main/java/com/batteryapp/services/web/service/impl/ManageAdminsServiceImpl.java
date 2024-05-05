package com.batteryapp.services.web.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.batteryapp.services.web.dao.ManageAdminsDao;
import com.batteryapp.services.web.domain.AddSuperAdminRequestDomain;
import com.batteryapp.services.web.domain.ViewSuperAdminRequestDomain;
import com.batteryapp.services.web.entity.UserMaster;
import com.batteryapp.services.web.service.ManageAdminsService;
import com.batteryapp.services.web.utils.UtilityProperties;
import org.qubyte.base.domain.QueryParamHolder;
import org.qubyte.base.requestcontext.IRequestInitiationContext;
import org.qubyte.base.utils.CommonUtils;
import org.qubyte.base.utils.PasswordGenerator;
import org.qubyte.base.utils.QubytePasswordEncoder;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Service
public class ManageAdminsServiceImpl implements ManageAdminsService {

	@Autowired
	private ManageAdminsDao manageAdminsDao;
	
	@Autowired
	private QubytePasswordEncoder qubytePasswordEncoder;
	
	@Autowired
	private UtilityProperties utilityProperties;
	
	@Autowired 
	private IRequestInitiationContext irequestContext;
	
	@Override
	public boolean isMobileExist(String mobileNo) {
		
		List<UserMaster> userMasterList = manageAdminsDao.isMobileExist(mobileNo);

        return !userMasterList.isEmpty() && userMasterList.size() > 0;
    }

	@Transactional
	@Override
	public void saveUserDtls(AddSuperAdminRequestDomain cleanRequest) {
		
		UserMaster userDtls = preaperUserDtls(cleanRequest);
		
		System.out.println("=========password : "+qubytePasswordEncoder.decrypt(userDtls.getEncryptedPassword()));
		manageAdminsDao.save(userDtls);
		
	}

	private UserMaster preaperUserDtls(AddSuperAdminRequestDomain cleanRequest) {
		
		UserMaster userDtls = new UserMaster();
		
		userDtls.setUserCd(cleanRequest.getMobileNo());
		userDtls.setUserFirstName(cleanRequest.getFirstName());
		userDtls.setUserMiddleName(StringUtil.isNotBlank(cleanRequest.getMiddleName()) ? cleanRequest.getMiddleName() : null);
		userDtls.setUserLastName(StringUtil.isNotBlank(cleanRequest.getLastName()) ? cleanRequest.getLastName() : null);
		userDtls.setEmailId(cleanRequest.getEmailId());
		userDtls.setDepartment(cleanRequest.getDepartment());
		userDtls.setEncryptedPassword(qubytePasswordEncoder.encode(PasswordGenerator.getPassword(8)));
		userDtls.setPasswordExpiryDate(CommonUtils.addDaysInCurrentDay(utilityProperties.getPasswordExpDays()));
		userDtls.setExpiryDate(CommonUtils.addDaysInCurrentDay(utilityProperties.getExpiryDays()));
		userDtls.setCurrentlyLoggedIn("N");
		userDtls.setLockStatus("U");
		userDtls.setStatus("A");
		userDtls.setStatusDt(new Date());
		userDtls.setMobileNo(cleanRequest.getMobileNo());
		//userDtls.setLockStatusDt("");
		userDtls.setMappedOrgId(irequestContext.getUserInfo().getUsername());
		userDtls.setUserType("SA");
		userDtls.setPin(cleanRequest.getPin());
		userDtls.setDob(CommonUtils.getDate(cleanRequest.getDob(),"dd-MM-yyyy"));
		userDtls.setGender(cleanRequest.getGender());
		//userDtls.setPinExpiryDate("");
		userDtls.setSecurityQuesFlag("N");
		
		userDtls.setCreateUser(irequestContext.getUserInfo().getUsername());
		userDtls.setCreateDt(new Date());
		userDtls.setModifUser(irequestContext.getUserInfo().getUsername());
		userDtls.setModifDt(new Date());
		userDtls.setVersionId(1L);
		
		return userDtls;
	}

	@Override
	public QueryParamHolder getSuperAdminDetail(ViewSuperAdminRequestDomain viewDomain,
			QueryParamHolder queryParamHolder) {
		// TODO Auto-generated method stub
		return manageAdminsDao.getSuperAdminDetail( viewDomain , queryParamHolder);
	}


}
