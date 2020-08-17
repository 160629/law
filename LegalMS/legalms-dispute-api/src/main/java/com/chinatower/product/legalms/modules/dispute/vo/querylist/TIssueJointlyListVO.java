package com.chinatower.product.legalms.modules.dispute.vo.querylist;

import java.util.Date;
import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.common.OrgDepLeader;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author 刘晓亮
 * @create 2019-12-31
 * 引诉纠纷综合查询条件
 */
public class TIssueJointlyListVO {
    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;
    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;
    @ApiModelProperty(value = "标题", name = "title")
    public String jointlyTitle;
    @ApiModelProperty(value = "编号", name = "code")
    public String jointlyCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "开始时间", name = "startDate", example = "2000-10-10 00:00:00")
    public Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "结束时间", name = "endDate", example = "2000-10-10 00:00:00")
    public Date endDate;
    @ApiModelProperty(value = "协办部门",name = "jointlyOrg")
    public String jointlyOrg;
    @ApiModelProperty(value = "关联案件",name = "caseTitle")
    private String caseTitle;
    @ApiModelProperty(value="组织机构树的类型",name="type")
    private String type;
    @ApiModelProperty(value="组织机构树的id",name="type")
    private String id;

    @ApiModelProperty(value="企业类型",name="enterpType")
    private String enterpType;/*单位编码 例：CT 代表铁塔 TE 能源 TZ 智联*/

    @ApiModelProperty(value="公司列表",name="orgList")
    private String orgPath;// 公司组织树

    @ApiModelProperty(value="分管领导管理部门列表",name="orgDepLeaders")
    private List<OrgDepLeader> orgDepLeaders;

    @ApiModelProperty(value="当前人部门ID",name="dept")
    private String deptId;

    @ApiModelProperty(value="当前人id",name="userId")
    private String userId;

    @ApiModelProperty(value="状态",name="jointlyStatus")
    private String jointlyStatus;

    @ApiModelProperty("上级公司")
    private List<String> preOrgId;

    @ApiModelProperty("执行单位id, codePath")
    private String unitIdPath;

    @ApiModelProperty("协办单位")
    private String  jointlyUnitName;

    @ApiModelProperty("协办单位id")
    private String jointlyUnitId;
    @ApiModelProperty(value="所属公司-固定条件",name="creatorUnitId")
    private String creatorUnitId;

    @ApiModelProperty(value="执行单位-固定条件",name="execUnitId")
    private String execUnitId;

    @ApiModelProperty(value = "",name = "")
    private List<String> orgCodeList;

    public List<String> getOrgCodeList() {
        return orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getCreatorUnitId() {
        return creatorUnitId;
    }

    public void setCreatorUnitId(String creatorUnitId) {
        this.creatorUnitId = creatorUnitId;
    }

    public String getExecUnitId() {
        return execUnitId;
    }

    public void setExecUnitId(String execUnitId) {
        this.execUnitId = execUnitId;
    }
    public List<String> getPreOrgId() {
        return preOrgId;
    }

    public void setPreOrgId(List<String> preOrgId) {
        this.preOrgId = preOrgId;
    }

    public String getUnitIdPath() {
        return unitIdPath;
    }

    public void setUnitIdPath(String unitIdPath) {
        this.unitIdPath = unitIdPath;
    }

    public String getJointlyUnitName() {
        return jointlyUnitName;
    }

    public void setJointlyUnitName(String jointlyUnitName) {
        this.jointlyUnitName = jointlyUnitName;
    }

    public String getJointlyUnitId() {
        return jointlyUnitId;
    }

    public void setJointlyUnitId(String jointlyUnitId) {
        this.jointlyUnitId = jointlyUnitId;
    }

    public String getJointlyStatus() {
        return jointlyStatus;
    }

    public void setJointlyStatus(String jointlyStatus) {
        this.jointlyStatus = jointlyStatus;
    }

    public String getJointlyTitle() {
        return jointlyTitle;
    }

    public void setJointlyTitle(String jointlyTitle) {
        this.jointlyTitle = jointlyTitle;
    }

    public String getJointlyCode() {
        return jointlyCode;
    }

    public void setJointlyCode(String jointlyCode) {
        this.jointlyCode = jointlyCode;
    }

    public String getJointlyOrg() {
        return jointlyOrg;
    }

    public void setJointlyOrg(String jointlyOrg) {
        this.jointlyOrg = jointlyOrg;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrgDepLeader> getOrgDepLeaders() {
        return orgDepLeaders;
    }

    public void setOrgDepLeaders(List<OrgDepLeader> orgDepLeaders) {
        this.orgDepLeaders = orgDepLeaders;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public String getEnterpType() {
        return enterpType;
    }

    public void setEnterpType(String enterpType) {
        this.enterpType = enterpType;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
