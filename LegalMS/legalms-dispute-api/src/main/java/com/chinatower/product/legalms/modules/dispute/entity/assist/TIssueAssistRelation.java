package com.chinatower.product.legalms.modules.dispute.entity.assist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Table(name = "t_issue_assist_relation")
@ApiModel("协助执行单关联关系表")
public class TIssueAssistRelation {
    @Column(name = "id")
    @ApiModelProperty(value = "id", name = "id")
    private String id;

    @Column(name = "assist_id")
    @ApiModelProperty(value = "关联执行单id", name = "assistId")
    private String assistId;

    @ApiModelProperty(value = "关联执行单标题", name = "assistTitle")
    private String assistTitle;

    @Column(name = "assist_parent_id")
    @ApiModelProperty(value = "上级协助执行单id", name = "assistParentId")
    private String assistParentId;

    @ApiModelProperty(value = "上级协助执行单标题", name = "assistPTitle")
    private String assistPTitle;

    @Column(name = "org_id")
    @ApiModelProperty(value = "创建人单位id", name = "orgId")
    private String orgId;

    @Column(name = "create_time")
    @ApiModelProperty(value = "提交时间", name = "createTime")
    private Date createTime;

    @Column(name = "modify_time")
    @ApiModelProperty(value = "最后修改时间", name = "modifyTime")
    private Date modifyTime;

    @Column(name = "flow_id")
    @ApiModelProperty(value = "执行记录对应的流程实例id", name = "flowId")
    private String flowId;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getAssistTitle() {
        return assistTitle;
    }

    public void setAssistTitle(String assistTitle) {
        this.assistTitle = assistTitle;
    }

    public String getAssistPTitle() {
        return assistPTitle;
    }

    public void setAssistPTitle(String assistPTitle) {
        this.assistPTitle = assistPTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssistId() {
        return assistId;
    }

    public void setAssistId(String assistId) {
        this.assistId = assistId;
    }

    public String getAssistParentId() {
        return assistParentId;
    }

    public void setAssistParentId(String assistParentId) {
        this.assistParentId = assistParentId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public TIssueAssistRelation() {
    }

    public TIssueAssistRelation(String id, String assistId, String assistParentId, String orgId, Date createTime, Date modifyTime) {
        this.id = id;
        this.assistId = assistId;
        this.assistParentId = assistParentId;
        this.orgId = orgId;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "TIssueAssistRelation{" +
                "id='" + id + '\'' +
                ", assistId='" + assistId + '\'' +
                ", assistParentId='" + assistParentId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TIssueAssistRelation that = (TIssueAssistRelation) o;
        return id.equals(that.id) &&
                assistId.equals(that.assistId) &&
                assistParentId.equals(that.assistParentId) &&
                orgId.equals(that.orgId) &&
                createTime.equals(that.createTime) &&
                modifyTime.equals(that.modifyTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assistId, assistParentId, orgId, createTime, modifyTime);
    }
}
