package com.chinatower.product.legalms.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "t_sys_quick_button")
@ToString
public class FlowContractVO implements Serializable {
    @Id
    @Column(name = "flowId")
    @JsonProperty("flow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String flowId;


    @JsonProperty("flowName")
    @Column(name = "flow_name")
    private String flowName;              //流程名字

    @JsonProperty("roleCode")
    @Column(name = "role_code")
    private String roleCode;            //角色编码

}
