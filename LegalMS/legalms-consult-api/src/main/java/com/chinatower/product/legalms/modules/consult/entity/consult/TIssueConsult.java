package com.chinatower.product.legalms.modules.consult.entity.consult;

import com.chinatower.product.legalms.modules.flow.entity.unview.Tbase;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@ApiModel("法律支撑表")
@Table(name="t_issue_consult")
public class TIssueConsult extends Tbase {
    @Id
    @ApiModelProperty(value="id", name="id")
    private String id;

    @ApiModelProperty(value = "编号", name = "code")
    private String code;

    @ApiModelProperty(value = "标题", name = "title")
    private String title;

    @ApiModelProperty(value = "问题描述", name = "problem")
    private String problem;

    @ApiModelProperty(value = "问题答复", name = "businessAdvince")
    private String businessAdvice;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:dd",timezone = "GMT+8")
    @ApiModelProperty(value = "提交时间", name = "createTime")
    private Date createTime;

    @ApiModelProperty(value = "业务类别", name = "businessType")
    private String businessType;

    @ApiModelProperty(value = "支撑类别", name = "supportType")
    private String supportType;

    @ApiModelProperty(value = "重要程度", name = "importance")
    private String importance;

    @ApiModelProperty(value = "状态", name = "state")
    private String state;

    @ApiModelProperty(value="修改时间",name="modifyTime")
    private Date modifyTime;

    @ApiModelProperty(value = "是否送会签", name = "signDept")
    String signDept;

    @ApiModelProperty(value = "页面标识", name = "moduleName")
    String moduleName;

    @Override
    public String toString() {
        return "TIssueConsult{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", problem='" + problem + '\'' +
                ", businessAdvice='" + businessAdvice + '\'' +
                ", createTime=" + createTime +
                ", businessType='" + businessType + '\'' +
                ", supportType='" + supportType + '\'' +
                ", importance='" + importance + '\'' +
                ", state='" + state + '\'' +
                ", modifyTime=" + modifyTime +
                ", signDept='" + signDept + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", loginAcct='" + loginAcct + '\'' +
                ", loginName='" + loginName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", deptId='" + deptId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getSignDept() {
        return signDept;
    }

    public void setSignDept(String signDept) {
        this.signDept = signDept;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getBusinessAdvice() {
        return businessAdvice;
    }

    public void setBusinessAdvice(String businessAdvice) {
        this.businessAdvice = businessAdvice;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getSupportType() {
        return supportType;
    }

    public void setSupportType(String supportType) {
        this.supportType = supportType;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
