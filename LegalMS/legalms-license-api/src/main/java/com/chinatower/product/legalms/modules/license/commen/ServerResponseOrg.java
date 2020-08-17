package com.chinatower.product.legalms.modules.license.commen;

import java.io.Serializable;

public class ServerResponseOrg implements Serializable {

    private String flag;
    private String desc;

    public ServerResponseOrg(String flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    //调用成功
    public static ServerResponseOrg success(ResponseOrg responseOrg) {
        return new ServerResponseOrg(responseOrg.getFlag(),responseOrg.getDesc());
    }

    //异常提示
    public static ServerResponseOrg error(ResponseOrg responseOrg, String str) {
        return new ServerResponseOrg(responseOrg.getFlag(),responseOrg.getDesc()+":"+str);
    }
    //异常提示
    public static ServerResponseOrg error(ResponseOrg responseOrg) {
        return new ServerResponseOrg(responseOrg.getFlag(),responseOrg.getDesc());
    }


    @Override
    public String toString() {
        return "ServerResponseOrg{" +
                "flag='" + flag + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
