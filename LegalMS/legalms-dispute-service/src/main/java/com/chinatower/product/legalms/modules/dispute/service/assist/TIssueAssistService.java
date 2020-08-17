package com.chinatower.product.legalms.modules.dispute.service.assist;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssist;
import com.chinatower.product.legalms.modules.dispute.vo.assist.FindTIssueAssistListListParamVO;
import com.chinatower.product.legalms.modules.dispute.vo.assist.SelectAllListParamVO;
import com.chinatower.product.legalms.modules.dispute.vo.assist.SelectRelationshipListInCreateParamVO;
import com.chinatower.product.legalms.modules.dispute.vo.assist.SelectRelationshipListParamVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.eos.workflow.omservice.WFParticipant;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TIssueAssistService {
    /**
     * 起草暂存
     * @param vo
     * @return
     */
    int tempTIssueAssist(AddFlowVO<TIssueAssist> vo);

    /**
     * 列表分页查询
     * @param param
     * @return
     */
    PageInfo<TIssueAssist> selectAll(SelectAllListParamVO param);

    /**
     * 删除
     * 删除未提交的协助执行单
     * @param ids
     * @return
     */
    int deleteTIssueAssist(List<String> ids);

    /**
     * 更新
     * @param tIssueAssist
     * @return
     */
    int updateByPrimaryKeySelective(TIssueAssist tIssueAssist);

    /**
     * 关联协助执行单列表
     * @param param
     * @return
     */
    PageInfo<TIssueAssist> selectRelationshipList(SelectRelationshipListParamVO param);

    /**
     * 查询编号
     * @param unitCode
     * @param zx
     * @param orgCode
     * @return
     */
    String selectCode(String unitCode, String zx, String orgCode);

    /**
     * 创建流程
     * @param vo
     * @param flowUtil
     * @param info
     * @return
     */
    int addTIssueAssist(AddFlowVO<TIssueAssist> vo, FlowUtil flowUtil, UserInfo info);

    /**
     * 驱动流程起草
     * @param vo
     * @param flowUtil
     * @param info
     * @param log
     * @return
     */
    int addFlow(AddFlowVO<TIssueAssist> vo, FlowUtil flowUtil, UserInfo info, TFlowLog log);

    void addOrgsUndone(List<OrgParticipantVO> orgs);

    void addUndone(WFParticipant depaInterPersonP);

    /**
     * 驱动下一步
     * @param vo
     * @return
     */
    ProcessResult addTFlowLog(AddFlowLogVO vo);

    /**
     * 流程中暂存
     * @param addFlowLogVO
     * @return
     */
    ProcessResult tempSelective(AddFlowLogVO addFlowLogVO);

    /**
     * 更新关联关系
     * @param param
     * @return
     */
    ProcessResult updateRelation(Map<String, String> param);


    /**
     * 协助执行流程查询详情
     * @param id
     * @param actId
     * @return
     */
    ProcessResult findOne(String id, String actId, String activityInstId);

    /***
     * 协助执行流程综合查询
     * @param param
     * @return
     */
    ProcessResult findTIssueAssistList(FindTIssueAssistListListParamVO param);

    ProcessResult deleteByPrimaryKey(String id);

    PageInfo<TIssueAssist> selectRelationshipListInCreate(SelectRelationshipListInCreateParamVO param);

    ProcessResult findBaseInfo(String id);

    ProcessResult findBaseInfoAndBindFiles(String id);
}
