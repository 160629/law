package com.chinatower.product.legalms.modules.dispute.api.issue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueGuide;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.dispute.service.issue.TIssueGuideService;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.issue.TIssueGuideVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueGuideListVO;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.service.common.OrgBeanService;
import com.chinatower.product.legalms.modules.flow.service.common.OrgDepLeaderService;
import com.chinatower.product.legalms.modules.flow.service.common.SysBodyconfigService;
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
@Api(tags = { "引诉纠纷接口" })
public class TIssueGuideApiImpl extends BaseController implements TIssueGuideApi {
	private static final Logger logger = LoggerFactory.getLogger(TIssueGuideApiImpl.class);
	@Autowired
	TIssueGuideService service;
	@Autowired
	TFlowService tFlowService;
	@Autowired
	SysBodyconfigService configService;
	@Autowired
	OrgBeanService orgBeanService;
	@Autowired
	FlowUtil flowUtil;
	@Autowired
	OrgDepLeaderService orgDepLeaderService;
	@Autowired
	TFlowCommonService commonService;
	
	
	@Override
	public ProcessResult addTFlowLog(HttpServletRequest request) {
		AddFlowLogVO vo=(AddFlowLogVO) request.getAttribute("vo");
		logger.info("修改引诉纠纷参数:vo={}" , vo);
		try {
			return service.addTFlowLog(vo);
		} catch (Exception e) {
			logger.info("addTFlowLog业务处理异常" , e);
			return new ProcessResult(ProcessResult.ERROR, e.getMessage());
		}
	}
	
	
	@Override
	@ApiOperation("修改引诉纠纷")
	public ProcessResult updateTIssueGuide(@RequestBody TIssueGuide guide) {
		logger.info("修改引诉纠纷参数:guide={}" , guide);
		try {
			int i = service.updateByPrimaryKey(guide);
			String body = guide.getOurLawsuitBody();
			if(i>0 && !StringUtil.isEmpty(body)) 
				configService.addSysBody(body, guide.getGuideId(), DisputeConstClass.T_ISSUE_GUIDE);
			return new ProcessResult(ProcessResult.SUCCESS, "", i);
		} catch (Exception e) {
			logger.info("业务处理异常", e);
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}

	@Override
	@ApiOperation("暂存引诉纠纷")
	public ProcessResult tempTIssueGuide(@RequestBody AddFlowVO<TIssueGuide> vo) {
		logger.info("暂存引诉纠纷参数:vo={}" , vo);
		try {
			UserInfo info = RequestHolder.getUserInfo();
			TIssueGuide model = vo.getModel();
			if(null==info|| null==model) {
				return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.REQUEST_ERR_PARAM);
			}
			if (StringUtils.isBlank(model.getGuideCode())) {
				String guideCode = service.selectCode(info.getUnitCode(),DisputeConstClass.YS, info.getOrgCode());
				model.setGuideCode(guideCode);
				vo.setAddFlag(1);
			}
			StringUtil.copyProperties(model,info);
			int i = service.tempTIssueGuide(vo, info);
			String body = model.getOurLawsuitBody();
			if(i>0 && !StringUtil.isEmpty(body)) 
				configService.addSysBody(body, model.getGuideId(),  DisputeConstClass.T_ISSUE_GUIDE);
			return new ProcessResult(ProcessResult.SUCCESS, "", i);
		} catch (Exception e) {
			logger.info("业务处理异常" , e);
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}

	@Override
	@ApiOperation("提交新增引诉纠纷")
	public ProcessResult addTIssueGuide(@RequestBody AddFlowVO<TIssueGuide> vo) {
		logger.info("提交新增引诉纠纷参数:vo={}",vo);
		UserInfo info = RequestHolder.getUserInfo();
		TIssueGuide model = vo.getModel();
		if(null==info|| null==model) {
			return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.REQUEST_ERR_PARAM);
		}
		if (StringUtils.isBlank(model.getGuideCode())) {
			String guideCode = service.selectCode(info.getUnitCode(),DisputeConstClass.YS, info.getOrgCode());
			model.setGuideCode(guideCode);
			vo.setAddFlag(1);
		}
		StringUtil.copyProperties(model,info);
		flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, false);
		int i = service.addTIssueGuide(vo, flowUtil, info);
		
