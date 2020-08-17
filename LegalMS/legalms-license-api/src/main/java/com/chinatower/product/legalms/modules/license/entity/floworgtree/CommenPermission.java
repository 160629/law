package com.chinatower.product.legalms.modules.license.entity.floworgtree;

/**
 * @Date: 2019/11/29 11:14
 * @Description: 用于接受权限flag标识
 */
public class CommenPermission {

    private String flag;//接受参数

    private String param;//冗余参数

    private String orgCode;

    private String flowId;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getFlag() {

        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
