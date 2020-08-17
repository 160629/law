package com.chinatower.product.legalms.modules.flow.vo.flow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@ApiModel("纠纷处理意见配置")
@Table(name = "t_business_log_config")
public class BusinessLogConfigVO {
    @ApiModelProperty(name = "id", value = "id")
    @Id
    @Column(name = "id")
    private Integer id;

    @ApiModelProperty(name = "activityDefName", value = "纠纷处理意见配置环节集合")
    @Column(name = "activity_def_name")
    private String activityDefName;

    @ApiModelProperty(name = "flowDefName", value = "流程定义名称")
    @Column(name = "flow_def_name")
    private String flowDefName;

    @ApiModelProperty(name = "versionId", value = "流程版本号")
    @Column(name = "version_id")
    private Integer versionId;

    @ApiModelProperty(name = "isSpecial", value = "特殊字段")
    @Column(name = "is_special")
    private Integer isSpecial;

    @Override
    public String toString() {
        return "BusinessLogConfigVO{" +
                "id=" + id +
                ", activityDefName='" + activityDefName + '\'' +
                ", flowDefName='" + flowDefName + '\'' +
                ", versionId=" + versionId +
                ", isSpecial=" + isSpecial +
                '}';
    }

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityDefName() {
        return activityDefName;
    }

    public void setActivityDefName(String activityDefName) {
        this.activityDefName = activityDefName;
    }

    public String getFlowDefName() {
        return flowDefName;
    }

    public void setFlowDefName(String flowDefName) {
        this.flowDefName = flowDefName;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

}
