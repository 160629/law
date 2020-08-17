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
public class TLegislationListVO {
    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;
    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;
    @ApiModelProperty(value = "标题", name = "title")
    public String title;
    @ApiModelProperty(value = "编号", name = "odd")
    public String odd;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "开始时间", name = "startDate", example = "2000-10-10 00:00:00")
    public Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "结束时间", name = "endDate", example = "2000-10-10 00:00:00")
    public Date endDate;
    @ApiModelProperty(value = "最小金额",name = "minAmount")
    public Double minAmount;
    @ApiModelProperty(value = "最大金额",name = "maxAmount")
    public Double maxAmount;
    @ApiModelProperty(value = "执行部门",name="executiveArm")
    public String executiveArm;
    @ApiModelProperty(value = "关联案件",name = "caseTitle")
    public String caseTitle;
    @ApiModelProperty(value="组织机构树的类型",name="type")
    private String type;
    @ApiModelProperty(value="组织机构树的id",name="type")
    private String id;
    @ApiModelProperty("状态")
    private String state;

    @ApiModelProperty("执行单位")
    private String  executeUnitName;

    @ApiModelProperty("执行单位id")
    private String executeUnitId;

    @ApiModelProperty("上级公司")
    private List<String> preOrgId;

    @ApiModelProperty("执行单位id, codePath")
    private String unitIdPath;

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

    @ApiModelProperty(value="所属公司-固定条件",name="creatorUnitId")
    private String creatorUnitId;

    @ApiModelProperty(value="执行单位-固定条件",name="execUnitId")
    private String execUnitId;

    @ApiModelProperty(value = "",name = "")
    private List<String> orgCodeList;

    @ApiModelProperty(value = "",name = "")
    private String creatorOrgId;

    public String getCreatorOrgId() {
        return creatorOrgId;
    }

    public void setCreatorOrgId(String creatorOrgId) {
        this.creatorOrgId = creatorOrgId;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getExecutiveArm() {
        return executiveArm;
    }

    public void setExecutiveArm(String executiveArm) {
        this.executiveArm = executiveArm;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOdd() {
        return odd;
    }

    public void setOdd(String odd) {
        this.odd = odd;
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

    public String getExecuteUnitName() {
        return executeUnitName;
    }

    public void setExecuteUnitName(String executeUnitName) {
        this.executeUnitName = executeUnitName;
    }

    public String getExecuteUnitId() {
        return executeUnitId;
    }

    public void setExecuteUnitId(String executeUnitId) {
        this.executeUnitId = executeUnitId;
    }


    public List<String> getOrgCodeList() {
        return orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }
}
