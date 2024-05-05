package com.batteryapp.services.web.dao.impl;

import com.batteryapp.services.web.dao.ManageVendorsDao;
import com.batteryapp.services.web.entity.UserMaster;
import org.qubyte.base.dao.impl.JPADAOIMPL;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Repository
public class ManageVendorsDaoImpl extends JPADAOIMPL implements ManageVendorsDao {
    @Override
    public List<UserMaster> isMobileExist(String mobileNo) {

        return getEntityManager().createQuery("from UserMaster where mobileNo = :mobileNo ", UserMaster.class)
                .setParameter("mobileNo", mobileNo).getResultList();
    }
}
