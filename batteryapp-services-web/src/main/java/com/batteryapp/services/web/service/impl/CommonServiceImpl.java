package com.batteryapp.services.web.service.impl;

import java.util.Date;
import java.util.List;

import com.batteryapp.services.web.utils.UtilityProperties;
import org.qubyte.base.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.batteryapp.services.web.dao.CommonDao;
import com.batteryapp.services.web.entity.UserMaster;
import com.batteryapp.services.web.service.CommonService;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
@Service
public class CommonServiceImpl implements  CommonService {

	@Autowired 
	private CommonDao commonDao;

	@Autowired
	private UtilityProperties utilityProperties;
	
	@Override
	public UserMaster getUserByUserCd(String usercd) {
		
		List<UserMaster> listData = commonDao.getUserByUserCd(usercd);
		if(!listData.isEmpty() && listData.size()>0) {
			return listData.get(0);
		}
		return null;
	}

	@Override
	public Boolean isValidTimeStamp(String timeStamp) {

		Boolean isExist = false;

		if (DateTimeUtils.isDateFormatValid(timeStamp, "ddMMyyyyHHmmss")) {
			String format = "ddMMyyyyHHmmss";
			Date sysDate = new Date();
			String frmDt = DateTimeUtils.addMinutesInDateTime(utilityProperties.getMinusServerTime(), sysDate, format);
			String toDt = DateTimeUtils.addMinutesInDateTime(utilityProperties.getPlusServerTime(), sysDate, format);
			isExist = DateTimeUtils.ifExistBetweenDateRange(frmDt, toDt, timeStamp, format);
		}
		return isExist;
	}
}
