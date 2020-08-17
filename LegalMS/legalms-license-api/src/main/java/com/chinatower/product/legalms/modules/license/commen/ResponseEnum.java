package com.chinatower.product.legalms.modules.license.commen;
/**
 * 服务返回数据编码
 */

public enum ResponseEnum {

    SUCCESS("CHNTLEGALMS","0", "成功"),
    ERROR("CHNTLEGALMS", "1", "失败");

    private String serviceId;

    private String rsp;

    private String errDesc;

    public String getServiceId() {
        return serviceId;
    }

    public String getRsp() {
        return rsp;
    }

    public String getErrDesc() {
        return errDesc;
    }

   private ResponseEnum(String serviceId, String rsp, String errDesc) {
        this.serviceId = serviceId;
        this.rsp = rsp;
        this.errDesc = errDesc;
    }
}
