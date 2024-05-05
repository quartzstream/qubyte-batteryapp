package com.batteryapp.services.web.dao;

import java.util.List;

import com.batteryapp.services.web.entity.UserMaster;
import org.qubyte.base.dao.JPADAO;


/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
public interface AuthenticationDao extends JPADAO {

	/**
	 * @param userMaster
	 * @return
	 */
	List<String> getAuthorities(UserMaster userMaster);

	/**
	 * @param userId
	 * @param userLockStatus
	 * @param userStatus
	 * @param userLockStatusInDb
	 * @param expiryDays
	 * @param userStatusInDb
	 * @return
	 */
	Boolean updateUserStatus(Long userId, String userLockStatus, String userStatus, String userStatusInDb,
			String userLockStatusInDb, int expiryDays);

	

}
