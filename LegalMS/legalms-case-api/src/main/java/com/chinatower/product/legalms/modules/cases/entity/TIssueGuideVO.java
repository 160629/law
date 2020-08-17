package com.chinatower.product.legalms.modules.cases.entity;

import com.chinatower.product.legalms.modules.cases.api.MoneyCheckUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 引诉信息表
 *
 * @author wangyong
 * @date   2019/10/22
 */
@ApiModel("引诉信息表")
@Data
public class TIssueGuideVO{
    @ApiModelProperty(value="引诉ID",name="guideId")
    private String guideId;

    @ApiModelProperty(value="编号",name="guideCode")
    private String guideCode;

    @ApiModelProperty(value="标题",name="guideTitle")
    private String guideTitle;
    
    @ApiModelProperty(value="执行结果",name="guideResult")
    private String guideResult;
    
    @ApiModelProperty(value="重要程度",name="guideSize")
    private String guideSize;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value="修改时间",name="modifyTime")
    private Date modifyTime;

    @ApiModelProperty(value="我方争议主体",name="ourLawsuitBody")
    private String ourLawsuitBody;

    @ApiModelProperty(value="我方争议主体名称",name="ourLawsuitBodyName")
    private String ourLawsuitBodyName;
    
    @ApiModelProperty(value="对方争议主体名称",name="otherDeputeBody")
    private String otherDeputeBody;

    @ApiModelProperty(value="案由",name="caseReason")
    private String caseReason;

    @ApiModelProperty(value="争议金额",name="caseDeputeMoney",example="0")
    private String caseDeputeMoney;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    @ApiModelProperty(value="纠纷发生时间",name="caseHappenTime")
    private Date caseHappenTime;

    @ApiModelProperty(value="是否会签",name="signDept")
    private String signDept;

    @ApiModelProperty(value="附件",name="guideFile")
    private String guideFile;
    
    @ApiModelProperty(value="附件信息",name="files")
    private List<Map<String, Object>> files;

    @ApiModelProperty(value="执行金额",name="implMoney",example="0")
    private String implMoney;

    @ApiModelProperty(value="诉讼分类",name="guideMethod",example="0")
    private String guideMethod;

    @ApiModelProperty(value="状态",name="guideStatus")
    private String guideStatus;

    @ApiModelProperty(value="争议事实",name="caseDeputeTruth")
    private String caseDeputeTruth;

    @ApiModelProperty(value="纠纷处理建议",name="caseDeputeAdvice")
    private String caseDeputeAdvice;
	
	@ApiModelProperty("当前活动ID")
	private String currActivityDefId;
	
	@ApiModelProperty("当前活动名称")
	private String currActivityDefName;

    public String getCaseDeputeMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(caseDeputeMoney);
    }

    public void setCaseDeputeMoney(String caseDeputeMoney) {
        this.caseDeputeMoney = MoneyCheckUtil.checkMoneyIsEmpty(caseDeputeMoney);
    }

    public String getImplMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(implMoney);
    }

    public void setImplMoney(String implMoney) {
        this.implMoney = MoneyCheckUtil.checkMoneyIsEmpty(implMoney);
    }
}