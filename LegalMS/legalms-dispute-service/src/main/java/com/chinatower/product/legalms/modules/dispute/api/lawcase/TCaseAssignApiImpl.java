package com.chinatower.product.legalms.modules.dispute.api.lawcase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.common.DisputeConstClass.FAILURE;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.SmsAround;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.lawcase.TCaseAssign;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.dispute.service.issue.TIssueGuideService;
import com.chinatower.product.legalms.modules.dispute.service.lawcase.TCaseAssignService;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.issue.TCaseAssignVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TCaseAssignListVO;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.service.common.OrgBeanService;
import com.chinatower.product.legalms.modules.flow.service.common.SysBodyconfigService;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.eos.workflow.data.WFWorkItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = { "案件交办接口" })
public class TCaseAssignApiImpl extends BaseController implements TCaseAssignApi {
	private static final String CLASS_CATCH = "TCaseAssignApiImpl类捕捉异常";
	private static final Logger logger = LoggerFactory.getLogger(TCaseAssignApiImpl.class);
	@Autowired
	TCaseAssignService service;
	@Autowired
	TIssueGuideService tIssueGuideService;
	@Autowired
	SysBodyconfigService configService;
	@Autowired
	TFlowService tFlowService;
	@Autowired
	OrgBeanService orgBeanService;
	@Autowired
	FlowUtil flowUtil;
	
	//案件交办审批接口
	@Override
	@SmsAround
	public ProcessResult addTFlowLog(HttpServletRequest request) {
		AddFlowLogVO vo=(AddFlowLogVO) request.getAttribute("vo");
		logger.info("修改案件交办参数:vo={}" , vo);
		try {
			return service.addTFlowLog(vo);
		} catch (Exception e) {
			logger.info("addTFlowLog业务处理异常" , e);
			return new ProcessResult(ProcessResult.ERROR, e.getMessage());
		}
	}
	@Override
	@ApiOperation("修改案件交办")
	public ProcessResult updateTCaseAssign(@RequestBody TCaseAssign assign) {
		logger.info("修改案件交办参数:assign={}",assign);
		try {
			int i = service.updateByPrimaryKey(assign);
			String body = assign.getOurLawsuitBody();
			if(i>0 && !StringUtil.isEmpty(body)) 
				configService.addSysBody(body, assign.getAssignId(), DisputeConstClass.T_CASE_ASSIGN);
			return new ProcessResult(ProcessResult.SUCCESS, "", i);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			return new ProcessResult(ProcessResult.ERROR, e.getMessage());
		}
	}

	@Override
	@ApiOperation("暂存案件交办")
	public ProcessResult tempTCaseAssign(@RequestBody AddFlowVO<TCaseAssign> vo) {
		logger.info("暂存案件交办参数:vo={}",vo);
		try {
			UserInfo info = RequestHolder.getUserInfo();
			TCaseAssign model = vo.getModel();
			if(null==info|| null==model) {
				return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.REQUEST_ERR_PARAM);
			}
			if (StringUtils.isBlank(model.getAssignCode())) {
				String assignCode =tIssueGuideService.selectCode(info.getUnitCode(),DisputeConstClass.JB, info.getOrgCode());
				model.setAssignCode(assignCode);
				vo.setAddFlag(1);
			}
			StringUtil.copyProperties(model,info);
			int i = service.tempTCaseAssign(vo, info);
			String body = model.getOurLawsuitBody();
			if(i>0 && !StringUtil.isEmpty(body)) 
				configService.addSysBody(body, model.getAssignId(), DisputeConstClass.T_CASE_ASSIGN);
			return new ProcessResult(ProcessResult.SUCCESS, "", i);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}

	@Override
	@SmsAround
	@ApiOperation("提交新增案件交办")
	public ProcessResult addTCaseAssign(@RequestBody AddFlowVO<TCaseAssign> vo) {
		logger.info("提交新增案件交办参数:vo={}",vo);
		UserInfo info = RequestHolder.getUserInfo();
		TCaseAssign model = vo.getModel();
		if(null==info|| null==model) {
			return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.REQUEST_ERR_PARAM);
		}
		if (StringUtils.isBlank(model.getAssignCode())) {
			String assignCode =tIssueGuideService.selectCode(info.getUnitCode(),DisputeConstClass.JB, info.getOrgCode());
			model.setAssignCode(assignCode);
			vo.setAddFlag(1);
		}
		StringUtil.copyProperties(model,info);
		flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, false);
		int i = service.addTCaseAssign(vo, flowUtil, info);
	
		String body = model.getOurLawsuitBody();
		if(i>0 && !StringUtil.isEmpty(body)) 
			configService.addSysBody(body, model.getAssignId(), DisputeConstClass.T_CASE_ASSIGN);
		List<OrgParticipantVO> orgs = vo.getOrgs();
		Long flowId = flowUtil.getFlowId();
		try {
			TFlowLog log = new TFlowLog();
			flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, true);
			flowUtil.setFlowId(flowId);
			i = service.addFlow(vo,  flowUtil, info,log);
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
			return new ProcessResult(ProcessResult.SUCCESS, "", i);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			tFlowService.flowBack(service.getClass(), vo.getModel().getAssignId(), flowUtil);
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}

	@Override
	@ApiOperation("查询案件交办")
	public ProcessResult findTCaseAssign(@RequestBody ListParam param) {
		logger.info("查询案件交办参数:param={}",param);
		try {
			String loginAcct =  RequestHolder.getUserInfo().getLoginAcct();
			param.setLoginAcct(loginAcct);
			String path = ((OrgBeanVO) orgBeanService.selectProvincePathByCode().getData()).getOrgPath();// 获取当前公司组织树
			path = path.substring(0, path.length() - 1);
			path = path.substring(0,path.lastIndexOf(','));// 解析出省份公司组织树
			param.setOrgId(path);
			PageHelper.startPage(param.getPageNum(), param.getPageSize());
			List<TCaseAssign> list = service.selectAll(param, null);
			PageInfo<TCaseAssign> pageInfo = new PageInfo<>(list);
			return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}

	@Override
	@ApiOperation("案件交办详情")
	public ProcessResult findOne(String assignId, String actId) {
		logger.info("案件交办详情参数:assignId={}",assignId);
		TCaseAssignVO findOne = service.findOne(assignId, actId);
		return new ProcessResult(ProcessResult.SUCCESS, "", findOne);
	}

	@Override
	@ApiOperation("删除案件交办")
	public ProcessResult deleteTCaseAssign(@RequestBody List<String> assignIds) {
		logger.info("删除案件交办参数:assignIds={}",assignIds);
		int i = service.deleteTCaseAssign(assignIds);
		return new ProcessResult(ProcessResult.SUCCESS, "", i);
	}

	@Override
	public ProcessResult findTCaseAssignList(@RequestBody TCaseAssignListVO param) {
		logger.info("案件交办综合查询，参数:param={}",param.toString());
		return service.selectAlles(param);
	}

	@Override
	public ProcessResult test() {
		return service.test();
	}



}
