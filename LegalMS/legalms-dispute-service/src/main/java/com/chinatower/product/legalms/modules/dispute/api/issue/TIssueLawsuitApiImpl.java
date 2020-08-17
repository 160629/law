package com.chinatower.product.legalms.modules.dispute.api.issue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.common.DisputeConstClass.FAILURE;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.SmsAround;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueLawsuit;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.dispute.service.issue.TIssueGuideService;
import com.chinatower.product.legalms.modules.dispute.service.issue.TIssueLawsuitService;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.issue.TIssueLawsuitVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueLawsuitListVO;
import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.service.common.OrgBeanService;
import com.chinatower.product.legalms.modules.flow.service.common.OrgDepLeaderService;
import com.chinatower.product.legalms.modules.flow.service.common.SysBodyconfigService;
import com.chinatower.product.legalms.modules.flow.service.common.UserInfoService;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.eos.workflow.data.WFWorkItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 引诉信息表 前端控制器
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
@RestController
@Api(tags={"诉讼纠纷接口"})
public class TIssueLawsuitApiImpl  extends BaseController  implements TIssueLawsuitApi{
	private static final String CLASS_CATCH = "TIssueLawsuitApiImpl类捕捉异常";
	private static final Logger logger = LoggerFactory.getLogger(TIssueLawsuitApiImpl.class);
	@Autowired
	private TIssueLawsuitService service;
	@Autowired
	TIssueGuideService tIssueGuideService;
	@Autowired
	TFlowService tFlowService;
	@Autowired
	SysBodyconfigService configService;

	@Autowired
	OrgBeanService orgBeanService;

	@Autowired
	OrgDepLeaderService orgDepLeaderService;
	@Autowired
	FlowUtil flowUtil;
	
	@Override
	@SmsAround
	public ProcessResult addTFlowLog(HttpServletRequest request) {
		AddFlowLogVO vo=(AddFlowLogVO) request.getAttribute("vo");
		logger.info("修改诉讼纠纷参数:vo={}" , vo);
		try {
			return service.addTFlowLog(vo);
		} catch (Exception e) {
			logger.info("addTFlowLog业务处理异常", e);
			return new ProcessResult(ProcessResult.ERROR, e.getMessage());
		}
	}

