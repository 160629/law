package com.chinatower.product.legalms.modules.flow.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Table(name="t_flow_activity_power")
@ApiModel(value="流程权限配置表")
public class FlowActivityPowerVO {

	@Column(name = "flow_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(name="flowId",value = "流程定义ID",required = true)
	private String flowId;

	@Column(name = "flow_name")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(name="flowName",value = "流程定义名称",required = true)
	private String flowName;

	@Column(name = "act_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(name="actId",value = "环节ID",required = true)
	private String actId;

	@Column(name = "act_name")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(name="actName",value = "环节名称")
	private String actName;

	@JsonProperty("permissionJson")
	@Column(name = "permission_json")
	@ApiModelProperty(name="permissionJson",value = "当前环节权限json串")
	private String permissionJson;

	@Column(name = "role_ids")
	@ApiModelProperty(name="roleIds",value = "参与者角色  多个角色用,隔开")
	private String roleIds;

	@Column(name = "org_ids")
	@ApiModelProperty(name="orgIds",value = "参与者组织  多个组织用,隔开")
	private String orgIds;

	@Column(name = "version_id")
	@ApiModelProperty(name="versionId",value = "版本id")
	private Integer versionId;

	public FlowActivityPowerVO() {
	}

	public FlowActivityPowerVO(String flowId, String actId,Integer versionId) {
		this.flowId = flowId;
		this.actId = actId;
		this.versionId = versionId;
	}

	public String getPermissionJson() {
		return permissionJson;
	}

	public void setPermissionJson(String permissionJson) {
		this.permissionJson = permissionJson;
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

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(String orgIds) {
		this.orgIds = orgIds;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}
}
