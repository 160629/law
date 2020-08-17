package com.chinatower.product.legalms.modules.dispute.api.issue;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.chinatower.product.legalms.common.*;
import com.chinatower.product.legalms.modules.flow.entity.common.CaseMainVO;
import com.chinatower.product.legalms.modules.flow.service.common.CaseMainService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.DisputeConstClass.FAILURE;

import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueJointly;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.dispute.service.issue.TIssueGuideService;
import com.chinatower.product.legalms.modules.dispute.service.issue.TIssueJointlyService;
import com.chinatower.product.legalms.modules.dispute.vo.jointly.JointlyParam;
import com.chinatower.product.legalms.modules.dispute.vo.jointly.JointlyVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueJointlyListVO;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.service.common.OrgBeanService;
import com.chinatower.product.legalms.modules.flow.service.common.OrgDepLeaderService;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.eos.workflow.data.WFWorkItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 刘晓亮
 * @create 2019-11-27
 */
@RestController
@Api(tags = {"案件协办单接口"})
public class TIssueJointlyApiImpl extends BaseController implements TIssueJointlyApi {

    private static final Logger logger = LoggerFactory.getLogger("TransLog");
    @Autowired
    private TIssueJointlyService tIssueJointlyService;

    @Autowired
    TFlowService tFlowService;

    @Autowired
    TIssueGuideService tIssueGuideService;
    @Autowired
    OrgBeanService orgBeanService;

    @Autowired
    OrgDepLeaderService orgDepLeaderService;
	@Autowired
	FlowUtil flowUtil;
	@Autowired
    CaseMainService caseMainService;
    @Override
    @ApiOperation("提交新增案件协办单")
    @SmsAround
    public ProcessResult addTIssueJointly(@RequestBody AddFlowVO<TIssueJointly> vo) {
        UserInfo info = RequestHolder.getUserInfo();
        TIssueJointly tIssueJointly = vo.getModel();// 从参数列表中获取案件协办对象
        if(null==info|| null==tIssueJointly) {
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.REQUEST_ERR_PARAM);
        }
        if (StringUtils.isBlank(tIssueJointly.getJointlyCode())) {
            String jointlyCode = tIssueJointlyService.selectCode(info.getUnitCode(), info.getOrgCode());
            tIssueJointly.setJointlyCode(jointlyCode);
            vo.setAddFlag(1);
        }
        StringUtil.copyProperties(tIssueJointly,info);
        flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, false);
        //  查询案件信息
        CaseMainVO caseMainVO = caseMainService.selectCaseOne(tIssueJointly.getJointlyCase());
        String caseStatus = caseMainVO.getCaseStatus();
        if(!"2".equals(caseStatus)){
            return new ProcessResult(ProcessResult.ERROR, "关联的案件不是在办状态的，请重新选择案件!",DisputeConstClass.REQUEST_ERR_CASEID);
        }else{
            tIssueJointlyService.addTIssueJointly(vo, flowUtil, info,tIssueJointly);
    }
        List<OrgParticipantVO> orgs = vo.getOrgs();
        Long flowId = flowUtil.getFlowId();
        try {
            TFlowLog log = new TFlowLog();
            flowUtil.init(vo.getFlowName(),info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, true);
            flowUtil.setFlowId(flowId);
            tIssueJointlyService.addFlow(vo, flowUtil, info,log);
            Long activityInstID = flowUtil.getActivityInstID();
            flowUtil.clientCommit(true);
            tFlowService.setIsUnView(vo.getModel().getSignDept(), info, flowId);
            flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, false);
            List<WFWorkItem> wfWorkItems = flowUtil.queryNextWorkItemsByActivityInstID(activityInstID, false);
            for (int j = 0; wfWorkItems != null && j < wfWorkItems.size(); j++) {
                TFlowDelegate delegate = tFlowService.verifyDelegate(wfWorkItems.get(j).getParticipant());
                if (null != delegate) {
                	flowUtil.init(wfWorkItems.get(j).getParticipant(), wfWorkItems.get(j).getPartiName(), DisputeConstClass.TENANLID, null, true);
                    flowUtil.delegateWorkItem(delegate.getToerId(), delegate.getToerName(),wfWorkItems.get(j).getWorkItemID());
                    flowUtil.clientCommit(true);
                }
            }
            String signDept = vo.getModel().getSignDept();
            if ("1".equals(signDept)) {
                tIssueGuideService.addOrgsUndone(orgs);
            } else {
                tIssueGuideService.addUndone(orgs.get(0).getDepaInterPersonP());
            }
            return new ProcessResult(ProcessResult.SUCCESS, "");
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            tFlowService.flowBack(tIssueJointlyService.getClass(), vo.getModel().getJointlyId(), flowUtil);
            return new ProcessResult(ProcessResult.ERROR,FAILURE.FAILURE_MESS);
        }

    }


    @Override
    @ApiOperation("查询案件协办单列表")
    public ProcessResult findTIssueJointly(@RequestBody JointlyParam param) {
        return tIssueJointlyService.selectAll(param);
    }


    @Override
    @ApiOperation("暂存案件协办单")
    public ProcessResult tempTIssueJointly(@RequestBody AddFlowVO<TIssueJointly> vo) {
        try {
            UserInfo info = RequestHolder.getUserInfo();
            TIssueJointly tIssueJointly = vo.getModel();
            if(null==info|| null==tIssueJointly) {
                return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.REQUEST_ERR_PARAM);
            }
            if (StringUtils.isBlank(tIssueJointly.getJointlyCode())) {
                String jointlyCode = tIssueJointlyService.selectCode(info.getUnitCode(),info.getOrgCode());
                tIssueJointly.setJointlyCode(jointlyCode);
                vo.setAddFlag(1);
            }
            StringUtil.copyProperties(tIssueJointly,info);
            int i = tIssueJointlyService.tempTIssueJointly(vo, info,tIssueJointly);
            return new ProcessResult(ProcessResult.SUCCESS, "", i);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
        }
    }

    @Override
    @ApiOperation("案件详情页面")
    public ProcessResult findOneTIssueJointly(String jointlyId, String actId) {
        JointlyVO jointlyVO = null;
        try {
            jointlyVO = tIssueJointlyService.findOneTIssueJointly(jointlyId, actId);
            if (jointlyVO == null) {
                return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
            }
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
        }
        return new ProcessResult(ProcessResult.SUCCESS, "", jointlyVO);
    }

    @Override
    @ApiOperation("删除")
    public ProcessResult deleteTIssueJointly(@RequestBody List<String> jointlyIds) {
        int i = tIssueJointlyService.deleteTIssueJointly(jointlyIds);
        return new ProcessResult(ProcessResult.SUCCESS, "", i);
    }

    @Override
    public ProcessResult findTIssueJointlyList(@RequestBody TIssueJointlyListVO param) {
        logger.info("案件协办综合查询，条件：" , param.toString());
        return tIssueJointlyService.findTIssueJointlyList(param);
    }

    @Override
    @SmsAround
    public ProcessResult addTFlowLog(HttpServletRequest request) {
        AddFlowLogVO vo=(AddFlowLogVO) request.getAttribute("vo");
        logger.info("案件协办审批接口参数:vo={}" , vo);
        try {
            return tIssueJointlyService.addTFlowLog(vo);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }

}
