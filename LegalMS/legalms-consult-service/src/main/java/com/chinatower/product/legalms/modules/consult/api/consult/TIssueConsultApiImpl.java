package com.chinatower.product.legalms.modules.consult.api.consult;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.CoreConstClass;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.SmsAround;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.consult.entity.consult.TIssueConsult;
import com.chinatower.product.legalms.modules.consult.service.consult.TIssueConsultService;
import com.chinatower.product.legalms.modules.consult.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.consult.vo.issue.ConsultListParam;
import com.chinatower.product.legalms.modules.consult.vo.issue.TIssueConsultVO;
import com.chinatower.product.legalms.modules.consult.vo.querylist.TIssueConsultListVO;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.mapper.common.BusinessLogConfigMapper;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.eos.workflow.data.WFWorkItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"法律支撑接口"})
@RestController
public class TIssueConsultApiImpl extends BaseController implements TIssueConsultApi{
    private static final Logger logger = LoggerFactory.getLogger(TIssueConsultApiImpl.class);

    @Autowired
    TIssueConsultService tIssueConsultService;
    @Autowired
    FlowUtil flowUtil;
    @Autowired
    TFlowService tFlowService;
	@Autowired
	TFlowCommonService commonService;
    @Autowired
    BusinessLogConfigMapper businessLogConfigMapper;

