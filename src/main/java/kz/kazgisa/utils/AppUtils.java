package kz.kazgisa.utils;


import kz.kazgisa.data.entity.App;
import kz.kazgisa.data.entity.AppOrgStatus;
import kz.kazgisa.data.entity.AppOrganization;
import kz.kazgisa.data.entity.AppOzo;
import kz.kazgisa.data.entity.AppOzoStatus;
import kz.kazgisa.data.entity.AppStatus;
import kz.kazgisa.data.enums.Status;

import java.util.Date;

public class AppUtils {
    public static AppStatus createAppStatus(App app, Status status, Long userId, String message) {
        AppStatus appStatus = new AppStatus();
        appStatus.setDate(new Date());
        appStatus.setAppId(app.getId());
        appStatus.setStatus(status);
        appStatus.setUserId(userId);
        appStatus.setMessage(message);
        return appStatus;
    }

    public static AppOrgStatus createAppOrgStatus(AppOrganization appOrganization, Status status, Long userId, String message) {
        AppOrgStatus appOrgStatus = new AppOrgStatus();
        appOrgStatus.setDate(new Date());
        appOrgStatus.setAppOrganizationId(appOrganization.getId());
        appOrgStatus.setStatus(status);
        appOrgStatus.setUserId(userId);
        appOrgStatus.setMessage(message);
        return appOrgStatus;
    }

    public static AppOzoStatus createAppOzoStatus(AppOzo appOzo, Status status, Long userId, String message) {
        AppOzoStatus appOzoStatus = new AppOzoStatus();
        appOzoStatus.setDate(new Date());
        appOzoStatus.setAppOzoId(appOzo.getId());
        appOzoStatus.setStatus(status);
        appOzoStatus.setUserId(userId);
        appOzoStatus.setMessage(message);
        return appOzoStatus;
    }
}
