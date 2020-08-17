package com.chinatower.product.legalms.modules.cases.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_case_relationship")
@Data
@ToString
@ApiModel(value="关联Table")
public class RelationshipVO {

    /**
     *  关联表
     */

    @Column(name = "business_type")
    @ApiModelProperty(name="businessType",value = "业务表类型",required = true)
    private String businessType;   //业务表类型

    @Id
    @Column(name = "case_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="caseId",value = "案件id",required = true)
    private String caseId;  //案件id

    @Id
    @Column(name = "business_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="businessId",value = "业务表id",required = true)
    private String businessId;  //业务表id

    @Column(name = "tittle")
    @ApiModelProperty(name="tittle",value = "标题",required = true)
    private String tittle;   //标题

    @Column(name = "code")
    @ApiModelProperty(name="code",value = "编号",required = true)
    private String code;   //编号

    @Column(name = "module_name")
    @ApiModelProperty(name="moduleName",value = "跳转页面标识",required = true)
    private String moduleName;   //跳转页面标识

    @Column(name = "is_auto")
    @ApiModelProperty(name="isAuto",value = "是否为自动生成",required = true)
    private String isAuto;   //是否为自动生成标识

    @Column(name = "is_delete")
    @ApiModelProperty(name="isDelete",value = "关联关系是否可以删除",required = true)
    private String isDelete;// 关联关系是否可以删除

    @ApiModelProperty(name="flowStatus",value = "流程状态",required = true)
    private String flowStatus;

    @ApiModelProperty(name="createTime",value = "创建时间",required = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    @ApiModelProperty(name="oldCaseId",value = "更新前caseId")
    private String oldCaseId;

    @ApiModelProperty(name="optionType",value = "操作类型")
    private String optionType;
}
