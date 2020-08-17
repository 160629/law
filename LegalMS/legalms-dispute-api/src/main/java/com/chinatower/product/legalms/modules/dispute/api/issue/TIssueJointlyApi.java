package com.chinatower.product.legalms.modules.dispute.api.issue;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueJointly;
import com.chinatower.product.legalms.modules.dispute.vo.jointly.JointlyParam;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueJointlyListVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;

public interface TIssueJointlyApi {

    /*案件协办单起草*/
    @PostMapping("/tIssueJointly/addTIssueJointly")
    ProcessResult addTIssueJointly(AddFlowVO<TIssueJointly> vo);

    /*案件协办单暂存*/
    @PostMapping("/tIssueJointly/tempTIssueJointly")
    ProcessResult tempTIssueJointly(@RequestBody AddFlowVO<TIssueJointly> vo);

    /*案件协办单列表查询*/
    @PostMapping("/tIssueJointly/findTIssueJointly")
    ProcessResult findTIssueJointly(@RequestBody JointlyParam param);

    /*案件协办单详情*/
    @GetMapping("/tIssueJointly/findOneTIssueJointly")
    ProcessResult findOneTIssueJointly(String jointlyId, String actId);
    @PostMapping("/tIssueJointly/deleteTIssueJointly")
    ProcessResult deleteTIssueJointly(List<String> jointlyIds);

    /*案件协办综合查询*/
    @PostMapping("/tIssueJointly/findTIssueJointlyList")
    ProcessResult findTIssueJointlyList(TIssueJointlyListVO param);

    //案件协办审批接口
    @PostMapping("/tIssueJointly/addTFlowLog")
    ProcessResult addTFlowLog(HttpServletRequest request);
}
