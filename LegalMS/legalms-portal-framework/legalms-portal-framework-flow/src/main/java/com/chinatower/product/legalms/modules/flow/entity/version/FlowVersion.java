package com.chinatower.product.legalms.modules.flow.entity.version;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FlowVersion {
    private Integer id;
    private Integer versionId;
    private String flowId;
    private String flowName;
    private String createAccountId;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;
    private String isNow;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getCreateAccountId() {
        return createAccountId;
    }

    public void setCreateAccountId(String createAccountId) {
        this.createAccountId = createAccountId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsNow() {
        return isNow;
    }

    public void setIsNow(String isNow) {
        this.isNow = isNow;
    }

    @Override
    public String toString() {
        return "FlowVersion{" +
                "id=" + id +
                ", versionId=" + versionId +
                ", flowId='" + flowId + '\'' +
                ", flowName='" + flowName + '\'' +
                ", createAccountId='" + createAccountId + '\'' +
                ", createTime=" + createTime +
                ", isNow='" + isNow + '\'' +
                '}';
    }
}
