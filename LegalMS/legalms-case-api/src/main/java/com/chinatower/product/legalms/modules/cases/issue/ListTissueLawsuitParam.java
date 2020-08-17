package com.chinatower.product.legalms.modules.cases.issue;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("列表查询对象")
@Data

public class ListTissueLawsuitParam {
	@ApiModelProperty(value = "页码", name = "pageNum")
	public Integer pageNum;
	@ApiModelProperty(value = "单页数量", name = "pageSize")
	public Integer pageSize;
	@ApiModelProperty(value = "标题", name = "title")
	public String title;
	@ApiModelProperty(value = "编号", name = "code")
	public String code;
	@ApiModelProperty(value = "审理机构", name = "approveOrg")
	public String approveOrg;
	@ApiModelProperty(value = "案件类型", name = "caseType")
	public String caseType;
	@ApiModelProperty(value = "主送单位", name = "mainSeedOrg")
	public String mainSeedOrg;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@ApiModelProperty(value = "开始时间", name = "startDate", example = "2000-10-10 00:00:00")
	public Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
	@ApiModelProperty(value = "结束时间", name = "endDate", example = "2000-10-10 00:00:00")
	public Date endDate;
    @ApiModelProperty(value="我方争议主体名称",name="ourLawsuitBodyName")
    private String ourLawsuitBodyName;
    @ApiModelProperty(value="对方争议主体名称",name="otherDeputeBody")
    private String otherDeputeBody;
	@ApiModelProperty(value="对方诉讼主体名称",name="theyLawsuitBody")
	private String theyLawsuitBody;
    @ApiModelProperty(value="1 =关联页面查询",name="correlate")
    private Integer correlate;

    @ApiModelProperty(value="执行结果",name="result")
    private String result;
    
    @ApiModelProperty(value="案件ID",name="caseId")
    private String caseId;
    @ApiModelProperty(value="状态",name="status")
    private String status;
	@ApiModelProperty(value="公司ID",name="orgId")
	private String orgId;
	@ApiModelProperty(value="公司名称",name="orgName")
	private String orgName;
	@ApiModelProperty(value="企业类型",name="enterpType")
	private String enterpType;/*单位编码 例：CT 代表铁塔 TE 能源 TZ 智联*/
	@ApiModelProperty(value="部门ID",name="deptId")
	private String deptId;
	@ApiModelProperty(value="起草人",name="createAcct")
	private String loginAcct;
	@ApiModelProperty(value="组织机构树的类型",name="type")
	private String type;
	@ApiModelProperty(value="组织机构树的id",name="type")
	private String id;
	@ApiModelProperty(value="组织机构树列表",name="orgList")
    private List<String> orgList;



}
