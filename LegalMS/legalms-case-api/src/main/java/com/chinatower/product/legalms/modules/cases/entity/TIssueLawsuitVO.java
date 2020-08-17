package com.chinatower.product.legalms.modules.cases.entity;

import com.chinatower.product.legalms.modules.cases.api.MoneyCheckUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Table(name = "t_issue_lawsuit")
public class TIssueLawsuitVO{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lawsuit_id")
	@ApiModelProperty(name = "lawsuitId", value = "id")
	private String lawsuitId;

	@ApiModelProperty(name = "lawsuitCode", value = "编码")
	private String lawsuitCode;

	@ApiModelProperty(name = "lawsuitTitle", value = "标题")
	private String lawsuitTitle;

	@Column(name = "guide_id")
	@ApiModelProperty(name = "lawsuitCode", value = "关联引诉纠纷")
	private String guideId;

	@ApiModelProperty(name = "caseId", value = "关联案件")
	private String caseId;

	@ApiModelProperty(name = "approveState", value = "审理机构省份")
	private String approveState;

	@ApiModelProperty(name = "approveCity", value = "审理机构地市")
	private String approveCity;

	@ApiModelProperty(name = "approveOrg", value = "审理机构名称")
	private String approveOrg;

	@ApiModelProperty(name = "approveLevel", value = "案件审级")
	private String approveLevel;

	@ApiModelProperty(name = "caseCreateTime", value = "立案时间")
	private Date caseCreateTime;

	@ApiModelProperty(name = "caseType", value = "案件类型")
	private String caseType;

	@ApiModelProperty(name = "indictOrUnindict", value = "起诉/应诉")
	private String indictOrUnindict;

	@ApiModelProperty(name = "caseLine", value = "案件所属专业线")
	private String caseLine;

	@ApiModelProperty(name = "caseCause", value = "案由")
	private String caseCause;

	@ApiModelProperty(name = "ourLawsuitBody", value = "我方诉讼主体")
	private String ourLawsuitBody;

	@ApiModelProperty(name = "ourLawsuitBodyName", value = "我方诉讼主体名称")
	private String ourLawsuitBodyName;

	@ApiModelProperty(name = "theyLawsuitBody", value = "对方诉讼主体")
	private String theyLawsuitBody;

	@ApiModelProperty(name = "ourLawsuitStatus", value = "我方诉讼地位")
	private String ourLawsuitStatus;

	@ApiModelProperty(name = "lawsuitMoney", value = "争议金额")
	private String lawsuitMoney;

	@ApiModelProperty(name = "lawsuitSize", value = "是否是重大诉讼")
	private String lawsuitSize;

	@ApiModelProperty(name = "lawsuitDetail", value = "案件基本事实")
	private String lawsuitDetail;

	@ApiModelProperty(name = "fileUrl", value = "附件")
	private String fileUrl;

	@ApiModelProperty(name = "files", value = "文件列表")
    private List<Map<String, Object>> files;

	@ApiModelProperty(name = "otherRelated", value = "其他涉案方")
	private String otherRelated;

	@ApiModelProperty(name = "thirdPerson", value = "第三人")
    private String thirdPerson;

	@ApiModelProperty(name = "plaintiff", value = "同案原告")
    private String plaintiff;

	@ApiModelProperty(name = "defendant", value = "同案被告")
    private String defendant;

	@ApiModelProperty(name = "signDept", value = "是否会签")
	private String signDept;

	@ApiModelProperty(name = "lawsuitStatus", value = "状态")
	private String lawsuitStatus;

	@ApiModelProperty(name = "currActivityDefId", value = "当前活动定义ID")
	private String currActivityDefId;

	@ApiModelProperty(name = "currActivityDefName", value = "当前活动定义名称")
	private String currActivityDefName;

	public String getLawsuitMoney() {
		return MoneyCheckUtil.checkMoneyIsEmpty(lawsuitMoney);
	}

	public void setLawsuitMoney(String lawsuitMoney) {
		this.lawsuitMoney = MoneyCheckUtil.checkMoneyIsEmpty(lawsuitMoney);
	}
}
