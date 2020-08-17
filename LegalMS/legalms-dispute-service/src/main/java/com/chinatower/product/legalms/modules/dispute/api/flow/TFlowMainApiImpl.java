package com.chinatower.product.legalms.modules.dispute.api.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.ScheduledProperties;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowMainService;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowUnviewService;
import com.chinatower.product.legalms.modules.dispute.vo.push.NotifAndUndone;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowMain;
import com.chinatower.product.legalms.modules.flow.entity.unview.TFlowUnview;
import com.chinatower.product.legalms.modules.flow.vo.unview.TFlowUnviewParam;
import com.eos.workflow.data.WFActivityDefine;
import com.eos.workflow.data.WFNotificationInst;
import com.eos.workflow.data.WFNotificationInst.State;
import com.eos.workflow.data.WFProcessInst;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.helper.ResultList;
import com.eos.workflow.omservice.WFParticipant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.primeton.workflow.api.PageCond;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 审批表 前端控制器
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
@RestController
@Api(tags = { "流程主接口" })
public class TFlowMainApiImpl implements TFlowMainApi {
	private static final Logger logger = LoggerFactory.getLogger(TFlowMainApiImpl.class);
	@Autowired
	TFlowMainService tFlowMainService;
	
	@Autowired
	TFlowUnviewService tFlowUnviewService;
	
	@Autowired
	ScheduledProperties properties;
	@Autowired
	FlowUtil flowUtil;
	@Override
	@ApiOperation(value = "确认通知")
	@ApiImplicitParam(value = "通知ID",name = "notificationID")
	public ProcessResult confirmNotification(long notificationID) {
		flowUtil.init("xiaoyz", "肖益忠", DisputeConstClass.TENANLID, null, false);
		try {
			flowUtil.getClient().getNotificationManager().confirmNotification(notificationID);
			return new ProcessResult(ProcessResult.SUCCESS, "", null);
		} catch (Exception e) {
			return new ProcessResult(ProcessResult.ERROR, e.getMessage());
		}
	}
	@Override
	@ApiOperation(value = "查询通知")
	@ApiImplicitParam(value = "VIEWED,已阅 UNVIEWED 待阅",name = "status")
	public ProcessResult queryNotifications(String status) {
		flowUtil.init("xiaoyz", "肖益忠", DisputeConstClass.TENANLID, null, false);
		try {
			List<WFNotificationInst> list =null;
			if("VIEWED".equals(status)) {
				list = flowUtil.queryNotificationsCriteria(State.VIEWED);
			}else if("UNVIEWED".equals(status)) {
				list = flowUtil.queryNotificationsCriteria(State.UNVIEWED);
			}
			return new ProcessResult(ProcessResult.SUCCESS, "", list);
		} catch (Exception e) {
			return new ProcessResult(ProcessResult.ERROR, e.getMessage());
		}
	}
	
	@Override
	@ApiOperation(value = "推送待办待阅")
	public NotifAndUndone queryNotifAndUndone(String serviceId,String userCode,String flag,String userName,Integer pageNum,Integer pageSize) {
		logger.info("推送待办待阅参数：serviceId={},userCode={},flag={},userName={},pageNum={},pageSize={}",serviceId,userCode,flag,userName,pageNum,pageSize);
		flowUtil.init(userCode, userName, DisputeConstClass.TENANLID, null, false);
		try {
			if("1".equals(flag)) {
				TFlowUnviewParam record =new TFlowUnviewParam();
				record.setViewStatus("0");
				record.setToerId(userCode);
				PageHelper.startPage(pageNum, pageSize);
				List<TFlowUnview> list = tFlowUnviewService.selectAll(record);
				logger.info("推送待阅列表：list.size()={}",list.size());
				PageInfo<TFlowUnview> pageInfo = new PageInfo<>(list);
				String params=properties.getFormurl()+properties.getUnviewUrl();
				for (TFlowUnview fu : list) {
					String toerId = fu.getToerId();
					String userId = toerId.split("_")[0];
					String viewUrl=params+fu.getViewId()+"&userId="+userId+"&appAcctId="+toerId;
					fu.setViewUrl(viewUrl);
				}
				int total = (int) pageInfo.getTotal();
				return new NotifAndUndone<TFlowUnview>().setServiceId(serviceId)
				.setBereadMessage(list).setFlag(flag).setUserId(userCode).setBereadSum(total);
			}else if("0".equals(flag)) {
				PageCond pageCond = new PageCond((pageNum-1)*pageSize, pageSize, true);
				List<WFWorkItem> list = flowUtil.queryPersonWorkItems(userCode, "ALL", "ALL", pageCond);
				logger.info("推送待办列表：list.size()={}",list.size());
				
				for (WFWorkItem wfWorkItem : list) {
					String participant = wfWorkItem.getParticipant();
					long activityInstID = wfWorkItem.getActivityInstID();
					long processInstID = wfWorkItem.getProcessInstID();
					String subProcessInstID="";
					if(wfWorkItem.getRootProcInstID()!=processInstID) {
						WFProcessInst parentInst = flowUtil.queryProcessInstDetail(wfWorkItem.getRootProcInstID());
						if(null!=parentInst) {
							wfWorkItem.setProcessInstName(parentInst.getProcessInstName());
							subProcessInstID="&subProcessInstID="+processInstID;
							processInstID=wfWorkItem.getRootProcInstID();
						}
					}
					String userId = participant.split("_")[0];
					String actionURL = properties.getFormurl()+"/legalms/legalms-portal/form.html?activityInstId="+activityInstID+"&processInstId="+processInstID+"&userId="+userId+"&openType=1&pageKey=view";
					actionURL=actionURL+"&appAcctId="+participant+subProcessInstID;
					wfWorkItem.setActionURL(actionURL);
				}
				if(!list.isEmpty()) {
					ResultList<WFWorkItem> resultItems = (ResultList<WFWorkItem>)list;
					int count = resultItems.getPageCond().getCount();
					return new NotifAndUndone<WFWorkItem>().setServiceId(serviceId)
							.setBereadMessage(list).setFlag(flag).setUserId(userCode).setBereadSum(count);
				}else {
					list= new ArrayList<>();
					return new NotifAndUndone<WFWorkItem>().setServiceId(serviceId)
							.setBereadMessage(list).setFlag(flag).setUserId(userCode).setBereadSum(0);
				}
			}
			return new NotifAndUndone<String>().setServiceId(serviceId)
					.setBereadMessage(Arrays.asList("参数错误")).setFlag(flag).setUserId(userCode);
		} catch (Exception e) {
			logger.info("TFlowMainApiImpl类推送待办待阅捕捉异常{}",e);
			return new NotifAndUndone<String>().setServiceId(serviceId)
					.setBereadMessage(Arrays.asList("程序加班中")).setFlag(flag).setUserId(userCode);
		}
	}
	@Override
	@ApiOperation(value = "查询所有流程")
	public ProcessResult selectAll() {
		try {
			List<TFlowMain> selectAll = tFlowMainService.selectAll();
			return new ProcessResult(ProcessResult.SUCCESS, "", selectAll);
		} catch (Exception e) {
			return new ProcessResult(ProcessResult.ERROR, e.getMessage());
		}

	}