    @Override
    @SmsAround
    public ProcessResult addTIssueConsult(@RequestBody AddFlowVO<TIssueConsult> vo) {
        logger.info("新增法律支撑参数：" + vo.toString());
        UserInfo info = RequestHolder.getUserInfo();
        TIssueConsult model = vo.getModel();
        if(null==info|| null==model) {
            return new ProcessResult(ProcessResult.ERROR, CoreConstClass.REQUEST_ERR_PARAM);
        }
        if (StringUtils.isBlank(model.getCode())) {
            String code = tIssueConsultService.selectCode(info.getUnitCode(),CoreConstClass.ZX, info.getOrgCode());
            model.setCode(code);
            vo.setAddFlag(1);
        }
        StringUtil.copyProperties(model,info);
        flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, false);
        model.setModuleName(vo.getModuleName());
        int i = tIssueConsultService.addTIssueConsult(vo, flowUtil, info);
        List<OrgParticipantVO> orgs = vo.getOrgs();
        Long flowId = flowUtil.getFlowId();
        try {
            TFlowLog log = new TFlowLog();
            flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, true);
            flowUtil.setFlowId(flowId);
            i += tIssueConsultService.addFlow(vo,  flowUtil, info,log);
            Long activityInstID = flowUtil.getActivityInstID();
            flowUtil.clientCommit(true);
            tFlowService.setIsUnView(vo.getModel().getSignDept(), info, flowId);
            flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, false);
            List<WFWorkItem> wfWorkItems = flowUtil.queryNextWorkItemsByActivityInstID(activityInstID, false);
            for (int j = 0; wfWorkItems != null && j < wfWorkItems.size(); j++) {
                TFlowDelegate delegate = commonService.verifyDelegate(wfWorkItems.get(j).getParticipant());
                if (null != delegate) {
                    flowUtil.init(wfWorkItems.get(j).getParticipant(), wfWorkItems.get(j).getPartiName(), CoreConstClass.TENANLID, null, true);
                    flowUtil.delegateWorkItem(delegate.getToerId(), delegate.getToerName(),wfWorkItems.get(j).getWorkItemID());
                    flowUtil.clientCommit(true);
                }
            }
            String signDept = vo.getModel().getSignDept();
            if ("1".equals(signDept)) {
                tIssueConsultService.addOrgsUndone(orgs);
            } else {
                tIssueConsultService.addUndone(orgs.get(0).getDepaInterPersonP());
            }

            return new ProcessResult(ProcessResult.SUCCESS, "", i);
        } catch (Exception e) {
            logger.info("业务处理异常,流程业务回滚" , e);
            tFlowService.flowBack(tIssueConsultService.getClass(), vo.getModel().getId(), flowUtil);
            return new ProcessResult(ProcessResult.ERROR,CoreConstClass.FAILURE_MESS);
        }
    }

    @Override
    @ApiOperation("法律支撑综合查询接口")
    public ProcessResult findIssueConsultList(@RequestBody TIssueConsultListVO param) {
        return tIssueConsultService.findIssueConsultList(param);
    }

    @Override
    public ProcessResult tempTIssueConsult(@RequestBody AddFlowVO<TIssueConsult> vo) {
        logger.info("暂存法律支撑参数:vo={}" , vo);
        try {
            UserInfo info = RequestHolder.getUserInfo();
            TIssueConsult model = vo.getModel();
            if(null==info|| null==model) {
                return new ProcessResult(ProcessResult.ERROR, CoreConstClass.REQUEST_ERR_PARAM);
            }
            if (StringUtils.isBlank(model.getCode())) {
                String guideCode = tIssueConsultService.selectCode(info.getUnitCode(),CoreConstClass.ZX, info.getOrgCode());
                model.setCode(guideCode);
                vo.setAddFlag(1);
            }
            StringUtil.copyProperties(model,info);
            int i = tIssueConsultService.tempTIssueConsult(vo, info);
            return new ProcessResult(ProcessResult.SUCCESS, "", i);
        } catch (Exception e) {
            logger.info("业务处理异常" , e);
            return new ProcessResult(ProcessResult.ERROR, CoreConstClass.FAILURE_MESS);
        }
    }

    @Override
    public ProcessResult findOne(String id, String actId) {
        logger.info("引诉纠纷详情参数:guideId={}", id);
        TIssueConsultVO findOne = null;
        try {
            findOne = tIssueConsultService.findOne(id, actId);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, "", e);
        }
        return new ProcessResult(ProcessResult.SUCCESS, "", findOne);
    }

    @Override
    public ProcessResult selectAll(@RequestBody ConsultListParam consultListParam) {
        logger.info("查询引诉纠纷参数:param={}", consultListParam);
        try {
            String loginAcct =  RequestHolder.getUserInfo().getLoginAcct();
            consultListParam.setLoginAcct(loginAcct);
            PageHelper.startPage(consultListParam.getPageNum(), consultListParam.getPageSize());
            List<TIssueConsult> list = tIssueConsultService.selectAll(consultListParam);
            PageInfo<TIssueConsult> pageInfo = new PageInfo<>(list);
            return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, CoreConstClass.FAILURE_MESS);
        }
    }

    @Override
    public ProcessResult deleteTIssueConsult(@RequestBody List<String> ids) {
        logger.info("删除法律支撑参数:ids={}",ids);
        int i = tIssueConsultService.deleteTIssueGuide(ids);
        return new ProcessResult(ProcessResult.SUCCESS, "", i);
    }

    @Override
    public ProcessResult nullifyFlow(@RequestBody Map<String, Object> businessMap) {
        return tIssueConsultService.nullifyFlow(businessMap);
    }

    @Override
    public ProcessResult tempSelective(@RequestBody AddFlowLogVO addFlowLogVO) {
        return tIssueConsultService.tempSelective(addFlowLogVO);
    }

    @Override
    @SmsAround
    public ProcessResult addTFlowLog(@RequestBody AddFlowLogVO vo) {
        logger.info("================================================================");
        logger.info("法律支撑审批参数:vo={}" , vo.toString());
        try {
            return tIssueConsultService.addTFlowLog(vo);
        } catch (Exception e) {
            logger.info("法律支撑审批处理异常" , e);
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }

    @Override
    public ProcessResult flowDrawBack(@RequestParam Long activityInstId) {
        return tIssueConsultService.flowDrawBack(activityInstId);
    }

	@Override
	public String test() {
		return commonService.getLawKafka();
		
	}

}
