package com.chinatower.product.legalms.modules.system.entity;

/**
 * @Date: 2020/6/18 16:36
 * @Description:
 */
public class CheckUserTokenVO {

    private  String token;
    private  String userId;
    private  String appAcctId;
    private  String activityInstId;
    private  String processInstId;
    private  String moduleName;
    private  String viewId;
    private  String mainProcessInstID;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppAcctId() {
        return appAcctId;
    }

    public void setAppAcctId(String appAcctId) {
        this.appAcctId = appAcctId;
    }

    public String getActivityInstId() {
        return activityInstId;
    }

    public void setActivityInstId(String activityInstId) {
        this.activityInstId = activityInstId;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getMainProcessInstID() {
        return mainProcessInstID;
    }

    public void setMainProcessInstID(String mainProcessInstID) {
        this.mainProcessInstID = mainProcessInstID;
    }
}
