package com.chinatower.product.legalms.modules.cases.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("作废卷宗后新卷宗列表查询参数")
@Data
public class NewCaseListParam {
    @ApiModelProperty(value = "页码", name = "pageNum")
    public Integer pageNum;

    @ApiModelProperty(value = "单页数量", name = "pageSize")
    public Integer pageSize;

    @ApiModelProperty(value = "标题", name = "title")
    public String title;

    @ApiModelProperty(value = "编码", name = "caseCode")
    private String caseCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "开始时间", name = "startDate", example = "2000-10-10 00:00:00")
    public Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "结束时间", name = "endDate", example = "2000-10-10 00:00:00")
    public Date endDate;

    @ApiModelProperty(value="我方争议主体名称",name="ourLawsuitBodyName")
    private String ourLawsuitBodyName;

    @ApiModelProperty(value="对方争议主体名称",name="otherLawsuitBody")
    private String otherLawsuitBody;

    @ApiModelProperty(value="案件类型",name="deputeType")
    private String deputeType;

    @ApiModelProperty(value="卷宗创建单位id",name="creatorUnitId")
    private String creatorUnitId;

    @ApiModelProperty(value="作废的卷宗id",name="nullifyCaseId")
    private String nullifyCaseId;
}
