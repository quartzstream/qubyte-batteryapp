package com.batteryapp.services.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.batteryapp.services.web.dao.AuthenticationDao;
import com.batteryapp.services.web.entity.UserMaster;
import com.batteryapp.services.web.service.AuthenticationService;
import com.batteryapp.services.web.service.CommonService;
import org.qubyte.base.domain.UserInfo;
import org.qubyte.base.logger.BaseLogger;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger logger = BaseLogger.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	private AuthenticationDao authenticationDao;
	
	@Autowired
	private CommonService commonService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserInfo userInfo =null;
		try{
			
			UserMaster userMaster = commonService.getUserByUserCd(username);

		 if(userMaster == null){
			 throw new UsernameNotFoundException(username+"::User not found.");
		 }
		 
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

		List<String> authorityMasterList = authenticationDao.getAuthorities(userMaster);
		
			if(authorityMasterList.size()>0){
				for(String authorityMaster:authorityMasterList){
					SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(authorityMaster);
					authorities.add(simpleGrantedAuthority);
				}
			}
		 userInfo = prepareUserInfo(userMaster,authorities);

		 if(userInfo.getIsCredentialChangeRequired()){
			 authorities=new ArrayList<SimpleGrantedAuthority>();
             SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority("Forced_Change_Password");
				authorities.add(simpleGrantedAuthority);
				 userInfo = prepareUserInfo(userMaster,authorities);
		 }
		}

		catch(Exception e){
			logger.error(e.getMessage());
			throw new UsernameNotFoundException(e.toString());

		}
		//UserDetails user = new User(userInfo.getUsername(), userInfo.getPassword(), userInfo.isAccountNonExpired(), userInfo.isAccountNonLocked(), userInfo.isCredentialsNonExpired(), userInfo.isEnabled(), authorities);
		return userInfo;
	}

	private UserInfo prepareUserInfo(UserMaster userMaster, Collection<SimpleGrantedAuthority> authorities) {
		
		boolean isEnabled=false;
		boolean isAccountNonLocked=false;
		boolean isAccountNonExpired=false;
		boolean isCredentialsNonExpired=true;
		boolean isCredentialChangeRequired=true;

		UserInfo userInfo =null;

			if(!(userMaster.getStatus().equals("D")))
			{
				isEnabled=(true);
			}
			Date currentDate=getCurrentDate();

	    	if((userMaster.getExpiryDate().after(currentDate))||(userMaster.getExpiryDate().equals(currentDate))){
	    		isAccountNonExpired=(true);
	    	}

	    	if((userMaster.getStatus().equals("I"))){
	    		isAccountNonExpired=(false);
	    	}

	    	if((userMaster.getPasswordExpiryDate().after(currentDate))||(userMaster.getPasswordExpiryDate().equals(currentDate))){
	    		//isCredentialsNonExpired=(true);
	    		isCredentialChangeRequired=false;
	    	}

			if(userMaster.getLockStatus().equals("U"))
			{
				isAccountNonLocked=(true);
			}

	    	userInfo = new UserInfo(userMaster.getUserCd(), userMaster.getEncryptedPassword(), isEnabled, isAccountNonExpired,isCredentialsNonExpired , isAccountNonLocked, authorities);

	    	userInfo.setUserFullName(checkAndReplace(userMaster.getUserFirstName())+" "+ checkAndReplace(userMaster.getUserLastName()));
			userInfo.setIsCredentialChangeRequired(isCredentialChangeRequired);
	    	userInfo.setUserId(userMaster.getUserId());
	    	userInfo.setLastLoginDateTime(userMaster.getLastLoginDate());
	    	userInfo.setCurrentLoginDateTime(currentDate);
	    	userInfo.setUserMailId(userMaster.getEmailId());
	    	userInfo.setMappedOrgId(userMaster.getMappedOrgId());
	    	userInfo.setUserType(userMaster.getUserType());
	    	
		return userInfo;
	}

	private String checkAndReplace(String str) {
		
		if (str==null || str.equals("null"))	{
			return "";
		}
		 return str;
	}

	private Date getCurrentDate() {
		
		Date currentDate = null;
		Calendar cal = Calendar.getInstance();
		currentDate = cal.getTime();
		return currentDate;
	}

	@Override
	public Boolean updateUserStatus(UserMaster userMst, String userLockStatus, String userStatus, int expiryDays) {
		
		return authenticationDao.updateUserStatus(userMst.getUserId(),userLockStatus,userStatus,userMst.getStatus(),userMst.getLockStatus(),expiryDays);
	}

	@CacheEvict(value = "userManagementUserListCache", allEntries = true)
	@Override
	public void clearUserManagementUserListCache() {
		
		logger.debug("===================== : clear User Cache");
	}

	@Transactional
	@Override
	public void updateUserMaster(UserMaster userMst) {
		authenticationDao.update(userMst);
		
	}

	

}