	@Override
	@ApiOperation("根据流程定义ID与活动定义ID查询活动参与者")
	@ApiImplicitParams({@ApiImplicitParam(name="processDefID",value="流程定义ID"),
		@ApiImplicitParam(name="activityDefID",value="活动定义ID")})
	public ProcessResult getParticipants(long processDefID, String activityDefID) {
		flowUtil.init(DisputeConstClass.USERID, DisputeConstClass.USERNAME, DisputeConstClass.TENANLID, null, false);
		try {
			WFActivityDefine define = flowUtil.getParticipants(processDefID, activityDefID);
			return new ProcessResult(ProcessResult.SUCCESS, "", define);
		} catch (Exception e) {
			return new ProcessResult(ProcessResult.ERROR, e.getMessage());
		}
	}

	@Override
	@ApiOperation("根据流程定义名称查询所有活动信息")
	@ApiImplicitParams({@ApiImplicitParam(name="name",value="流程定义名称"),
		@ApiImplicitParam(name="hasParticipant",value="是否包含参与者")})
	public ProcessResult getActivityDefineByName(String name, boolean hasParticipant) {
		flowUtil.init(DisputeConstClass.USERID, DisputeConstClass.USERNAME, DisputeConstClass.TENANLID, null, false);
		try {
			List<WFActivityDefine> define = flowUtil.getActivityDefineByName(name, hasParticipant);
			return new ProcessResult(ProcessResult.SUCCESS, "", define);
		} catch (Exception e) {
			return new ProcessResult(ProcessResult.ERROR, e.getMessage());
		}
	}

	@Override
	@ApiOperation("根据活动实列查询后继活动信息 或 根据流程定义名称与活动名称查询活动信息")
	@ApiImplicitParams({@ApiImplicitParam(name="name",value="流程定义名称"),
		@ApiImplicitParam(name="activityName",value="与活动名称"),
		@ApiImplicitParam(name="hasParticipant",value="是否包含参与者"),
		@ApiImplicitParam(name="actInstID",value="活动实列查询ID")})
	public ProcessResult getActivityDefineByNameOrID(String name, String activityName,Long actInstID) {
		flowUtil.init(DisputeConstClass.USERID, DisputeConstClass.USERNAME, DisputeConstClass.TENANLID, null, false);
		try {
			WFActivityDefine define = flowUtil.getActivityDefineByNameOrID(name, activityName,
					actInstID);
			return new ProcessResult(ProcessResult.SUCCESS, "", define);
		} catch (Exception e) {
			return new ProcessResult(ProcessResult.ERROR, e.getMessage());
		}
	}

	@Override
	@ApiOperation("修改工作项参与者")
	@ApiImplicitParam(name="workItemID",value="工作项ID")
	public ProcessResult updateParticipant(long workItemID, List<WFParticipant> participants) {
		flowUtil.init(DisputeConstClass.USERID, DisputeConstClass.USERNAME, DisputeConstClass.TENANLID, null, false);
		try {
			flowUtil.updateParticipant(workItemID, participants);
			return new ProcessResult(ProcessResult.SUCCESS, "", null);
		} catch (Exception e) {
			return new ProcessResult(ProcessResult.ERROR, e.getMessage());
		}
	}
	@Override
	@ApiOperation("获取主键ID")
	public ProcessResult getPrimaryKey() {
		return new ProcessResult(ProcessResult.SUCCESS, "", StringUtil.getId());
	}



}
