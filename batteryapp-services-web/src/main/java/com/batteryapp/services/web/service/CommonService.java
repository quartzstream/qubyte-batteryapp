package com.batteryapp.services.web.service;

import com.batteryapp.services.web.entity.UserMaster;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
public interface CommonService {

	UserMaster getUserByUserCd(String usercd);

    Boolean isValidTimeStamp(String timeStamp);
}
