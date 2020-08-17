package com.chinatower.product.legalms.modules.filecommon.vo.filecommon;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_file_share")
public class FileShareVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "file_share_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String fileShareId;		//ID

    @Column(name = "file_type")
    private Integer fileType;         //文件业务类型

    @Column(name = "file_share_business_key")
    private String fileShareBusinessKey;    //文件业务主键ID

    @Column(name = "file_share_type")
    private String fileShareType;    //文件归属模块

    @Column(name = "file_share_status_upd_user_id")
    private String fileShareStatusUpdUserId;    //文件上传者ID

    @Column(name = "file_order")
    private Integer fileOrder;      //文件排序号

    @Column(name = "file_share_status")
    private String fileShareStatus;    //文件状态

    @Column(name = "file_share_status_upd_time")
    private Date fileShareStatusUpdTime;   //文件更新时间

    @Column(name = "file_remark")
    private String fileRemark;   //备注

    @Column(name = "file_id")
    private String fileId;    //文件ID

    @Column(name = "process_inst_id")
    private String processInstId;     //流程实例id如果当前在子流程 那么这就是子流程实例id

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFileShareId() {
        return fileShareId;
    }

    public void setFileShareId(String fileShareId) {
        this.fileShareId = fileShareId;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFileShareBusinessKey() {
        return fileShareBusinessKey;
    }

    public void setFileShareBusinessKey(String fileShareBusinessKey) {
        this.fileShareBusinessKey = fileShareBusinessKey;
    }

    public String getFileShareType() {
        return fileShareType;
    }

    public void setFileShareType(String fileShareType) {
        this.fileShareType = fileShareType;
    }

    public String getFileShareStatusUpdUserId() {
        return fileShareStatusUpdUserId;
    }

    public void setFileShareStatusUpdUserId(String fileShareStatusUpdUserId) {
        this.fileShareStatusUpdUserId = fileShareStatusUpdUserId;
    }

    public Integer getFileOrder() {
        return fileOrder;
    }

    public void setFileOrder(Integer fileOrder) {
        this.fileOrder = fileOrder;
    }

    public String getFileShareStatus() {
        return fileShareStatus;
    }

    public void setFileShareStatus(String fileShareStatus) {
        this.fileShareStatus = fileShareStatus;
    }

    public Date getFileShareStatusUpdTime() {
        return fileShareStatusUpdTime;
    }

    public void setFileShareStatusUpdTime(Date fileShareStatusUpdTime) {
        this.fileShareStatusUpdTime = fileShareStatusUpdTime;
    }

    public String getFileRemark() {
        return fileRemark;
    }

    public void setFileRemark(String fileRemark) {
        this.fileRemark = fileRemark;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

}
