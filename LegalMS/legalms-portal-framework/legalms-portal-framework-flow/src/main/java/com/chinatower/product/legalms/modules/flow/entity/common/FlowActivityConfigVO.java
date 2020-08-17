package com.chinatower.product.legalms.modules.flow.entity.common;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowMainVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name="t_flow_activity_config")
@ApiModel(value="会签Table")
public class FlowActivityConfigVO {

	@Column(name = "flow_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(name="flowId",value = "流程定义ID",required = true)
	private String flowId;

	@Column(name = "begin_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(name="beginId",value = "开始环节ID",required = true)
	private String beginId;

	@Column(name = "begin_name")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(name="beginName",value = "开始环节名称")
	private String beginName;

	@Column(name = "end_id")
	@ApiModelProperty(name="endId",value = "结束环节ID")
	private String endId;

	@Column(name = "end_name")
	@ApiModelProperty(name="endName",value = "结束环节名称")
	private String endName;

	@Column(name = "condition")
	@ApiModelProperty(name="condition",value = "0:不限制 10:省限制 1:同公司限制 3:同部门限制 4:同处室限制 5:返回经办 6:送结束 7:退回承办人再提交下一步直接出退回人，格式为：7#userId，该限制为系统自动增加，不需要人为配置")
	private String condition;

	@Column(name = "order")
	@ApiModelProperty(name="order",value = "排序")
	private Integer order;

	@Column(name = "hidden")
	@ApiModelProperty(name="hidden",value = "是否隐藏  0:不隐藏 1:不隐藏")
	private String hidden;

	@Column(name = "is_mut")
	@ApiModelProperty(name="isMut",value = "0:单选  1：多选")
	private String isMut;

	@Column(name="is_countersign")
	@ApiModelProperty(name="isCountersign",value = "是否为送会签")
	private String isCountersign; //是否为送会签

	@ApiModelProperty(name="viewCountersign",value = "是否送会签")
	private String viewCountersign; //是否送会签

	@JsonProperty("level")
	@Column(name = "level")
	private String level;  //层级(总部 01 省分 02 地市 03)

	@JsonProperty("importantLevel")
	@Column(name = "important_level")
	private String importantLevel;  // 普通 normal 一般 commonly 重大 weighty

	@JsonProperty("moduleName")
	@Column(name = "module_name")
	private String moduleName;  //对应的页面模块名称

	@JsonProperty("roleCodes")
	private String roleCodes;   //rolecode的用逗号分隔的串

	@JsonProperty("handoverLevel")
	@Column(name = "handover_level")
	private String handoverLevel;  // 交办标志(交办标志(共有,share省分标志,02地市标志,03))

	@JsonProperty("flag")
	private String flag;

	@JsonProperty("lineName")
	@Column(name = "line_name")
	private String lineName;

	@JsonProperty("businessId")
	private String businessId;

	@Column(name = "is_return")
	private String isReturn;

	@Column(name = "version_id")
	@ApiModelProperty(name="versionId",value = "版本id")
	private Integer versionId;

	@JsonProperty("mian")
	@ApiModelProperty("审批主对象")
	private TFlowMainVO mian;

	@JsonProperty("id")
	private String id;

	@JsonProperty("isSonReturn")
	@ApiModelProperty(name="isSonReturn",value = "是不是子流程退回主流程的线")
	private String isSonReturn;

	@Override
	public String toString() {
		return "FlowActivityConfigVO{" +
				"flowId='" + flowId + '\'' +
				", beginId='" + beginId + '\'' +
				", beginName='" + beginName + '\'' +
				", endId='" + endId + '\'' +
				", endName='" + endName + '\'' +
				", condition='" + condition + '\'' +
				", order=" + order +
				", hidden='" + hidden + '\'' +
				", isMut='" + isMut + '\'' +
				", isCountersign='" + isCountersign + '\'' +
				", viewCountersign='" + viewCountersign + '\'' +
				", level='" + level + '\'' +
				", importantLevel='" + importantLevel + '\'' +
				", moduleName='" + moduleName + '\'' +
				", roleCodes='" + roleCodes + '\'' +
				", handoverLevel='" + handoverLevel + '\'' +
				", flag='" + flag + '\'' +
				", lineName='" + lineName + '\'' +
				", businessId='" + businessId + '\'' +
				", isReturn='" + isReturn + '\'' +
				", versionId=" + versionId +
				", mian=" + mian +
				", id='" + id + '\'' +
				", isSonReturn='" + isSonReturn + '\'' +
				'}';
	}

	public String getIsSonReturn() {
		return isSonReturn;
	}

	public void setIsSonReturn(String isSonReturn) {
		this.isSonReturn = isSonReturn;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getBeginId() {
		return beginId;
	}

	public void setBeginId(String beginId) {
		this.beginId = beginId;
	}

	public String getBeginName() {
		return beginName;
	}

	public void setBeginName(String beginName) {
		this.beginName = beginName;
	}

	public String getEndId() {
		return endId;
	}

	public void setEndId(String endId) {
		this.endId = endId;
	}

	public String getEndName() {
		return endName;
	}

	public void setEndName(String endName) {
		this.endName = endName;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public String getIsMut() {
		return isMut;
	}

	public void setIsMut(String isMut) {
		this.isMut = isMut;
	}

	public String getIsCountersign() {
		return isCountersign;
	}

	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}

	public void setIsCountersign(String isCountersign) {
		this.isCountersign = isCountersign;
	}

	public String getViewCountersign() {
		return viewCountersign;
	}

	public void setViewCountersign(String viewCountersign) {
		this.viewCountersign = viewCountersign;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getImportantLevel() {
		return importantLevel;
	}

	public void setImportantLevel(String importantLevel) {
		this.importantLevel = importantLevel;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(String roleCodes) {
		this.roleCodes = roleCodes;
	}

	public String getHandoverLevel() {
		return handoverLevel;
	}

	public void setHandoverLevel(String handoverLevel) {
		this.handoverLevel = handoverLevel;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public TFlowMainVO getMian() {
		return mian;
	}

	public void setMian(TFlowMainVO mian) {
		this.mian = mian;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

}
