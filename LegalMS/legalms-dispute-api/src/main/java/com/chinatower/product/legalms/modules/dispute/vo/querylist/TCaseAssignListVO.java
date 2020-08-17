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
public class TCaseAssignListVO {
    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;
    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;
    @ApiModelProperty(value = "标题", name = "title")
    public String title;
    @ApiModelProperty(value = "编号", name = "code")
    public String code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "开始时间", name = "startDate", example = "2000-10-10 00:00:00")
    public Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value = "结束时间", name = "endDate", example = "2000-10-10 00:00:00")
    public Date endDate;
    @ApiModelProperty(value = "主送单位", name = "mainSeedOrg")
    public String mainSeedOrg;
    @ApiModelProperty(value = "转出单位", name = "assignOrg")
    public String assignOrg;
    @ApiModelProperty(value = "案件类型", name = "caseType")
    public String caseType;
    @ApiModelProperty(value="组织机构树的类型",name="type")
    private String type;
    @ApiModelProperty(value="组织机构树的id",name="type")
    private String id;

    @ApiModelProperty(value="状态",name="assignStatus")
    private String assignStatus;

    public String getAssignStatus() {
        return assignStatus;
    }

    public void setAssignStatus(String assignStatus) {
        this.assignStatus = assignStatus;
    }

    @Override
    public String toString() {
        return "TCaseAssignListVO{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", mainSeedOrg='" + mainSeedOrg + '\'' +
                ", assignOrg='" + assignOrg + '\'' +
                ", caseType='" + caseType + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", enterpType='" + enterpType + '\'' +
                ", orgPath='" + orgPath + '\'' +
                ", orgDepLeaders=" + orgDepLeaders +
                ", deptId='" + deptId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getAssignOrg() {
        return assignOrg;
    }

    public void setAssignOrg(String assignOrg) {
        this.assignOrg = assignOrg;
    }

    public String getMainSeedOrg() {
        return mainSeedOrg;
    }

    public void setMainSeedOrg(String mainSeedOrg) {
        this.mainSeedOrg = mainSeedOrg;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
