package com.chinatower.product.legalms.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.BusinessFields;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.eos.workflow.data.WFActivityInst;

/**
 * @author 刘晓亮
 * @create 2019-11-05 16:36
 *
 * 业务冗余字段工具类
 */
public class BusinessFiledUtil {
    private BusinessFiledUtil(){

    }

    public static void setReceiverInfo(TFlowLog tFlowLog, List<OrgParticipantVO> orgs, UserInfo info) {
        OrgParticipantVO tempOrgParticipantVO = null;
        StringBuilder receiverCompanyIds = new StringBuilder();
        StringBuilder receiverCompanyNames = new StringBuilder();
        StringBuilder receiverOrgIds = new StringBuilder();
        StringBuilder receiverOrgNames = new StringBuilder();
        StringBuilder receiverIds = new StringBuilder();
        StringBuilder receiverNames = new StringBuilder();
        for (int i = 0; i < orgs.size(); i++) {
            tempOrgParticipantVO = orgs.get(i);
            receiverCompanyIds.append(tempOrgParticipantVO.getReceiverCompanyId() + " ");
            receiverCompanyNames.append(tempOrgParticipantVO.getReceiverCompanyName() + " ");
            receiverOrgIds.append(tempOrgParticipantVO.getReceiverOrgId() + " ");
            receiverOrgNames.append(tempOrgParticipantVO.getReceiverOrgName() + " ");
            receiverIds.append(tempOrgParticipantVO.getReceiverId() + " ");
            receiverNames.append(tempOrgParticipantVO.getReceiverName() + " ");
        }
        tFlowLog.setReceiverCompanyId(StringUtils.strip(receiverCompanyIds.toString()));
        tFlowLog.setReceiverCompanyName(StringUtils.strip(receiverCompanyNames.toString()));
        tFlowLog.setReceiverOrgId(StringUtils.strip(receiverOrgIds.toString()));
        tFlowLog.setReceiverOrgName(StringUtils.strip(receiverOrgNames.toString()));
        tFlowLog.setReceiverId(StringUtils.strip(receiverIds.toString()));
        tFlowLog.setReceiverName(StringUtils.strip(receiverNames.toString()));
        tFlowLog.setUserId(info.getLoginAcct());
        tFlowLog.setUserName(info.getLoginName());
        tFlowLog.setOrgId(info.getOrgId());
        tFlowLog.setOrgName(info.getOrgName());
        tFlowLog.setDeptId(info.getDeptId());
        tFlowLog.setDeptName(info.getDeptName());
    }


    public static Map<String, Object> setBusinessFields(BusinessFields businessFields) {
    	Map<String, Object> bizInfo = new HashMap<>();
    	bizInfo.put(CoreConstClass.BUSINESS_TABLE_TITLE, businessFields.getBusinessTitle());
        bizInfo.put(CoreConstClass.BUSINESS_TABLE_CODE, businessFields.getBusinessCode());
        bizInfo.put(CoreConstClass.BUSINESS_TABLE_TYPE, businessFields.getBusinessTableName()
                + CoreConstClass.BUSINESS_FILED_SEPARATOR
                + businessFields.getBusinessId()
                + CoreConstClass.BUSINESS_FILED_SEPARATOR
                + businessFields.getBusinessIdName()
                + CoreConstClass.BUSINESS_FILED_SEPARATOR
                + businessFields.getBusinessType());
        bizInfo.put(CoreConstClass.BUSINESS_TABLE_CURWORK, businessFields.getCurState());
        bizInfo.put(CoreConstClass.BUSINESS_TABLE_ACCOUNT, businessFields.getAccountId());
        bizInfo.put(CoreConstClass.BUSINESS_TABLE_PERSON, businessFields.getModuleName());
        return bizInfo;
    }
    
    public static void setRelativeData(TFlowLog record, String signDept, List<OrgParticipantVO> orgs, FlowUtil flowUtil, WFActivityInst lastActivity) {
    	Map<String, Object> map = new HashMap<>();// 条件相关数据
    	if ("1".equals(signDept)) {// 1表示会签
            List<Map<String, Object>> list = AddFlowVO.getObjectToMap(orgs);// 转换参与者对象
            map.put("orgs", list);// 设置相关数据-会签-参与者
            map.put("parentActInstId", lastActivity.getActivityInstID());
        } else if (!record.getNextActivityDefId().equals("finishActivity")) {
            // 设置相关数据-下一步-参与者
            map.put(record.getNextActivityDefId() + "P", orgs.get(0).getDepaInterPersonP());
        }
        if (record.getFlowPid() != 0) {
            long parentActInstId = (Long) flowUtil.getRelativeData(record.getFlowPid(), "parentActInstId");
            record.setActPid(parentActInstId);
        }
        map.put("next", record.getActivityDefId() + record.getNextActivityDefId());// 设置相关数据-流程路径判断条件
        map.put("unView", 0);
        flowUtil.setRelativeDataBatch(record.getFlowId(), map);// 更新相关数据
        long pFlowId = (record.getFlowPid() == null || record.getFlowPid() == 0) ? record.getFlowId() : record.getFlowPid();
        if (!(record.getFlowPid() == null || record.getFlowPid() == 0) && (orgs != null && !orgs.isEmpty())) {
            flowUtil.setRelativeData(pFlowId, "next", record.getActivityDefId() + record.getNextActivityDefId());// 更新相关数据
            flowUtil.setRelativeData(pFlowId, record.getNextActivityDefId() + "P", orgs.get(0).getDepaInterPersonP());// 更新相关数据
        }
    }
    
    public static String getBusinessType(String approveItemType) {

		switch (approveItemType) {
		case "t_issue_guide":
			return "引诉纠纷";
        case "t_issue_jointly":
            return "案件协办";
        case "t_issue_lawsuit":
            return "纠纷处理";
        case "t_case_assign":
            return "案件交办";
        case "t_legalms_letter":
            return "法律文书";
		default:
			return null;
		}
	}
}
