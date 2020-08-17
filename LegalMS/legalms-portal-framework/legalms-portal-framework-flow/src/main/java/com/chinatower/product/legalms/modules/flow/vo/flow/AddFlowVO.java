package com.chinatower.product.legalms.modules.flow.vo.flow;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.modules.flow.vo.common.MyOwnRuntimeException;
import com.eos.workflow.omservice.WFParticipant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("流程提交对象")
public class AddFlowVO <T>{
	@ApiModelProperty("业务模型")
	T model;
	@ApiModelProperty("会签部门参与者列表,无会签设置为空")
	List<OrgParticipantVO> orgs;
	@ApiModelProperty("下一步活动定义ID")
	String actDefParam;
	@ApiModelProperty("下一步活动定义名称")
	String actDefName;
	@ApiModelProperty("当前活动定义ID")
	String curActDefParam;
	@ApiModelProperty("当前活动定义名称")
	String curActDefName;

	@ApiModelProperty("流程限定名")
	String flowName;

	@ApiModelProperty("跳转页面标识")
	String moduleName;
	
	@ApiModelProperty("是否新增")
	Integer addFlag;

	@ApiModelProperty("流程版本id")
	Integer versionId;
	
	@ApiModelProperty("业务标题")
	String title;
	
	@ApiModelProperty("0,发短信,1不发短信")
	Integer smsFlag;
	
	
	public Integer getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(Integer smsFlag) {
		this.smsFlag = smsFlag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public Integer getAddFlag() {
		return addFlag;
	}

	public void setAddFlag(Integer addFlag) {
		this.addFlag = addFlag;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getCurActDefParam() {
		return curActDefParam;
	}

	public void setCurActDefParam(String curActDefParam) {
		this.curActDefParam = curActDefParam;
	}

	public String getCurActDefName() {
		return curActDefName;
	}

	public void setCurActDefName(String curActDefName) {
		this.curActDefName = curActDefName;
	}

	public List<OrgParticipantVO> getOrgs() {
		return orgs;
	}
	public void setOrgs(List<OrgParticipantVO> orgs) {
		this.orgs = orgs;
	}
	public T getModel() {
		return model;
	}
	public void setModel(T model) {
		this.model = model;
	}

	public String getActDefParam() {
		return actDefParam;
	}

	public String getActDefId(){
		return actDefParam;
	}

	public void setActDefParam(String actDefParam) {
		this.actDefParam = actDefParam;
	}
	public String getActDefName() {
		return actDefName;
	}
	public void setActDefName(String actDefName) {
		this.actDefName = actDefName;
	}


	public static List<Map<String, Object>> getObjectToMap(List<OrgParticipantVO> vos) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (OrgParticipantVO vo : vos) {
			Map<String, Object> map = new HashMap<>();
			Class<?> clazz = vo.getClass();
			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);
				String fieldName = field.getName();
				if("serialVersionUID".equals(fieldName)) {
					continue;
				}
				try {
					if(field.getType() == WFParticipant.class) {
						WFParticipant value = (WFParticipant) field.get(vo);
						map.put(fieldName, value);
					}
					else {
						Object value = field.get(vo);
						map.put(fieldName, value);
					}
				} catch (Exception e) {
					throw new MyOwnRuntimeException(e);
				}
			}
			list.add(map);
		}
		return list;
	}

	@Override
	public String toString() {
		return "AddFlowVO [model=" + model + ", orgs=" + orgs + ", actDefParam=" + actDefParam + ", actDefName="
				+ actDefName + ", curActDefParam=" + curActDefParam + ", curActDefName=" + curActDefName + ", flowName="
				+ flowName + ", moduleName=" + moduleName + ", addFlag=" + addFlag + ", versionId=" + versionId
				+ ", title=" + title + ", smsFlag=" + smsFlag + "]";
	}
}


