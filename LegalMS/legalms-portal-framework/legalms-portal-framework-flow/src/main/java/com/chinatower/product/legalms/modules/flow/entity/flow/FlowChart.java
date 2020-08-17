package com.chinatower.product.legalms.modules.flow.entity.flow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 刘晓亮
 * @create 2019-12-24
 */
@ApiModel("流程图对象")
public class FlowChart {
    @ApiModelProperty(name = "flowDefId", value = "流程定义id")
    private Long flowDefId;
    @ApiModelProperty(name = "flowDefName", value = "流程定义名称")
    private String flowDefName;
    @ApiModelProperty(name = "flowInstId", value = "流程实例id")
    private Long flowInstId;
    @ApiModelProperty(name = "flowInstName", value = "流程实例名称")
    private String flowInstName;
    @ApiModelProperty(name = "parentFlowDefId", value = "父流程定义id")
    private Long parentFlowDefId;
    @ApiModelProperty(name = "parentFlowDefName", value = "父流程定义名称")
    private String parentFlowDefName;
    @ApiModelProperty(name = "parentFlowInstId", value = "父流程实例id")
    private Long parentFlowInstId;
    @ApiModelProperty(name = "parentFlowInstName", value = "父流程实例名称")
    private String parentFlowInstName;
    @ApiModelProperty(name = "flowChartHtml", value = "流程图页面资源")
    private String flowChartHtml;

    public String getFlowChartHtml() {
        return flowChartHtml;
    }

    public void setFlowChartHtml(String flowChartHtml) {
        this.flowChartHtml = flowChartHtml;
    }

    public Long getParentFlowDefId() {
        return parentFlowDefId;
    }

    public void setParentFlowDefId(Long parentFlowDefId) {
        this.parentFlowDefId = parentFlowDefId;
    }

    public String getParentFlowDefName() {
        return parentFlowDefName;
    }

    public void setParentFlowDefName(String parentFlowDefName) {
        this.parentFlowDefName = parentFlowDefName;
    }

    public String getFlowInstName() {
        return flowInstName;
    }

    public void setFlowInstName(String flowInstName) {
        this.flowInstName = flowInstName;
    }

    public String getParentFlowInstName() {
        return parentFlowInstName;
    }

    public void setParentFlowInstName(String parentFlowInstName) {
        this.parentFlowInstName = parentFlowInstName;
    }

    public Long getFlowDefId() {
        return flowDefId;
    }

    public void setFlowDefId(Long flowDefId) {
        this.flowDefId = flowDefId;
    }

    public String getFlowDefName() {
        return flowDefName;
    }

    public void setFlowDefName(String flowDefName) {
        this.flowDefName = flowDefName;
    }

    public Long getFlowInstId() {
        return flowInstId;
    }

    public void setFlowInstId(Long flowInstId) {
        this.flowInstId = flowInstId;
    }

    public Long getParentFlowInstId() {
        return parentFlowInstId;
    }

    public void setParentFlowInstId(Long parentFlowInstId) {
        this.parentFlowInstId = parentFlowInstId;
    }
}
