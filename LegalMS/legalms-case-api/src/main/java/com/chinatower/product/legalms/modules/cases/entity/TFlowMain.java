package com.chinatower.product.legalms.modules.cases.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 审批表
 *
 * @author wangyong
 * @date   2019/10/08
 */
@Table(name="t_flow_main")
@ApiModel("审批主数据")
@Data
public class TFlowMain {
	@Id
	@ApiModelProperty(value="流程ID",name="flowId")
    private Long flowId;

	@ApiModelProperty(value="流程名称",name="flowName")
    private String flowName;

	@ApiModelProperty(value="流程状态",name="flowStatus")
    private String flowStatus;

	@ApiModelProperty(value="创建时间",name="createTime")
    private Date createTime;

	@ApiModelProperty(value="创建ID",name="createUserId")
    private String createUserId;

	@ApiModelProperty(value="创建人名称",name="createUserName")
    private String createUserName;

	@ApiModelProperty(value="审批事项ID",name="approveItemId")
    private String approveItemId;

	@ApiModelProperty(value="审批事项类型",name="approveItemType")
    private String approveItemType;
	
	@ApiModelProperty(value="审批事项名称",name="approveItemName")
    private String approveItemName;
    
	@ApiModelProperty(value="部门名称",name="orgName")
    private String orgName;
    
	@ApiModelProperty(value="部门ID",name="orgId")
    private String orgId;

    @ApiModelProperty(value="跳转页面标识",name="moduleName")
	private String moduleName;

}