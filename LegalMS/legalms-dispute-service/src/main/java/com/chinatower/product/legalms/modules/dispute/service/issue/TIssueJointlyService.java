package com.chinatower.product.legalms.modules.dispute.service.issue;

import java.util.List;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueJointly;
import com.chinatower.product.legalms.modules.dispute.vo.jointly.JointlyParam;
import com.chinatower.product.legalms.modules.dispute.vo.jointly.JointlyVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueJointlyListVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;

public interface TIssueJointlyService {

    int addTIssueJointly(AddFlowVO<TIssueJointly> vo, FlowUtil flowUtil, UserInfo info,TIssueJointly tIssueJointly);

    int tempTIssueJointly(AddFlowVO<TIssueJointly> vo,UserInfo info,TIssueJointly tIssueJointly);

    JointlyVO findOneTIssueJointly(String jointlyId, String actId);

    int updateByPrimaryKey(TIssueJointly jointly);

    int deleteTIssueJointly(List<String> jointlyIds);

    int addFlow(AddFlowVO<TIssueJointly> vo, FlowUtil flowUtil, UserInfo info, TFlowLog log);

    ProcessResult findTIssueJointlyList(TIssueJointlyListVO param);

    String selectCode(String unitCode, String orgCode);

    int updateByPrimaryKeySelective(TIssueJointly jointly);

    ProcessResult addTFlowLog(AddFlowLogVO vo);

    ProcessResult selectAll(JointlyParam param);
}
