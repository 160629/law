package com.chinatower.product.legalms.modules.dispute.entity;



/**
 * @Date: 2019/12/16 12:46
 * @Description:
 */
public class CommonParam {


    private String beginId;
    private String endId;
    private String flowId;
    private String loginAcct;
    private Long processInstId;
    private WhereParam whereParam;

    private Integer versionId;

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public void setBeginId(String beginId) {
        this.beginId = beginId;
    }

    public String getBeginId() {
        return beginId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public String getEndId() {
        return endId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct;
    }

    public String getLoginAcct() {
        return loginAcct;
    }


    public void setWhereParam(WhereParam whereParam) {
        this.whereParam = whereParam;
    }

    public WhereParam getWhereParam() {
        return whereParam;

    }

    public Long getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(Long processInstId) {
        this.processInstId = processInstId;
    }
}
