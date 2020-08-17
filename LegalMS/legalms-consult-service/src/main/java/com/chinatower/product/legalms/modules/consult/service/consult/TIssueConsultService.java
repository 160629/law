package com.chinatower.product.legalms.modules.consult.service.consult;

import java.util.List;
import java.util.Map;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.consult.entity.consult.TIssueConsult;
import com.chinatower.product.legalms.modules.consult.vo.issue.ConsultListParam;
import com.chinatower.product.legalms.modules.consult.vo.issue.TIssueConsultVO;
import com.chinatower.product.legalms.modules.consult.vo.querylist.TIssueConsultListVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.eos.workflow.omservice.WFParticipant;

public interface TIssueConsultService {
    String  selectCode(String unitName,String moduleCode,String orgCode);

    int addTIssueConsult(AddFlowVO<TIssueConsult> vo, FlowUtil flowUtil, UserInfo info);

    void addOrgsUndone(List<OrgParticipantVO> orgs);

    void addUndone(WFParticipant depaInterPersonP);

    int deleteByPrimaryKey(String id);

    int addFlow(AddFlowVO<TIssueConsult> vo, FlowUtil flowUtil, UserInfo info, TFlowLog log);

    ProcessResult findIssueConsultList(TIssueConsultListVO param);

    int tempTIssueConsult(AddFlowVO<TIssueConsult> vo, UserInfo info);

    TIssueConsultVO findOne(String id, String actId);

    List<TIssueConsult> selectAll(ConsultListParam consultListParam);

    int deleteTIssueGuide(List<String> ids);

    ProcessResult nullifyFlow(Map<String, Object> businessMap);

    int updateByPrimaryKeySelective(TIssueConsult tIssueConsult);

    ProcessResult tempSelective(AddFlowLogVO addFlowLogVO);

    ProcessResult addTFlowLog(AddFlowLogVO vo);

    ProcessResult flowDrawBack(Long activityInstId);
}
