package com.chinatower.product.legalms.modules.filecommon.vo.filecommon;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_file_main")
public class FileMainVO implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String fileId;

    @Column(name = "file_name")
    private String fileName;           //文件名称

    @Column(name = "file_extension")
    private String fileExtension;      //文件后缀

    @Column(name = "file_http_url")
    private String fileHttpUrl;        //访问地址

    @Column(name = "file_upload_app_id")
    private String fileUploadAppId;   //文件模块类型

    @Column(name = "file_upload_user_id")
    private String fileUploadUserId;  //文件上传者ID

    @Column(name = "file_upload_time")
    private Date fileUploadTime;     //文件上传时间

    @Column(name = "file_status_update_time")
    private Date fileStatusUpdateTime;  //文件更新时间

    @Column(name = "file_status")
    private String fileStatus;     //文件状态

    @Column(name = "file_length")
    private String fileLength;       //文件长度

    @Column(name = "file_remark")
    private String fileRemark;       //备注

    @Column(name = "visibility_level")
    private String visibilityLevel;  //文件可见性级别

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileHttpUrl() {
        return fileHttpUrl;
    }

    public void setFileHttpUrl(String fileHttpUrl) {
        this.fileHttpUrl = fileHttpUrl;
    }

    public String getFileUploadAppId() {
        return fileUploadAppId;
    }

    public void setFileUploadAppId(String fileUploadAppId) {
        this.fileUploadAppId = fileUploadAppId;
    }

    public String getFileUploadUserId() {
        return fileUploadUserId;
    }

    public void setFileUploadUserId(String fileUploadUserId) {
        this.fileUploadUserId = fileUploadUserId;
    }

    public Date getFileUploadTime() {
        return fileUploadTime;
    }

    public void setFileUploadTime(Date fileUploadTime) {
        this.fileUploadTime = fileUploadTime;
    }

    public Date getFileStatusUpdateTime() {
        return fileStatusUpdateTime;
    }

    public void setFileStatusUpdateTime(Date fileStatusUpdateTime) {
        this.fileStatusUpdateTime = fileStatusUpdateTime;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFileLength() {
        return fileLength;
    }

    public void setFileLength(String fileLength) {
        this.fileLength = fileLength;
    }

    public String getFileRemark() {
        return fileRemark;
    }

    public void setFileRemark(String fileRemark) {
        this.fileRemark = fileRemark;
    }

    public String getVisibilityLevel() {
        return visibilityLevel;
    }

    public void setVisibilityLevel(String visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
    }
}
