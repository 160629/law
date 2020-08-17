package com.chinatower.product.legalms.modules.flow.entity.common;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 刘晓亮
 * @create 2019-10-16 16:40
 */

@ApiModel("附件信息")
public class FileShareVO {
    @ApiModelProperty("文件ID")
    private String fileId;

    // 文件业务关联表
    @ApiModelProperty("ID")
    private String fileShareId;

    @ApiModelProperty("文件业务类型0:正文原稿;1:正式文件;2:生成附件;3:上传附件")
    private Integer fileType;

    @ApiModelProperty("文件业务主键ID")
    private String fileShareBusinessKey;

    @ApiModelProperty("文件归属模块")
    private String fileShareType;

    @ApiModelProperty("文件上传者ID")
    private String fileShareStatusUpdUserId;

    @ApiModelProperty("文件排序号")
    private Double fileOrder;

    @ApiModelProperty("文件状态")
    private String fileShareStatus;

    @ApiModelProperty("文件更新时间")
    private Date fileShareStatusUpdTime;

    @ApiModelProperty("备注")
    private String fileRemark;

    // 文件主表
	@ApiModelProperty("文件名称")
    private String fileName;

	@ApiModelProperty("文件后缀名")
    private String fileExtension;

	@ApiModelProperty("访问地址")
	private String fileHttpUrl;

	@ApiModelProperty("文件模块类型")
	private String fileUploadAppId;

	@ApiModelProperty("文件上传者ID")
	private String fileUploadUserId;

	@ApiModelProperty("文件上传时间")
	private String fileUploadTime;

	@ApiModelProperty("文件更新时间")
	private String fileStatusUpdateTime;

	@ApiModelProperty("文件状态")
	private String fileStatus;

	@ApiModelProperty("文件大小")
	private String fileLength;


	@ApiModelProperty("文件可见性级别")
	private String visibilityLevel;
	public String getFileId() {
		return fileId;
	}

	public FileShareVO setFileId(String fileId) {
		this.fileId = fileId;
		return this;
	}

	public String getFileShareId() {
		return fileShareId;
	}

	public FileShareVO setFileShareId(String fileShareId) {
		this.fileShareId = fileShareId;
		return this;
	}

	public Integer getFileType() {
		return fileType;
	}

	public FileShareVO setFileType(Integer fileType) {
		this.fileType = fileType;
		return this;
	}

	public String getFileShareBusinessKey() {
		return fileShareBusinessKey;
	}

	public FileShareVO setFileShareBusinessKey(String fileShareBusinessKey) {
		this.fileShareBusinessKey = fileShareBusinessKey;
		return this;
	}

	public String getFileShareType() {
		return fileShareType;
	}

	public FileShareVO setFileShareType(String fileShareType) {
		this.fileShareType = fileShareType;
		return this;
	}

	public String getFileShareStatusUpdUserId() {
		return fileShareStatusUpdUserId;
	}

	public FileShareVO setFileShareStatusUpdUserId(String fileShareStatusUpdUserId) {
		this.fileShareStatusUpdUserId = fileShareStatusUpdUserId;
		return this;
	}

	public Double getFileOrder() {
		return fileOrder;
	}

	public FileShareVO setFileOrder(Double fileOrder) {
		this.fileOrder = fileOrder;
		return this;
	}

	public String getFileShareStatus() {
		return fileShareStatus;
	}

	public FileShareVO setFileShareStatus(String fileShareStatus) {
		this.fileShareStatus = fileShareStatus;
		return this;
	}

	public Date getFileShareStatusUpdTime() {
		return fileShareStatusUpdTime;
	}

	public FileShareVO setFileShareStatusUpdTime(Date fileShareStatusUpdTime) {
		this.fileShareStatusUpdTime = fileShareStatusUpdTime;
		return this;
	}

	public String getFileRemark() {
		return fileRemark;
	}

	public FileShareVO setFileRemark(String fileRemark) {
		this.fileRemark = fileRemark;
		return this;
	}

	public String getFileName() {
		return fileName;
	}

	public FileShareVO setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public FileShareVO setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
		return this;
	}

	public String getFileHttpUrl() {
		return fileHttpUrl;
	}

	public FileShareVO setFileHttpUrl(String fileHttpUrl) {
		this.fileHttpUrl = fileHttpUrl;
		return this;
	}

	public String getFileUploadAppId() {
		return fileUploadAppId;
	}

	public FileShareVO setFileUploadAppId(String fileUploadAppId) {
		this.fileUploadAppId = fileUploadAppId;
		return this;
	}

	public String getFileUploadUserId() {
		return fileUploadUserId;
	}

	public FileShareVO setFileUploadUserId(String fileUploadUserId) {
		this.fileUploadUserId = fileUploadUserId;
		return this;
	}

	public String getFileUploadTime() {
		return fileUploadTime;
	}

	public FileShareVO setFileUploadTime(String fileUploadTime) {
		this.fileUploadTime = fileUploadTime;
		return this;
	}

	public String getFileStatusUpdateTime() {
		return fileStatusUpdateTime;
	}

	public FileShareVO setFileStatusUpdateTime(String fileStatusUpdateTime) {
		this.fileStatusUpdateTime = fileStatusUpdateTime;
		return this;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public FileShareVO setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
		return this;
	}

	public String getFileLength() {
		return fileLength;
	}

	public FileShareVO setFileLength(String fileLength) {
		this.fileLength = fileLength;
		return this;
	}

	public String getVisibilityLevel() {
		return visibilityLevel;
	}

	public FileShareVO setVisibilityLevel(String visibilityLevel) {
		this.visibilityLevel = visibilityLevel;
		return this;
	}
    
    
    
}