	@Override
	@ApiOperation("修改诉讼纠纷")
	public ProcessResult updateTIssueLawsuit(@RequestBody TIssueLawsuit lawsuit) {
		try {
			logger.info("修改诉讼纠纷参数:lawsuit={}",lawsuit);
			int i = service.updateByPrimaryKey(lawsuit);
			String body = lawsuit.getOurLawsuitBody();
			if(i>0 && !StringUtil.isEmpty(body)) 
				configService.addSysBody(body, lawsuit.getLawsuitId(), DisputeConstClass.T_ISSUE_LAWSUIT);
			return new ProcessResult(ProcessResult.SUCCESS, "", i);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}
	
	@Override
	@ApiOperation("暂存诉讼纠纷")
	public ProcessResult tempTIssueLawsuit(@RequestBody AddFlowVO<TIssueLawsuit> vo) {
		logger.info("暂存诉讼纠纷参数:vo={}",vo);
		UserInfo info = RequestHolder.getUserInfo();
		TIssueLawsuit model = vo.getModel();
		if(null==info|| null==model) {
			return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.REQUEST_ERR_PARAM);
		}
		if (StringUtils.isBlank(model.getLawsuitCode())) {
			String lawsuitCode =tIssueGuideService.selectCode(info.getUnitCode(),DisputeConstClass.JF, info.getOrgCode());
			model.setLawsuitCode(lawsuitCode);
			vo.setAddFlag(1);
		}
		StringUtil.copyProperties(model,info);
		int i = service.tempTIssueLawsuit(vo,info);
		String body = model.getOurLawsuitBody();
		if(i>0 && !StringUtil.isEmpty(body)) 
			configService.addSysBody(body, model.getLawsuitId(), DisputeConstClass.T_ISSUE_LAWSUIT);
		return new ProcessResult(ProcessResult.SUCCESS, "", i);
	}
	
	
	@Override
	@SmsAround
	@ApiOperation("提交新增诉讼纠纷")
	public ProcessResult addTIssueLawsuit(@RequestBody AddFlowVO<TIssueLawsuit> vo) {
		logger.info("提交新增诉讼纠纷参数:vo={}",vo);
		UserInfo info = RequestHolder.getUserInfo();
		TIssueLawsuit model = vo.getModel();
		if(service.verifyCaseId(model)) {
			return new ProcessResult(ProcessResult.ERROR, "卷宗已作废");
		}
		if(null==info|| null==model) {
			return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.REQUEST_ERR_PARAM);
		}
		if (StringUtils.isBlank(model.getLawsuitCode())) {
			String lawsuitCode =tIssueGuideService.selectCode(info.getUnitCode(),DisputeConstClass.JF, info.getOrgCode());
			model.setLawsuitCode(lawsuitCode);
			vo.setAddFlag(1);
		}
		StringUtil.copyProperties(model,info);
		flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, false);
		int i = service.addTIssueLawsuit(vo,flowUtil,info);

		String body = model.getOurLawsuitBody();
		if(i>0 && !StringUtil.isEmpty(body)) 
			configService.addSysBody(body, model.getLawsuitId(), DisputeConstClass.T_ISSUE_LAWSUIT);
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
			}else {
				tIssueGuideService.addUndone(orgs.get(0).getDepaInterPersonP());
			}
			return new ProcessResult(ProcessResult.SUCCESS, "", i);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			tFlowService.flowBack(service.getClass(), vo.getModel().getLawsuitId(), flowUtil);
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}

	@Override
	@ApiOperation("查询诉讼纠纷")
	public ProcessResult findTIssueLawsuit(@RequestBody ListParam param) {
		logger.info("查询诉讼纠纷参数:param={}",param);
		try {
			String loginAcct =  RequestHolder.getUserInfo().getLoginAcct();
			param.setLoginAcct(loginAcct);
			PageHelper.startPage(param.getPageNum(), param.getPageSize());
			List<TIssueLawsuit> list = service.selectAll(param);
			PageInfo<TIssueLawsuit> pageInfo = new PageInfo<>(list);
			return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}

	@Override
	@ApiOperation("诉讼纠纷详情")
	public ProcessResult findOne(String lawsuitId, String actId) {
		logger.info("诉讼纠纷详情参数:lawsuitId={}",lawsuitId);
		TIssueLawsuitVO findOne = service.findOne(lawsuitId, actId);
		return new ProcessResult(ProcessResult.SUCCESS, "", findOne);
	}
	
	
	@Override
	@ApiOperation("删除诉讼纠纷")
	public ProcessResult deleteTIssueLawsuit(@RequestBody List<String> lawsuitIds) {
		logger.info("删除诉讼纠纷参数:lawsuitIds={}",lawsuitIds);
		int i = service.deleteTIssueGuide(lawsuitIds);
		return new ProcessResult(ProcessResult.SUCCESS, "", i);
	}

	@Override
	@ApiOperation("关联案件查询诉讼纠纷")
	public ProcessResult selectAllByCase(@RequestBody ListParam param) {
		logger.info("关联案件查询诉讼纠纷参数:param={}",param);
		UserInfo info = RequestHolder.getUserInfo();
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<TIssueLawsuitVO> list = service.selectAllByCase(param, info.getOrgId());
		PageInfo<TIssueLawsuitVO> pageInfo = new PageInfo<>(list);
		return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
	}

	@Override
	@ApiOperation("主键查询")
	public ProcessResult selectByPrimaryKey(String lawsuitId) {
		TIssueLawsuit one = service.selectByPrimaryKey(lawsuitId);
		return new ProcessResult(ProcessResult.SUCCESS, "", one);
	}
	@Autowired
	UserInfoService userInfoService;
	/*@Autowired
	OrgBeanService OrgBeanService;*/
	@Autowired
	TFlowCommonService commonService;
	@Override
	public ProcessResult test(String approveItemType, String id,HttpServletRequest request) {
		//int i = service.autoSaveCase(approveItemType,id,request.getHeader("pid"),null);
	/*	AccountLogic userInfo = userInfoMapper.selectUserInfo("chenxj");
		System.out.println(userInfo);*/
/*		UserInfo userInfo = RequestHolder.getUserInfo();
		List<String> roleList=new ArrayList<>();
		roleList.add("CHNTLEGALMS_13");
		roleList.add("CHNTLEGALMS_17");
		roleList.add("CHNTLEGALMS_33");
		List<AccountLogic> list = userInfoService.selectUserByRoleCode(roleList, userInfo , "01");
		List<AccountLogic> code = userInfoService.selectUserByRoleCode(roleList, userInfo, "02");
		
		List<String> orgList=new ArrayList<>();
		orgList.add("1000010200");
		orgList.add("1000012900");
		List<AccountLogic> list2 = userInfoService.selectCopyUserByRoleAndOrgCode(roleList, orgList);
		String code2 = RequestHolder.getProvinceCode(userInfo.getDeptId());
		OrgBeanVO vo = OrgBeanService.selectOrgInfoByOrgCode(code2);
		commonService.updateByPrimaryKeyStatus("t_issue_lawsuit", "72817677839c58d06cbe", "lawsuit_id",  "2");
		*/
		UserInfo info = RequestHolder.getUserInfo();
		String[] split= {"CHNTLEGALMS_13","CHNTLEGALMS_17","CHNTLEGALMS_33"};
		List<AccountLogic> list2 = userInfoService.selectUserByRoleCode(Arrays.asList(split), info, "02");
		List<AccountLogic> list3 = userInfoService.selectUserByRoleCode(Arrays.asList(split), info, "01");
		List<String> collect = list2.stream().map(AccountLogic :: getAccountName).collect(Collectors.toList());
		logger.info("autoUnViewOne发送list2:{}",collect);
		List<String> collect3 = list3.stream().map(AccountLogic :: getAccountName).collect(Collectors.toList());
		logger.info("autoUnViewOne发送list3:{}",collect3);
		return new ProcessResult(ProcessResult.SUCCESS, "", 0);
	}

	@Override
	public ProcessResult findTIssueLawsuitList(@RequestBody TIssueLawsuitListVO param) {
		return service.findTIssueLawsuitList(param);
	}

	@Override
	public ProcessResult updateCaseId(@RequestParam("caseId") String caseId, @RequestParam("id") String id, @RequestParam("caseTitle") String caseTitle,@RequestParam(value = "createCaseType", required = false)  Integer createCaseType) {
		TIssueLawsuit lawsuit = new TIssueLawsuit();
		lawsuit.setCaseId(caseId);
		lawsuit.setLawsuitId(id);
		lawsuit.setCaseTitle(caseTitle);
		lawsuit.setCreateCaseType(createCaseType);
		int i = service.updateCaseId(lawsuit);
		return new ProcessResult(ProcessResult.SUCCESS, ProcessResult.SUCCESS, i);
	}


}

