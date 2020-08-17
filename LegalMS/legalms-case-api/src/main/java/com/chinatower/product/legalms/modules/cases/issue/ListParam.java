package com.chinatower.product.legalms.modules.cases.issue;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("列表查询对象")
@Data
public class ListParam {
	@ApiModelProperty(value = "页码", name = "pageNum")
	public Integer pageNum;
	@ApiModelProperty(value = "单页数量", name = "pageSize")
	public Integer pageSize;
	@ApiModelProperty(value = "标题", name = "title")
	public String title;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "开始时间", name = "startDate", example = "2000-10-10 00:00:00")
	public Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@ApiModelProperty(value = "结束时间", name = "endDate", example = "2000-10-10 00:00:00")
	public Date endDate;
	@ApiModelProperty(value="我方争议主体名称",name="ourLawsuitBodyName")
	private String ourLawsuitBodyName;

	@ApiModelProperty(value="删除状态",name="deleteStatus")
	private Integer deleteStatus;

	@ApiModelProperty(value="案件状态",name="caseStatus")
	private String caseStatus;

	@ApiModelProperty(value = "编码", name = "caseCode")
	private String caseCode;

	@ApiModelProperty(value="对方争议主体名称",name="otherLawsuitBody")
	private String otherLawsuitBody;

	@ApiModelProperty(value="案件类型",name="deputeType")
	private String deputeType;

	@ApiModelProperty(value="涉案部门",name="id")
	private String id;

	@ApiModelProperty(value="flag",name="标志")
	private String flag;
	/*勿动*/
	@ApiModelProperty(value="creatorAccountName",name="起草人名称")
	private String creatorAccountName; //起草人

	@ApiModelProperty(value="creatorAccountId",name="起草人ID")
	private String creatorAccountId;
    /*勿动 用于权限查询*/
	@ApiModelProperty(value="companyCodeList",name="公司编码列表")
	private List<String> companyCodeList;
	/*勿动 分公司code（例：包含省 地）*/
	@ApiModelProperty(value="allOrgCodeList",name="组织编码列表")
	private List<String> allOrgCodeList;
    /*勿动 省或地 code*/
	@ApiModelProperty(value="singleOrgCodeList",name="")
	private List<String> singleOrgCodeList;
	/*勿动*/
	@ApiModelProperty(value="orgCodeList",name="组织编码列表")
	private List<String> orgCodeList;
    /*判断当前节点type*/
	@ApiModelProperty(value="typeList",name="typeList")
	private List<String> typeList;

	@ApiModelProperty(value = "组织结构树", name = "orgPath")
	private String orgPath;

	@ApiModelProperty(value = "案件所属专线", name = "caseSpecialLine")
	String caseSpecialLine;

	@ApiModelProperty(value = "涉案负责人ID", name = "involvedAccountId")
	String involvedAccountId;

	@ApiModelProperty(value = "涉案负责人名称", name = "involvedAccountName")
	String involvedAccountName;
}
