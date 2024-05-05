package com.batteryapp.services.web.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import com.batteryapp.services.web.entity.UserMaster;


/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
public interface AuthenticationService extends UserDetailsService {

	//List<String> getPreviousPasswordHistory(Long userId, Integer numberOfPreviousPassword);

	/**
	 * @param userMst
	 * @param userLockStatus
	 * @param userStatus
	 * @param expiryDays
	 * @return
	 */
	Boolean updateUserStatus(UserMaster userMst, String userLockStatus, String userStatus, int expiryDays);

	/**
	 * 
	 */
	void clearUserManagementUserListCache();

	/**
	 * @param userMst
	 */
	void updateUserMaster(UserMaster userMst);


}
