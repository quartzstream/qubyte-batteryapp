package com.batteryapp.services.web.service.impl;

import com.batteryapp.services.web.dao.ManageVendorsDao;
import com.batteryapp.services.web.domain.AddVendorsAdminRequestDomain;
import com.batteryapp.services.web.entity.UserMaster;
import com.batteryapp.services.web.service.ManageVendorsService;
import com.batteryapp.services.web.utils.UtilityProperties;
import org.apache.poi.util.StringUtil;
import org.qubyte.base.requestcontext.IRequestInitiationContext;
import org.qubyte.base.utils.CommonUtils;
import org.qubyte.base.utils.PasswordGenerator;
import org.qubyte.base.utils.QubytePasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Service
public class ManageVendorsServiceImpl implements ManageVendorsService {

    @Autowired
    private ManageVendorsDao manageVendorsDao;

    @Autowired
    private QubytePasswordEncoder qubytePasswordEncoder;

    @Autowired
    private UtilityProperties utilityProperties;

    @Autowired
    private IRequestInitiationContext irequestContext;

    @Override
    public boolean isMobileExist(String mobileNo) {

        List<UserMaster> userMasterList = manageVendorsDao.isMobileExist(mobileNo);

        return !userMasterList.isEmpty() && userMasterList.size() > 0;
    }

    @Transactional
    @Override
    public void saveUserDtls(AddVendorsAdminRequestDomain cleanRequest) {

        UserMaster userDtls = preaperUserDtls(cleanRequest);

        System.out.println("=========password : "+qubytePasswordEncoder.decrypt(userDtls.getEncryptedPassword()));
        manageVendorsDao.save(userDtls);
    }

    private UserMaster preaperUserDtls(AddVendorsAdminRequestDomain cleanRequest) {

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
        userDtls.setUserType("VA");
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
}
