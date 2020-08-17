package com.chinatower.product.legalms.modules.license.commen;

public enum  ResponseOrg {
    SUCCESS("000", "OK"),
    SUP_SUCCESS("000", "TRUE"),
    SUP_ERROR("999",""),
    ERROR("999", "FALSE");
    private String flag;
    private String desc;

    public String getFlag() {
        return flag;
    }


    public String getDesc() {
        return desc;
    }


    private ResponseOrg(String flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

}
