package com.chinatower.product.legalms.modules.flow.vo.flow;

import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("流程处理对象")
public class AddFlowLogVO {
	@ApiModelProperty("流程日志对象")
	TFlowLog tFlowLog;
	@ApiModelProperty("会签参与者")
	List<OrgParticipantVO> orgs;
	@ApiModelProperty(value="是否会签",name="signDept")
	String signDept;
	@ApiModelProperty("业务更新字段")
	Map<String, Object> businessMap;
	@ApiModelProperty("纠纷处理意见 文件列表")
	String fileBusinessAdvice;
	@ApiModelProperty("业务标题")
	String title;
	@ApiModelProperty("业务编码")
	String code;
	@ApiModelProperty("页面标识")
	String moduleName;
	@ApiModelProperty("操作类型。0：下一步；1：退回")
	String optionType;
	@ApiModelProperty("0,发短信,1不发短信")
	Integer smsFlag;
	
	public Integer getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(Integer smsFlag) {
		this.smsFlag = smsFlag;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}
	public String getFileBusinessAdvice() {
		return fileBusinessAdvice;
	}

	public void setFileBusinessAdvice(String fileBusinessAdvice) {
		this.fileBusinessAdvice = fileBusinessAdvice;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, Object> getBusinessMap() {
		return businessMap;
	}

	public void setBusinessMap(Map<String, Object> businessMap) {
		this.businessMap = businessMap;
	}

	public TFlowLog gettFlowLog() {
		return tFlowLog;
	}

	public void settFlowLog(TFlowLog tFlowLog) {
		this.tFlowLog = tFlowLog;
	}

	public List<OrgParticipantVO> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<OrgParticipantVO> orgs) {
		this.orgs = orgs;
	}

	public String getSignDept() {
		return signDept;
	}

	public void setSignDept(String signDept) {
		this.signDept = signDept;
	}

	@Override
	public String toString() {
		return "AddFlowLogVO{" +
				"tFlowLog=" + tFlowLog +
				", orgs=" + orgs +
				", signDept='" + signDept + '\'' +
				", businessMap=" + businessMap +
				", fileBusinessAdvice='" + fileBusinessAdvice + '\'' +
				", title='" + title + '\'' +
				", code='" + code + '\'' +
				", moduleName='" + moduleName + '\'' +
				", optionType='" + optionType + '\'' +
				", smsFlag=" + smsFlag +
				'}';
	}
}
