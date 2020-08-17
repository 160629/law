package com.chinatower.product.legalms.modules.dispute.api.legislation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinatower.product.legalms.common.*;
import com.chinatower.product.legalms.modules.flow.entity.common.BusinessLogVO;
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

import com.chinatower.product.legalms.modules.dispute.entity.LegislationVO;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.dispute.service.issue.TIssueGuideService;
import com.chinatower.product.legalms.modules.dispute.service.legislation.LegislationService;
import com.chinatower.product.legalms.modules.dispute.vo.legislation.LegislationParam;
import com.chinatower.product.legalms.modules.dispute.vo.legislation.PageParam;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TLegislationListVO;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.service.common.OrgBeanService;
import com.chinatower.product.legalms.modules.flow.service.common.OrgDepLeaderService;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.chinatower.product.legalms.utils.FileUtil;
import com.eos.workflow.data.WFWorkItem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = { "法律文书转办接口" })
public class LegislationAPIImpl extends BaseController implements LegislationApi {
    private static final Logger logger = LoggerFactory.getLogger(LegislationAPIImpl.class);

    @Autowired
    LegislationService legislationService;

    @Autowired
    TIssueGuideService tIssueGuideService;

    @Autowired
    TFlowService tFlowService;
    @Autowired
    OrgBeanService orgBeanService;
	@Autowired
	FlowUtil flowUtil;
    @Autowired
    OrgDepLeaderService orgDepLeaderService;
    @Autowired
    CaseMainService caseMainService;
    @ApiOperation("法律文书转办单起草，提交表单数据")
    @SmsAround
    public ProcessResult addLegislationInfo(@RequestBody AddFlowVO<LegislationVO> vo) {
        logger.info("起草法律文书转办单:vo={}" , vo);
        //获取当前登录的用户信息
        UserInfo info = RequestHolder.getUserInfo();
        LegislationVO legislationVO = vo.getModel();// 从参数列表中获取案件协办对象
        if(null==info|| null==legislationVO) {
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.REQUEST_ERR_PARAM);
        }
        if (StringUtils.isBlank(legislationVO.getOdd())) {
            String odd = legislationService.selectCode(info.getUnitCode(), info.getOrgCode());
            legislationVO.setOdd(odd);
            vo.setAddFlag(1);
        }
        StringUtil.copyProperties(legislationVO,info);
        //获取当前用户登陆的账号，登录名，租户的id，
        flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, false);

        //  查询案件信息
        CaseMainVO caseMainVO = caseMainService.selectCaseOne(legislationVO.getCaseFile());
        String caseStatus = caseMainVO.getCaseStatus();
        if(!"2".equals(caseStatus)) {
            return new ProcessResult(ProcessResult.ERROR, "关联的案件不是在办状态的，请重新选择案件!", DisputeConstClass.REQUEST_ERR_CASEID);
        }else {
            legislationService.addLegislationInfo(vo, flowUtil, info, legislationVO);
        }
        List<OrgParticipantVO> orgs = vo.getOrgs();
        Long flowId = flowUtil.getFlowId();
        try {
            TFlowLog log = new TFlowLog();
            flowUtil.init(vo.getFlowName(),info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, false);
            flowUtil.setFlowId(flowId);
            legislationService.addFlow(vo, flowUtil,info,log);
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
            }else {
                tIssueGuideService.addUndone(orgs.get(0).getDepaInterPersonP());
            }
            return new ProcessResult(ProcessResult.SUCCESS, "");
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            tFlowService.flowBack(legislationService.getClass(), vo.getModel().getId(), flowUtil);
            return new ProcessResult(ProcessResult.ERROR,FAILURE.FAILURE_MESS);
        }

    }

    @ApiOperation("法律文书转办单列表查询")
    public ProcessResult findLegislation(@RequestBody PageParam param) {
           return legislationService.selectAll(param);
    }
    @Override
    @ApiOperation("暂存法律文书")
    public ProcessResult tempLegislation(@RequestBody AddFlowVO<LegislationVO> vo) {
        try {
            UserInfo info = RequestHolder.getUserInfo();
            LegislationVO legislationVO = vo.getModel();
            if (null == info || null == legislationVO) {
                return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.REQUEST_ERR_PARAM);
            }
            if (StringUtils.isBlank(legislationVO.getOdd())) {
                String odd = legislationService.selectCode(info.getUnitCode(), info.getOrgCode());
                legislationVO.setOdd(odd);
                vo.setAddFlag(1);
            }
            StringUtil.copyProperties(legislationVO, info);
            int i = legislationService.tempLegislation(vo,info,legislationVO);
            return new ProcessResult(ProcessResult.SUCCESS, "", i);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,FAILURE.FAILURE_MESS);
        }
    }
    @Override
    @ApiOperation("法律文书转办详情")
    public ProcessResult findOneLegislation(String id, String actId) {
        BusinessLogVO businessLogVO = new BusinessLogVO();
        businessLogVO.setApproveItemId(id);
        LegislationParam legislationParam=legislationService.findOneLegislation(id, actId,businessLogVO);
        return new ProcessResult(ProcessResult.SUCCESS,"",legislationParam);
    }


    @Override
    @ApiOperation("批量删除")
    public ProcessResult deleteLegislation (@RequestBody List<String> ids){
        int i = legislationService.deleteLegislation(ids);
        if (i>0){
            return new ProcessResult(ProcessResult.SUCCESS, "", ""+i);
        }else{
            return new ProcessResult(ProcessResult.ERROR,FAILURE.FAILURE_MESS+i);
        }
    }

    @Override
    @ApiOperation("法律文书流标综合查询")
    public ProcessResult findLegislationList(@RequestBody TLegislationListVO param) {
        return legislationService.findLegislationList(param);
    }

    @Override
    @SmsAround
    public ProcessResult addTFlowLog(HttpServletRequest request) {
        AddFlowLogVO vo=(AddFlowLogVO) request.getAttribute("vo");
        logger.info("修改法律文书参数:vo={}" , vo);
        try {
            return legislationService.addTFlowLog(vo);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }

    @Override
    public void getExcelDate(HttpServletResponse response) throws IOException {
        UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
        List<Map<String, Object>> list = legislationService.getExcelDate(info.getLoginAcct());
        FileUtil.downloadExcel(list, response);
    }
}
