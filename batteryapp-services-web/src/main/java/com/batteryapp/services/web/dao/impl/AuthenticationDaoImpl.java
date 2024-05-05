package com.batteryapp.services.web.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.batteryapp.services.web.dao.AuthenticationDao;
import com.batteryapp.services.web.entity.UserMaster;
import org.qubyte.base.dao.impl.JPADAOIMPL;


/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Repository
public class AuthenticationDaoImpl extends JPADAOIMPL implements AuthenticationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAuthorities(UserMaster userMaster) {
		
		String q = "SELECT " + 
				"    rm.role_name AS role_authority_name " + 
				"	FROM " + 
				"         user_mst u " + 
				"    INNER JOIN user_group_map     ugm ON ( u.user_id = ugm.user_id ) " + 
				"    INNER JOIN role_mst           rm ON ugm.role_id = rm.role_id " + 
				"    INNER JOIN authority_mst      am ON ugm.authority_id = am.authority_id " + 
				"    INNER JOIN role_authority_map ram ON ugm.role_authority_id = ram.role_authority_id " + 
				"	WHERE " + 
				"    u.user_id = :userid " + 
				"	UNION " + 
				"	SELECT " + 
				"    am.authority_name AS role_authority_name " + 
				"	FROM " + 
				"         user_mst u " + 
				"    INNER JOIN user_group_map     ugm ON ( u.user_id = ugm.user_id ) " + 
				"    INNER JOIN role_mst           rm ON ugm.role_id = rm.role_id " + 
				"    INNER JOIN authority_mst      am ON ugm.authority_id = am.authority_id " + 
				"    INNER JOIN role_authority_map ram ON ugm.role_authority_id = ram.role_authority_id " + 
				"	WHERE " + 
				"    u.user_id = :userid";
		
		return  getEntityManager().createNativeQuery(q)
				.setParameter("userid", userMaster.getUserId()).getResultList();
	
	}

	@Transactional
	@Override
	public Boolean updateUserStatus(Long userId, String userLockStatus, String userStatus, String userStatusInDb,
			String userLockStatusInDb, int expiryDays) {
		
		Boolean isStatusUpdate = false;
		Query q = null;
		Calendar newExpiryDate = Calendar.getInstance();
		newExpiryDate.add(Calendar.DATE, expiryDays);
		Date date = newExpiryDate.getTime();
		
		if ((!userStatus.equals(userStatusInDb)) && (!userLockStatus.equals(userLockStatusInDb))) {
			q = getEntityManager().createQuery("update UserMaster e set e.status = :userStatus,e.lockStatus=:userLockStatus,e.lockStatusDt=:lockStatusDate,e.loginFailedCounts=:loginFailedCounts,e.statusDt=:statusDate , e.expiryDate=:expiryDate where e.userId=:userId")
					.setParameter("userStatus", userStatus)
					.setParameter("userLockStatus", userLockStatus)
					.setParameter("lockStatusDate", new Date())
					.setParameter("loginFailedCounts", 0L)
					.setParameter("statusDate", new Date())
					.setParameter("expiryDate", date)
					.setParameter("userId", userId);
		}
		if (userStatus.equals(userStatusInDb)) {
			q = getEntityManager().createQuery("update UserMaster e set e.lockStatus=:userLockStatus,e.lockStatusDt=:lockStatusDate,e.loginFailedCounts=:loginFailedCounts , e.expiryDate=:expiryDate where e.userId=:userId")
					.setParameter("userLockStatus", userLockStatus)
					.setParameter("lockStatusDate", new Date())
					.setParameter("loginFailedCounts", 0L)
					.setParameter("expiryDate", date)
					.setParameter("userId", userId);
		}
		if (userLockStatus.equals(userLockStatusInDb)) {
			q = getEntityManager().createQuery("update UserMaster e set e.status = :userStatus,e.statusDt=:statusDate , e.expiryDate=:expiryDate where e.userId=:userId")
					.setParameter("userStatus", userStatus)
					.setParameter("statusDate", new Date())
					.setParameter("expiryDate", date)
					.setParameter("userId", userId);
		}
		
		int i = q.executeUpdate();
		if(i>0) {
			isStatusUpdate = true;
		}
		return isStatusUpdate;
	}

}
