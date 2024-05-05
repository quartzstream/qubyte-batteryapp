package com.batteryapp.services.web.dao;

import com.batteryapp.services.web.entity.UserMaster;
import org.qubyte.base.dao.JPADAO;

import java.util.List;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
public interface ManageVendorsDao extends JPADAO {
    List<UserMaster> isMobileExist(String mobileNo);
}
