package com.batteryapp.services.web.service;

import com.batteryapp.services.web.domain.AddVendorsAdminRequestDomain;

/**
 * @author Alok kumar on 28-04-2024
 * @project batteryapp-services-web
 */
public interface ManageVendorsService {
    boolean isMobileExist(String mobileNo);

    void saveUserDtls(AddVendorsAdminRequestDomain cleanRequest);
}
