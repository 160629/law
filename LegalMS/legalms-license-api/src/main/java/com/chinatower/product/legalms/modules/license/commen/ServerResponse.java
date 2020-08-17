package com.chinatower.product.legalms.modules.license.commen;

/**
 * 服务返回对象
 */
public class ServerResponse {

    private String serviceId;

    private String rsp;

    private String errDesc;


    public ServerResponse(String serviceId, String rsp, String errDesc) {
        this.serviceId = serviceId;
        this.rsp = rsp;
        this.errDesc = errDesc;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getRsp() {
        return rsp;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setRsp(String rsp) {
        this.rsp = rsp;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    //调用成功
    public static ServerResponse success(ResponseEnum responseEnum) {
        return new ServerResponse(responseEnum.getServiceId(),responseEnum.getRsp(), responseEnum.getErrDesc());
    }

    //异常提示
    public static ServerResponse error(ResponseEnum responseEnum, String str) {
        return new ServerResponse(responseEnum.getServiceId(),responseEnum.getRsp(), responseEnum.getErrDesc()+":"+str);
    }
    //异常提示
    public static ServerResponse error(ResponseEnum responseEnum) {
        return new ServerResponse(responseEnum.getServiceId(),responseEnum.getRsp(), responseEnum.getErrDesc());
    }

}
