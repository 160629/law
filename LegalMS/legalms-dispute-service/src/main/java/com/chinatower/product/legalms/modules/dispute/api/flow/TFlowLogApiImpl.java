package com.chinatower.product.legalms.modules.dispute.api.flow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowLogService;
import com.chinatower.product.legalms.modules.dispute.service.issue.TIssueGuideService;
import com.chinatower.product.legalms.modules.dispute.service.issue.TIssueLawsuitService;
import com.chinatower.product.legalms.modules.dispute.service.lawcase.TCaseAssignService;
import com.chinatower.product.legalms.modules.flow.entity.flow.FlowChart;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.vo.common.MyOwnRuntimeException;
import com.chinatower.product.legalms.modules.flow.vo.common.TaskVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.utils.FileUtil;
import com.eos.workflow.data.WFProcessDefine;
import com.eos.workflow.data.WFProcessInst;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Charsets;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 审批人员表 前端控制器
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
@RestController
@Api(tags={"流程处理日志接口"})
public class TFlowLogApiImpl extends BaseController implements TFlowLogApi{
	private static final Logger log = LoggerFactory.getLogger(DisputeConstClass.LOGGER_NAME);

	@Autowired
	TFlowLogService service;

	@Autowired
	TIssueGuideService tIssueGuideService;

	@Autowired
	TIssueLawsuitService tIssueLawsuitService;

	@Autowired
	TCaseAssignService tCaseAssignService;
	@Autowired
	FlowUtil flowUtil;

	@Value("${flowurl}")
	String flowUrl;

	@Override
	@ApiOperation("审批流程记录操作日志")
	public ModelAndView addTFlowLog(@RequestBody AddFlowLogVO vo) {
		ModelAndView view = new ModelAndView();
		view.addObject("vo", vo);
		if(null==vo || StringUtil.isEmpty(vo.getModuleName())) {
			log.info("审批参数错误vo="+vo);
		} else if (vo.getModuleName().startsWith("premise_dispute")) { // 引诉纠纷
			view.setViewName("/tIssueGuide/addTFlowLog");
		} else if (vo.getModuleName().startsWith("event_assign")) { // 案件交办
			view.setViewName("/tCaseAssign/addTFlowLog");
		} else if (vo.getModuleName().startsWith("lawsuitdeclare")) { // 诉讼
			view.setViewName("/tIssueLawsuit/addTFlowLog");
		} else if (vo.getModuleName().startsWith("assit_transact")) { // 案件协办
			view.setViewName("/tIssueJointly/addTFlowLog");
		} else if (vo.getModuleName().startsWith("law_writ_transact")) { // 法律文书办理
			view.setViewName("/legislation/addTFlowLog");
		}else if (vo.getModuleName().startsWith("issue_assist")) { // 法律文书办理
			view.setViewName("/tIssueAssist/addTFlowLog");
		}
		return view;
	}
	@Override
	@ApiOperation("查询流程处理日志")
	public ProcessResult findTFlowLog(Integer pageNum, Integer pageSize) {
		try {
			PageHelper.startPage(pageNum, pageSize);
			List<TFlowLog> list = service.selectAll();
			PageInfo<TFlowLog> pageInfo = new PageInfo<>(list);
			return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
		} catch (Exception e) {
			return new ProcessResult(ProcessResult.ERROR, e.getMessage());
		}
	}

	@Override
	@ApiOperation("查询主流程日志信息")
	public ProcessResult selectMainFlow(@RequestParam String businessId, @RequestParam String businessType) {
		// 主流程审批记录
		List<TFlowLog> mainFlowLog =  service.selectMainFlowLog(businessId, businessType);
		// 获取子流程ID
		List<String> subFlowLogIds = service.selectSubFlowIds(mainFlowLog.get(0).getFlowId());
		// 查询业务记录
		Map<String, Object> map = new HashMap<>();
		map.put("mainFlowLog", mainFlowLog);
		map.put("subFlowLogIds", subFlowLogIds);
		return new ProcessResult(ProcessResult.SUCCESS, "", map);
	}

	@Override
	@ApiOperation("查询子流程日志信息")
	public ProcessResult selectSubFlow(String subProcessFlowLogId) {
		TFlowLog subFlowLogs = service.selectByPrimaryKey(subProcessFlowLogId);
		return new ProcessResult(ProcessResult.SUCCESS, "", subFlowLogs);
	}

