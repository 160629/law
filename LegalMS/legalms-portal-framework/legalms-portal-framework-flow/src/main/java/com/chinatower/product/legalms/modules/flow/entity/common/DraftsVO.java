package com.chinatower.product.legalms.modules.flow.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
@Table(name="t_pub_drafts")
@ApiModel("草稿箱实体类")
public class DraftsVO {

    @Id
    @ApiModelProperty(value="ID（不传）",name="id" ,hidden = true)
    private Integer id;

    @ApiModelProperty(value="标题",name="lawCaseTitle")
    private String lawCaseTitle;

    @ApiModelProperty(value="编号",name="lawCaseCode")
    private String lawCaseCode;

    @ApiModelProperty(value="申请类别",name="applyType")
    private String applyType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value="创建时间",name="createTime",example = "2019-10-22 14:44:48",hidden = true)
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value="更新时间（不传）",name="updateTime",hidden = true)
    private Date updateTime;

    @ApiModelProperty(value="审批事项ID",name="approveItemId")
    private String approveItemId;

    @ApiModelProperty(value="审批事项类型",name="approveItemType")
    private String approveItemType;

    @ApiModelProperty(value="审批事项ID名称",name="approveItemName")
    private String approveItemName;

    @ApiModelProperty(value="跳转页面标识",name="moduleName")
    private String moduleName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value="开始时间",name="startDate",example = "2019-10-22 14:44:48")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value="结束时间",name="endDate",example = "2019-10-22 14:44:48")
    private Date endDate;

    @ApiModelProperty(value="页码",name="pageNum",hidden = true)
    private Integer pageNum;

    @ApiModelProperty(value="单页数量",name="pageSize",hidden = true)
    private Integer pageSize;

    @ApiModelProperty(value="approveItemIdList",name="approveItemIdList")
    private List<String> approveItemIdList;

    @ApiModelProperty(value="approveItemTypesList",name="approveItemTypesList")
    private List<String> approveItemTypesList;

    @ApiModelProperty(value="approveItemIds",name="approveItemIds",hidden = true)
    private List<String> approveItemIds;

    private List<String> approveItemNameList;

    public List<String> getApproveItemNameList() {
        return approveItemNameList;
    }

    public void setApproveItemNameList(List<String> approveItemNameList) {
        this.approveItemNameList = approveItemNameList;
    }

    private String userCode;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public DraftsVO(String approveItemType, List<String> approveItemIds) {
        this.approveItemType = approveItemType;
        this.approveItemIds = approveItemIds;
    }

    public List<String> getApproveItemIdList() {
        return approveItemIdList;
    }

    public void setApproveItemIdList(List<String> approveItemIdList) {
        this.approveItemIdList = approveItemIdList;
    }

    public List<String> getApproveItemTypesList() {
        return approveItemTypesList;
    }

    public void setApproveItemTypesList(List<String> approveItemTypesList) {
        this.approveItemTypesList = approveItemTypesList;
    }

    public List<String> getApproveItemIds() {
        return approveItemIds;
    }

    public void setApproveItemIds(List<String> approveItemIds) {
        this.approveItemIds = approveItemIds;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
    public String getModuleName() {
        return moduleName;
    }

    public DraftsVO setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public String getApproveItemId() {
        return approveItemId;
    }

    public DraftsVO setApproveItemId(String approveItemId) {
        this.approveItemId = approveItemId;
        return this;
    }

    public String getApproveItemType() {
        return approveItemType;
    }

    public DraftsVO setApproveItemType(String approveItemType) {
        this.approveItemType = approveItemType;
        return this;
    }

    public String getApproveItemName() {
        return approveItemName;
    }

    public DraftsVO setApproveItemName(String approveItemName) {
        this.approveItemName = approveItemName;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public DraftsVO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLawCaseTitle() {
        return lawCaseTitle;
    }

    public DraftsVO setLawCaseTitle(String lawCaseTitle) {
        this.lawCaseTitle = lawCaseTitle;
        return this;
    }

    public String getLawCaseCode() {
        return lawCaseCode;
    }

    public DraftsVO setLawCaseCode(String lawCaseCode) {
        this.lawCaseCode = lawCaseCode;
        return this;
    }

    public String getApplyType() {
        return applyType;
    }

    public DraftsVO setApplyType(String applyType) {
        this.applyType = applyType;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public DraftsVO setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public DraftsVO setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

	public DraftsVO(String approveItemType, String approveItemId) {
		super();
		this.approveItemType = approveItemType;
		this.approveItemId = approveItemId;
	}

	public DraftsVO() {
		super();
	}
    
    
}