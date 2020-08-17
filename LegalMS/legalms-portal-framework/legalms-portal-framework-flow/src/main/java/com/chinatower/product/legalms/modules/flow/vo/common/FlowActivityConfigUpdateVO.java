package com.chinatower.product.legalms.modules.flow.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "流程线配置更新")
public class FlowActivityConfigUpdateVO {

    @ApiModelProperty(name = "flowId", value = "流程定义ID", required = true)
    private String flowId;

    @ApiModelProperty(name = "flowIdOld", value = "流程定义ID(老)", required = true)
    private String flowIdOld;

    @ApiModelProperty(name = "actId", value = "环节ID")
    private String actId;

    @ApiModelProperty(name = "actName", value = "环节名称")
    private String actName;


    @ApiModelProperty(name = "actIdOld", value = "环节ID（老）")
    private String actIdOld;

    @ApiModelProperty(name = "actNameOld", value = "环节名称(老)")
    private String actNameOld;

    @ApiModelProperty(name = "versionId", value = "版本id")
    private Integer versionId;

    @ApiModelProperty(name = "versionIdOld", value = "版本id(老)")
    private Integer versionIdOld;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActIdOld() {
        return actIdOld;
    }

    public void setActIdOld(String actIdOld) {
        this.actIdOld = actIdOld;
    }

    public String getActNameOld() {
        return actNameOld;
    }

    public void setActNameOld(String actNameOld) {
        this.actNameOld = actNameOld;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public String getFlowIdOld() {
        return flowIdOld;
    }

    public void setFlowIdOld(String flowIdOld) {
        this.flowIdOld = flowIdOld;
    }

    public Integer getVersionIdOld() {
        return versionIdOld;
    }

    public void setVersionIdOld(Integer versionIdOld) {
        this.versionIdOld = versionIdOld;
    }

    public FlowActivityConfigUpdateVO() {
        //空的构造器
    }
}
