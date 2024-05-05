package com.batteryapp.services.web.dao;

import java.util.List;

import com.batteryapp.services.web.entity.UserMaster;
import org.qubyte.base.dao.JPADAO;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
public interface CommonDao extends JPADAO {

	List<UserMaster> getUserByUserCd(String usercd);
}
