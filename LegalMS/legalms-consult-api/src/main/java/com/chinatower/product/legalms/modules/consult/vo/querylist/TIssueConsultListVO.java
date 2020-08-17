package com.chinatower.product.legalms.modules.consult.vo.querylist;

import java.util.Date;
import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.common.OrgDepLeader;

import io.swagger.annotations.ApiModelProperty;

public class TIssueConsultListVO {
    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;
    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;

    @ApiModelProperty(value = "编号", name = "code")
    private String code;

    @ApiModelProperty(value = "标题", name = "title")
    private String title;

    @ApiModelProperty(value = "业务类别", name = "businessType")
    private String businessType;

    @ApiModelProperty(value = "支撑类别", name = "supportType")
    private String supportType;


    @ApiModelProperty(value = "状态", name = "state")
    private String state;

    @ApiModelProperty(value="修改时间",name="modifyTime")
    private Date modifyTime;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getSupportType() {
        return supportType;
    }

    public void setSupportType(String supportType) {
        this.supportType = supportType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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

    public String getEnterpType() {
        return enterpType;
    }

    public void setEnterpType(String enterpType) {
        this.enterpType = enterpType;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public List<OrgDepLeader> getOrgDepLeaders() {
        return orgDepLeaders;
    }

    public void setOrgDepLeaders(List<OrgDepLeader> orgDepLeaders) {
        this.orgDepLeaders = orgDepLeaders;
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
}