	@Override
	@ApiOperation("作废接口")
	public ProcessResult nullifyFlow(@RequestBody Map<String, Object> businessMap) {
		return service.nullifyFlow(businessMap);
	}
	@Override
	public void downloadExcel(Integer pageNum, Integer pageSize,HttpServletResponse response) throws IOException {
		PageHelper.startPage(pageNum, pageSize);
		List<TFlowLog> logs = service.selectAll();
		int k=1;
	       List<Map<String, Object>> list = new ArrayList<>();
        for (TFlowLog log2 : logs) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(k+++"", log2.getActivityDefId());
            map.put(k+++"",log2.getActivityDefName());
            map.put(k+++"", log2.getActivityInstId());
            map.put(k+++"", log2.getActPid());
            map.put(k+++"",log2.getApproveItemId());
            map.put(k+++"", log2.getApproveItemType());
            map.put(k+++"", log2.getBusinessAdvice());
            map.put(k+++"", log2.getFlowDefName());
            map.put(k+++"", log2.getFlowId());
            map.put(k+++"", log2.getFlowLogId());
            map.put(k+++"", log2.getFlowPid());
            map.put(k+++"", log2.getModifyTime());
            map.put(k+++"", log2.getNextActivityDefId());
            map.put(k+++"", log2.getNextActivityDefName());
            map.put(k+++"", log2.getOpinion());
            map.put(k+++"", log2.getOrgId());
            map.put(k+++"", log2.getOrgName());
            map.put(k+++"", log2.getReceiverCompanyId());
            map.put(k+++"", log2.getReceiverCompanyName());
            map.put(k+++"", log2.getReceiverId());
            map.put(k+++"", log2.getReceiverName());
            map.put(k+++"", log2.getReceiverOrgId());
            map.put(k+++"", log2.getReceiverOrgName());
            map.put(k+++"", log2.getStatus());
            map.put(k+++"", log2.getToerId());
            map.put(k+++"", log2.getToerName());
            map.put(k+++"", log2.getUserId());
            map.put(k+++"", log2.getUserName());
            map.put(k+++"", log2.getWorkType());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
	}

	@Override
	public ProcessResult tempSelective(@RequestBody AddFlowLogVO addFlowLogVO) {
		return service.tempSelective(addFlowLogVO);
	}

	@Override
	public ProcessResult getFlowChart(@RequestBody FlowChart flowChart) {
		log.info("获取流程图参数对象：" + flowChart);
		log.info("流程平台内网地址：" + flowUrl);
		UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
		String url;
		String result="";//访问返回结果
		BufferedReader read=null;//读取访问结果
		flowUtil.init(info.getLoginAcct(), info.getLoginName(), DisputeConstClass.TENANLID, null, false);
		try {
			if (flowChart.getFlowInstId() != null && flowChart.getFlowInstId() != 0) {
				WFProcessInst parentProcessInst = flowUtil.getParentProcessInst(flowChart.getFlowInstId());
				WFProcessInst wfProcessInst = flowUtil.queryProcessInstDetail(flowChart.getFlowInstId());
				flowChart.setFlowDefId(wfProcessInst.getProcessDefID());
				flowChart.setFlowDefName(wfProcessInst.getProcessDefName());
				flowChart.setFlowInstName(wfProcessInst.getProcessInstName());
				if (parentProcessInst != null) {
					flowChart.setParentFlowInstId(parentProcessInst.getProcessInstID());
					flowChart.setParentFlowInstName(parentProcessInst.getProcessInstName());
					flowChart.setParentFlowDefId(parentProcessInst.getProcessDefID());
					flowChart.setParentFlowDefName(parentProcessInst.getProcessDefName());
				}
				url = "http://" + flowUrl + "/workspace/showprocess.jsp?tenantID=lms&processInstID=" + flowChart.getFlowInstId();
			} else {
				List<WFProcessDefine> wfProcessDefines = flowUtil.queryProcessesByName(flowChart.getFlowDefName());
				if (wfProcessDefines != null && !wfProcessDefines.isEmpty()) {
					flowChart.setFlowDefId(wfProcessDefines.get(0).getProcessDefID());
					flowChart.setFlowDefName(wfProcessDefines.get(0).getProcessDefName());
					url = "http://" + flowUrl + "/workspace/showprocessdef.jsp?tenantID=lms&processDefID=" + flowChart.getFlowDefId();
				} else {
					String msg = "流程定义名称错误";
					log.error(msg);
					throw new MyOwnRuntimeException(msg);
				}
			}

			//创建url
			URL realurl=new URL(url);
			//打开连接
			URLConnection connection=realurl.openConnection();
			//建立连接
			connection.connect();
			read = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), Charsets.UTF_8));
			StringBuilder sb = getFlowChartString(read);
			result = sb.toString();
			result = result.replaceAll("/workspace/workflow/", "workflow/");
			result = result.replaceAll("/workspace/common/", "common/");
			result = result.replaceAll(";;", ";");
			result = result.replaceAll(".gif", ".png");
			flowChart.setFlowChartHtml(result);
			log.info(result);
		} catch (Exception e) {
			log.error("获取流程图对象错误");
			log.error(ProcessResult.ERROR,e);
		} finally{
			if(read!=null){//关闭流
				try {
					read.close();
				} catch (IOException e) {
					log.error(ProcessResult.ERROR,e);
				}
			}
		}
		return new ProcessResult(ProcessResult.SUCCESS, "", flowChart);
	}

	private StringBuilder getFlowChartString(BufferedReader read) throws IOException {
		String line;//循环读取
		StringBuilder sb = new StringBuilder();
		boolean isStart = false;
		while ((line = read.readLine()) != null) {
			if (line.equals("<script language=\"javascript\">")) {
				isStart = true;
			}
			if (line.equals("</script>")) {
				isStart = false;
			}
			sb.append(line);
			if (!line.equals("<script language=\"javascript\">") && !line.startsWith("/*") && !line.endsWith("*/") && isStart) {
				sb.append(";");
			}
		}
		return sb;
	}

	@Override
	public ProcessResult getCompleteFlow(@RequestBody TaskVO taskVO) {
		return service.selectCompleteFlow(taskVO);
	}

	@Override
	public ProcessResult flowDrawBack(@RequestParam Long activityInstId) {
		return service.flowDrawBack(activityInstId);
	}
}

