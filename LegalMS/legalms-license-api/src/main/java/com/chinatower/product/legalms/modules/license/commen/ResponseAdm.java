package com.chinatower.product.legalms.modules.license.commen;

public enum  ResponseAdm {

    SUCCESS("000","成功"),
    ERROR("999","失败");

    private String flag;

    private String desc;

    public String getFlag() {
        return flag;
    }

    public String getDesc() {
        return desc;
    }

    private ResponseAdm(String flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }
}
