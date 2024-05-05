package com.batteryapp.services.web.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.batteryapp.services.web.dao.CommonDao;
import com.batteryapp.services.web.entity.UserMaster;
import org.qubyte.base.dao.impl.JPADAOIMPL;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Repository
public class CommonDaoImpl extends JPADAOIMPL implements CommonDao {

	@Override
	public List<UserMaster> getUserByUserCd(String usercd) {
		
		return getEntityManager().createQuery("from UserMaster where userCd = :userCd ", UserMaster.class)
				.setParameter("userCd", usercd).getResultList();
	}
}
