package com.chinatower.product.legalms.modules.dispute.api.assist;


import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.modules.dispute.vo.assist.SelectRelationshipListInCreateParamVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssist;
import com.chinatower.product.legalms.modules.dispute.vo.assist.FindTIssueAssistListListParamVO;
import com.chinatower.product.legalms.modules.dispute.vo.assist.SelectAllListParamVO;
import com.chinatower.product.legalms.modules.dispute.vo.assist.SelectRelationshipListParamVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;

import javax.servlet.http.HttpServletRequest;

public interface TIssueAssistApi {

    /**
     * 起草
     * @param vo 流程起草对象
     * @return
     */
    @PostMapping("/tIssueAssist/addTIssueAssist")
    public ProcessResult addTIssueAssist(AddFlowVO<TIssueAssist> vo);

    /**
     * 流程驱动下一步
     * @param request 流程审批对象
     * @return
     */
    @PostMapping("/tIssueAssist/addTFlowLog")
    ProcessResult addTFlowLog(HttpServletRequest request);

    /**
     * 起草暂存
     * @param vo 流程起草对象
     * @return
     */
    @PostMapping("/tIssueAssist/tempTIssueAssist")
    ProcessResult tempTIssueAssist(AddFlowVO<TIssueAssist> vo);

    /**
     * 流程中暂存
     * @param addFlowLogVO 流程审批对象
     * @return
     */
    @PostMapping("/tIssueAssist/tempSelective")
    ProcessResult tempSelective(AddFlowLogVO addFlowLogVO);

    /**
     * 查询详情
     * @param id 业务id
     * @param actId 活动定义id（允许null）
     * @return
     */
    @GetMapping("/tIssueAssist/findOne")
    ProcessResult findOne(String id, String actId, String activityInstId);

    /**
     * 查询业务主表信息
     * @param id
     * @return
     */
    @GetMapping("/tIssueAssist/findBaseInfo")
    ProcessResult findBaseInfo(String id);

    /**
     * 查询业务主表信息，并做文件关联
     * @param id
     * @return
     */
    @GetMapping("/tIssueAssist/findBaseInfoAndBindFiles")
    ProcessResult findBaseInfoAndBindFiles(String id);

    /**
     * 列表
     * @return
     */
    @PostMapping("/tIssueAssist/selectAll")
    ProcessResult selectAll(SelectAllListParamVO param);

    /**
     * 协助执行列表综合查询
     * @param param 查询参数
     * @return
     */
    @PostMapping("/tIssueAssist/findTIssueAssistList")
    ProcessResult findTIssueAssistList(FindTIssueAssistListListParamVO param);

    /**
     * 协助执行单列表查询
     * 审批中关联
     * @return
     */
    @PostMapping("/tIssueAssist/selectRelationshipList")
    public ProcessResult selectRelationshipList(SelectRelationshipListParamVO param);

    /**
     * 协助执行单列表查询
     * 起草时关联
     * @return
     */
    @PostMapping("/tIssueAssist/selectRelationshipListInCreate")
    public ProcessResult selectRelationshipListInCreate(SelectRelationshipListInCreateParamVO param);

    /**
     * 批量删除
     * @param ids id集合
     * @return
     */
    @PostMapping("/tIssueAssist/deleteTIssueAssist")
    ProcessResult deleteTIssueAssist(List<String> ids);

    /**
     * 非本单位，新增或删除关联协助执行单
     * @param param
     * @return
     */
    @PostMapping("/tIssueAssist/updateRelation")
    ProcessResult updateRelation(Map<String, String> param);
}
