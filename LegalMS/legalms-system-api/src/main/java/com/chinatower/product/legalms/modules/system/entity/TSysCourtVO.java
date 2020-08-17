package com.chinatower.product.legalms.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @author shiyuqi
 * @date
 */
@Data
@Table(name = "t_sys_court")
@ToString
public class TSysCourtVO {
    @Id
    @Column(name = "id")
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @JsonProperty("pid")
    @Column(name = "pid")
    private String pid;
    @JsonProperty("courtName")
    @Column(name = "court_name")
    private String courtName;
    @JsonProperty("courtOldName")
    @Column(name = "court_old_name")
    private String courtOldName;
    @JsonProperty("province")
    @Column(name = "province")
    private String province;
    @JsonProperty("provinceCode")
    @Column(name = "province_code")
    private String provinceCode;
    @JsonProperty("courtLevel")
    @Column(name = "court_level")
    private String courtLevel;
    @JsonProperty("city")
    @Column(name = "city")
    private String city;
    @JsonProperty("cityCode")
    @Column(name = "city_code")
    private String cityCode;
    @JsonProperty("county")
    @Column(name = "county")
    private String county;
    @JsonProperty("countyCode")
    @Column(name = "county_code")
    private String countyCode;
    @JsonProperty("field1")
    @Column(name = "field1")
    private String field1;
    @JsonProperty("state")
    @Column(name = "state")
    private String state;
    private String pname;

    private List<TSysCourtVO> sysCourtVOList;

    private String isParent;

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<TSysCourtVO> getSysCourtVOList() {
        return sysCourtVOList;
    }

    public void setSysCourtVOList(List<TSysCourtVO> sysCourtVOList) {
        this.sysCourtVOList = sysCourtVOList;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getCourtOldName() {
        return courtOldName;
    }

    public void setCourtOldName(String courtOldName) {
        this.courtOldName = courtOldName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCourtLevel() {
        return courtLevel;
    }

    public void setCourtLevel(String courtLevel) {
        this.courtLevel = courtLevel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }
}