		String body = model.getOurLawsuitBody();
		if(i>0 && !StringUtil.isEmpty(body)) 
			configService.addSysBody(body, model.getGuideId(), DisputeConstClass.T_ISSUE_GUIDE);
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
				service.addOrgsUndone(orgs);
			} else {
				service.addUndone(orgs.get(0).getDepaInterPersonP());
			}

			return new ProcessResult(ProcessResult.SUCCESS, "", i);
		} catch (Exception e) {
			logger.info("业务处理异常,流程业务回滚" , e);
			tFlowService.flowBack(service.getClass(), vo.getModel().getGuideId(), flowUtil);
			return new ProcessResult(ProcessResult.ERROR,FAILURE.FAILURE_MESS);
		}

	}

	@Override
	@ApiOperation("查询引诉纠纷")
	public ProcessResult findTIssueGuide(@RequestBody ListParam param) {
		logger.info("查询引诉纠纷参数:param={}", param);
		try {
			String loginAcct =  RequestHolder.getUserInfo().getLoginAcct();
			param.setLoginAcct(loginAcct);
			PageHelper.startPage(param.getPageNum(), param.getPageSize());
			List<TIssueGuide> list = service.selectAll(param);
			PageInfo<TIssueGuide> pageInfo = new PageInfo<>(list);
			return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
		} catch (Exception e) {
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}

	@Override
	@ApiOperation("引诉纠纷详情")
	public ProcessResult findOne(String guideId, String actId) {
		logger.info("引诉纠纷详情参数:guideId={}", guideId);
		TIssueGuideVO findOne;
		try {
			findOne = service.findOne(guideId, actId);
		} catch (Exception e) {
			return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.MESSAGE_UNACTRIGHT);
		}

		return new ProcessResult(ProcessResult.SUCCESS, "", findOne);
	}

	@Override
	@ApiOperation("删除引诉纠纷")
	public ProcessResult deleteTIssueGuide(@RequestBody List<String> guideIds) {
		logger.info("删除引诉纠纷参数:guideIds={}",guideIds);
		int i = service.deleteTIssueGuide(guideIds);
		return new ProcessResult(ProcessResult.SUCCESS, "", i);
	}

	@Override
	public Map<String, Object> selectByType(String tableName, String fieldName, String fieldValue) {
		return service.selectByType(tableName, fieldName, fieldValue);
	}

	@Override
	@ApiOperation("引诉纠纷综合查询")
	public ProcessResult findTIssueGuideList(@RequestBody TIssueGuideListVO param) {
		logger.info("引诉纠纷综合查询，条件：" ,param.toString());
		return service.findTIssueGuideList(param);
	}

	@Override
	@ApiOperation("关联案件查询引诉纠纷")
	public ProcessResult selectAllByCase(@RequestBody ListParam param) {
		logger.info("关联案件查询引诉纠纷参数:param={}",param);
		UserInfo info = RequestHolder.getUserInfo();
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<TIssueGuideVO> list = service.selectAllByCase(param, info.getOrgId());
		PageInfo<TIssueGuideVO> pageInfo = new PageInfo<>(list);
		return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
	}

	@Override
	@ApiOperation("主键查询")
	public ProcessResult selectByPrimaryKey(String guideId) {
		try {
			TIssueGuide g = service.selectByPrimaryKey(guideId);
			return new ProcessResult(ProcessResult.SUCCESS, "", g);
		} catch (Exception e) {
			return new ProcessResult(ProcessResult.ERROR,FAILURE.FAILURE_MESS);
		}
	}

	@Override
	public void test(HttpServletResponse response,@RequestBody HashMap<String,Object> map) throws IOException {
/*		Word07Writer writer = new Word07Writer();

		// 添加段落（标题）
		writer.addText(new Font("方正小标宋简体", Font.PLAIN, 22), "我是第一部分", "我是第二部分");
		// 添加段落（正文）
		writer.addText(new Font("宋体", Font.PLAIN, 22), "我是正文第一部分", "我是正文第二部分");
		// 写出到文件
		writer.flush(FileUtil.file("d:/test/wordWrite.docx"));
		// 关闭
		writer.close();*/
/*		List<Map<String, Object>> list = service.getExcelDate();
		FileUtil.downloadExcel(list, response);*/
/*		Map<String, Object> test = commonService.test();
		System.out.println(test);
		flowUtil.init("chenxj" , "陈晓杰", ConstClass.TENANLID, null, false);
		List<WFWorkItem> inst = flowUtil.getNextWorkItems(100111880);
		System.out.println(inst);*/
		service.inserttest("1");
	/*	TIssueGuide instance = new TIssueGuide();
		try {
			BeanUtils.populate(instance, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		int y = service.updateByPrimaryKeySelective(instance);
		System.out.println(y);*/
	}

}
