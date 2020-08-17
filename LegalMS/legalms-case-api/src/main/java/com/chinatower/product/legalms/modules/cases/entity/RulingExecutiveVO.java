package com.chinatower.product.legalms.modules.cases.entity;

import com.chinatower.product.legalms.modules.cases.api.MoneyCheckUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;

@Table(name = "t_case_ruling_executive")
@Data
@ToString
@ApiModel(value="裁决执行Table")
public class RulingExecutiveVO {
    /*
     * 裁决执行
     * 所有字段信息 移动到卷宗主表中（作为结案信息再前台展示）
     */

    @Id
    @Column(name = "ruling_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(name="rulingId",value = "执行ID",required = true)
    private String rulingId;  //执行ID

    @Column(name = "case_id")
    @ApiModelProperty(name="caseId",value = "案件ID",required = true)
    private String caseId;   //案件ID

    @Column(name = "document_order")
    @ApiModelProperty(name="documentOrder",value = "法律文书执行单号")
    private String documentOrder;  //法律文书执行单号

    @Column(name = "our_ruling_body")
    @ApiModelProperty(name="ourRulingBody",value = "执行行政处罚机关名称")
    private String ourRulingBody; //我方执行主体名称

    @Column(name = "other_ruling_body")
    @ApiModelProperty(name="otherRulingBody",value = "对方执行主体名称")
    private String otherRulingBody;  //对方执行主体名称

    @Column(name = "perform_content")
    @ApiModelProperty(name="performContent",value = "执行内容")
    private String performContent; //执行内容

    @Column(name = "ruling_role")
    @ApiModelProperty(name="rulingRole",value = "我方执行部门/人员")
    private String rulingRole; //我方执行部门/人员

    @Column(name = "end_time")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(name="endTime",value = "执行完毕时间")
    private Date endTime; //执行完毕时间

    @Column(name = "executive_way")
    @ApiModelProperty(name="executiveWay",value = "执行方式")
    private String executiveWay; //执行方式

    @Column(name = "ruling_money")
    @ApiModelProperty(name="rulingMoney",value = "裁决/和解金额")
    private String rulingMoney; //裁决/和解金额

    @Column(name = "executive_money")
    @ApiModelProperty(name="executiveMoney",value = "执行金额")
    private String executiveMoney; //执行金额

    @Column(name = "help_loss_money")
    @ApiModelProperty(name="helpLossMoney",value = "挽回损失金额")
    private String helpLossMoney; //挽回损失金额

    @Column(name = "loss_money")
    @ApiModelProperty(name="lossMoney",value = "损失金额")
    private String lossMoney; //损失金额

    @Column(name = "lawyer_money")
    @ApiModelProperty(name="lawyerMoney",value = "律师费")
    private String lawyerMoney; //律师费

    @Column(name = "lawsuit_money")
    @ApiModelProperty(name="lawsuitMoney",value = "诉讼费")
    private String lawsuitMoney; //诉讼费

    @Column(name = "status")
    @ApiModelProperty(name="status",value = "状态")
    private String status; //状态

    public String getRulingMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(rulingMoney);
    }

    public void setRulingMoney(String rulingMoney) {
        this.rulingMoney = MoneyCheckUtil.checkMoneyIsEmpty(rulingMoney);
    }

    public String getExecutiveMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(executiveMoney);
    }

    public void setExecutiveMoney(String executiveMoney) {
        this.executiveMoney = MoneyCheckUtil.checkMoneyIsEmpty(executiveMoney);
    }

    public String getHelpLossMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(helpLossMoney);
    }

    public void setHelpLossMoney(String helpLossMoney) {
        this.helpLossMoney = MoneyCheckUtil.checkMoneyIsEmpty(helpLossMoney);
    }

    public String getLossMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(lossMoney);
    }

    public void setLossMoney(String lossMoney) {
        this.lossMoney = MoneyCheckUtil.checkMoneyIsEmpty(lossMoney);
    }

    public String getLawyerMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(lawyerMoney);
    }

    public void setLawyerMoney(String lawyerMoney) {
        this.lawyerMoney = MoneyCheckUtil.checkMoneyIsEmpty(lawyerMoney);
    }

    public String getLawsuitMoney() {
        return MoneyCheckUtil.checkMoneyIsEmpty(lawsuitMoney);
    }

    public void setLawsuitMoney(String lawsuitMoney) {
        this.lawsuitMoney = MoneyCheckUtil.checkMoneyIsEmpty(lawsuitMoney);
    }
}
