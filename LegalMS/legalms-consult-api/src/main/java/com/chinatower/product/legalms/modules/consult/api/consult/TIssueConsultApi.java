package com.chinatower.product.legalms.modules.consult.api.consult;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.consult.entity.consult.TIssueConsult;
import com.chinatower.product.legalms.modules.consult.vo.issue.ConsultListParam;
import com.chinatower.product.legalms.modules.consult.vo.querylist.TIssueConsultListVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;


public interface TIssueConsultApi {
    /**
     * 起草
     * @param vo 流程对象
     * @return
     */
    @PostMapping("/tIssueConsult/addTIssueConsult")
    public ProcessResult addTIssueConsult(AddFlowVO<TIssueConsult> vo);

    /**
     * 法律支撑列表综合查询
     * @param param 查询参数
     * @return
     */
    @PostMapping("/tIssueConsult/findIssueConsultList")
    ProcessResult findIssueConsultList(TIssueConsultListVO param);

    /**
     * 暂存
     * @param vo 流程对象
     * @return
     */
    @PostMapping("/tIssueConsult/tempTIssueConsult")
    ProcessResult tempTIssueConsult(AddFlowVO<TIssueConsult> vo);

    /**
     * 查询详情
     * @param id 业务id
     * @param actId 活动定义id（允许null）
     * @return
     */
    @GetMapping("/tIssueConsult/findOne")
    ProcessResult findOne(String id, String actId);

    /**
     * 列表
     * @param consultListParam 查询参数
     * @return
     */
    @PostMapping("/tIssueConsult/selectAll")
    ProcessResult selectAll(ConsultListParam consultListParam);

    /**
     * 批量删除
     * @param ids id集合
     * @return
     */
    @PostMapping("/tIssueConsult/deleteTIssueConsult")
    ProcessResult deleteTIssueConsult(List<String> ids);

    /**
     * 作废
     * @param businessMap 业务参数列表
     * @return
     */
    @PostMapping("/tIssueConsult/nullifyFlow")
    public ProcessResult nullifyFlow(Map<String, Object> businessMap);

    /**
     * 流程中暂存
     * @param addFlowLogVO 流程对象
     * @return
     */
    @PostMapping("/tIssueConsult/tempSelective")
    ProcessResult tempSelective(AddFlowLogVO addFlowLogVO);

    /**
     * 流程驱动下一步
     * @param vo 流程对象
     * @return
     */
    @PostMapping("/tIssueConsult/addTFlowLog")
    ProcessResult addTFlowLog(AddFlowLogVO vo);

    /**
     * 撤回
     * @param activityInstId 活动实例id
     * @return
     */
    @PostMapping("/tIssueConsult/flowDrawBack")
    ProcessResult flowDrawBack(Long activityInstId);
    
    @GetMapping("/tIssueConsult/test")
    String test();

}
