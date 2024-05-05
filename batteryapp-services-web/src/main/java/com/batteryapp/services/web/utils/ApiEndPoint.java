package com.batteryapp.services.web.utils;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
public interface ApiEndPoint {

	interface BASE_URL {

		String BASE = "/batteryapp/web";
	}

	interface URL {

		String LOGIN_AUTH = "/auth";
		String ADD_ADMIN = "/admin/add";
		String VIEW_ADMIN = "/admin/view";
		String CHANGE_PASSWORD = "/change/password";
		String ADD_VENDORS = "/vendors/add";
		String VIEW_VENDORS = "/vendors/view";
	}
}
