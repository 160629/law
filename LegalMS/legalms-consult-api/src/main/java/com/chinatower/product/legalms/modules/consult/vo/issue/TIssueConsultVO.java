package com.chinatower.product.legalms.modules.consult.vo.issue;

import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.modules.flow.entity.unview.Tbase;
import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowMainVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 法律支撑
 */
@ApiModel("法律支撑")
public class TIssueConsultVO extends Tbase {

    @ApiModelProperty(value="id",name="id")
    private String id;

    @ApiModelProperty(value="编号",name="code")
    private String code;

    @ApiModelProperty(value="标题",name="title")
    private String title;

    @ApiModelProperty(value = "问题描述", name = "problem")
    private String problem;

    @ApiModelProperty(value = "问题答复", name = "businessAdvice")
    private String businessAdvice;

    @ApiModelProperty(value = "业务类别", name = "businessType")
    private String businessType;

    @ApiModelProperty(value = "支撑类别", name = "supportType")
    private String supportType;

    @ApiModelProperty(value = "重要程度", name = "importance")
    private String importance;

    @ApiModelProperty(value = "状态", name = "state")
    private String state;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:dd",timezone = "GMT+8")
    @ApiModelProperty(value = "最后更新时间", name = "modifyTime")
    private String modifyTime;

    @ApiModelProperty(value="附件",name="guideFile")
    private String consultFile;

    @ApiModelProperty(value="附件信息",name="files")
    private List<Map<String, Object>> files;

    @ApiModelProperty("审批主对象")
    private TFlowMainVO mian;

    @ApiModelProperty(name = "isUnView", value = "是否已阅")
    private String isUnView;

    @ApiModelProperty(value = "页面标识", name = "moduleName")
    String moduleName;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getConsultFile() {
        return consultFile;
    }

    public void setConsultFile(String consultFile) {
        this.consultFile = consultFile;
    }

    public List<Map<String, Object>> getFiles() {
        return files;
    }

    public void setFiles(List<Map<String, Object>> files) {
        this.files = files;
    }

    public TFlowMainVO getMian() {
        return mian;
    }

    public void setMian(TFlowMainVO mian) {
        this.mian = mian;
    }

    public String getIsUnView() {
        return isUnView;
    }

    public void setIsUnView(String isUnView) {
        this.isUnView = isUnView;
    }

    @Override
    public String toString() {
        return "TIssueConsultVO{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", problem='" + problem + '\'' +
                ", businessAdvice='" + businessAdvice + '\'' +
                ", businessType='" + businessType + '\'' +
                ", supportType='" + supportType + '\'' +
                ", importance='" + importance + '\'' +
                ", state='" + state + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", consultFile='" + consultFile + '\'' +
                ", files=" + files +
                ", mian=" + mian +
                ", isUnView='" + isUnView + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", loginAcct='" + loginAcct + '\'' +
                ", loginName='" + loginName + '\'' +
                ", orgName='" + orgName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", deptId='" + deptId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", createTime=" + createTime +
                ", permissionJson='" + permissionJson + '\'' +
                '}';
    }
}
