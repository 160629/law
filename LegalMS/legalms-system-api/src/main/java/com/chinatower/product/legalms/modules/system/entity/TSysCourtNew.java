package com.chinatower.product.legalms.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author shiyuqi
 * @date
 */
@Data
@Table(name = "t_sys_court_new")
@ToString
public class TSysCourtNew {
    @Id
    @Column(name = "id")
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;
    @JsonProperty("sname")
    @Column(name = "sname")
    private  String sname;
    @JsonProperty("pid")
    @Column(name = "pid")
    private   Integer  pid;
    @JsonProperty("hasNext")
    @Column(name = "has_ext")
    private String hasNext;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getHasNext() {
        return hasNext;
    }

    public void setHasNext(String hasNext) {
        this.hasNext = hasNext;
    }
}
