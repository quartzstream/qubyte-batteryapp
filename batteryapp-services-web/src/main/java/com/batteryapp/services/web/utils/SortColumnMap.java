package com.batteryapp.services.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
public class SortColumnMap {

	public static String getSuperAdminShortColumnMap(int index) {

		String columnName = "";
		List<String> list = new ArrayList<String>();

		list.add("UM.USER_CD");
		list.add("UM.USER_FIRST_NAME");
		list.add("UM.USER_MIDDLE_NAME");
		list.add("UM.USER_LAST_NAME");
		list.add("UM.EMAIL_ID");
		list.add("UM.ENCRYPTED_PASSWORD");
		list.add("UM.MOBILE_NO");
		list.add("UM.MAPPED_ORG_ID");
		list.add("UM.USER_TYPE");
		list.add("UM.DOB");
		list.add("UM.GENDER");
		list.add("UM.CREATE_DT");

		columnName = list.get(index);

		return columnName;
	}

	public static Map<String, Object> getSuperAdminSearchColumnMap() {
		
		Map<String, Object> sMap = new HashMap<>();
        
		sMap.put("user_cd","UM.USER_CD");
		sMap.put("first_name","UM.USER_FIRST_NAME");
		sMap.put("middle_name","UM.USER_MIDDLE_NAME");
		sMap.put("last_lame","UM.USER_LAST_NAME");
		sMap.put("email_ld","UM.EMAIL_ID");
		sMap.put("mobile_no","UM.MOBILE_NO");
		sMap.put("mapped_org_id","UM.MAPPED_ORG_ID");
		sMap.put("dob","UM.DOB");
		sMap.put("gender","UM.GENDER");
		sMap.put("create_dt","UM.CREATE_DT");

		return sMap;
	}

}
