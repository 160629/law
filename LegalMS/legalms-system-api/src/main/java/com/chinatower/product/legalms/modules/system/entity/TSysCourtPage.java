package com.chinatower.product.legalms.modules.system.entity;

import lombok.Data;

import java.util.List;

/**
 * @author shiyuqi
 * @date
 */
@Data
public class TSysCourtPage {
    private Integer pageSize; //页码

    private Integer pageNum;  //每页条数

    private  String province;  //省份

    private String city; //地市

    private String courtName; //法院名称

    private String id;//法院id

    private String pid;//上级法院

    private String courtLevel;//法院级别

    private List<String> idList;

    private String flag;

    private String pname;//上级法院名称

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourtLevel() {
        return courtLevel;
    }

    public void setCourtLevel(String courtLevel) {
        this.courtLevel = courtLevel;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Override
    public String toString() {
        return "TSysCourtPage{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", courtName='" + courtName + '\'' +
                ", id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", courtLevel='" + courtLevel + '\'' +
                ", idList=" + idList +
                ", flag='" + flag + '\'' +
                ", pname='" + pname + '\'' +
                '}';
    }
}
