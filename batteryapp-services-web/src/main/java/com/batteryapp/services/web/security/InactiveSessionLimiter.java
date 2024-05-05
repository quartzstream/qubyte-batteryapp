package com.batteryapp.services.web.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alok kumar on 01-05-2024
 * @project batteryapp-services-web
 */
public class InactiveSessionLimiter {

	private static final Map<String, UserCache> userUriHitCountMap = new HashMap<String, UserCache>();

	public static boolean isRequestAllowedForUser(String token, String userName, Long inactiveAllowedMinutes) {
		if (userUriHitCountMap.containsKey(userName)) {
			UserCache userCache = userUriHitCountMap.get(userName);
			if (token.equals(userCache.getToken())) {
				Long currDate = new Date().getTime();
				if ((currDate - userCache.getLastActiveTime()) > (inactiveAllowedMinutes * 60000)) {
					return false;
				}
			}
		}
		UserCache userCache = new UserCache(token);
		userUriHitCountMap.put(userName, userCache);
		return true;
	}
}

class UserCache {
	private Long lastActiveTime;
	private String token;

	public UserCache(String token) {
		this.lastActiveTime = new Date().getTime();
		this.token = token;
	}

	public Long getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(Long lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
