
package com.chinatower.product.legalms.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.eos.workflow.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.RequestScope;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.flow.FlowStartVO;
import com.chinatower.product.legalms.modules.flow.vo.common.MyOwnRuntimeException;
import com.eos.das.entity.DASManager;
import com.eos.das.entity.ExpressionHelper;
import com.eos.das.entity.IDASCriteria;
import com.eos.workflow.data.WFActivityDefine;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFNotificationInst;
import com.eos.workflow.data.WFNotificationInst.State;
import com.eos.workflow.data.WFProcessDefine;
import com.eos.workflow.data.WFProcessInst;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.IWFOMService;
import com.eos.workflow.omservice.WFParticipant;
import com.primeton.bps.disttrans.api.IClientGlobalTransactionManager;
import com.primeton.workflow.api.PageCond;
import com.primeton.workflow.api.WFReasonableException;
import com.primeton.workflow.api.WFServiceException;
import com.primeton.workflow.model.definition.Participant;

/**
 *
 * @author wangyong
 *
 */
@Component
//@Scope("prototype")
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FlowUtil {
	private boolean onTransaction;
	private IBPSServiceClient client;
	private String userId;
	private String userName;
	private String tenantId;
	private Long flowId;
	private Long activityInstID;
	private String flowName;
	private IClientGlobalTransactionManager globalTxMgr;
	@Value("${push-service.formName}")
    private String formName;
//	private static final Logger log = LoggerFactory.getLogger(FlowUtil.class);

	private static final Logger log = LoggerFactory.getLogger(FlowUtil.class);

	public String getFlowDefName(Long flowId) {
		try {
			WFProcessInst wfProcessInst = client.getProcessInstManager().queryProcessInstDetail(flowId);
			return wfProcessInst.getProcessDefName();
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
		}
		return "";
	}

	// 工作项转办
	public void delegateWorkItem(String toerId,String toerName,Long workItemId){
		try {
			WFParticipant[] recipients = {new WFParticipant(toerId, toerName, "emp")};
			IWFDelegateManager manager = client.getDelegateManager();
			manager.delegateWorkItem(workItemId, recipients, "DELEG");
		} catch (Exception e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
	}

	// 终止流程
	public void nullifyFlow(long processInstId){
		try {
			client.getProcessInstManager().terminateProcessInstance(processInstId);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
	}

	// 修改业务冗余字段
	public void updateBizInfo(long processInstId, Map<String, Object> map) {
		try {
			client = BPSServiceClientFactory.getClient(formName);
			client.getCommonManager().updateBizInfo(processInstId, map);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
	}

	public void setMainFlowBusinessFields(FlowUtil flowUtil, Map<String, Object> bizInfo, WFProcessInst processInst2) {
		long mainFlowId = 0L;
		while (true) {
			if (processInst2.getParentProcID() == 0) {
				mainFlowId = processInst2.getProcessInstID();
				break;
			} else {
				processInst2 = flowUtil.queryProcessInstDetail(processInst2.getParentProcID());
			}
		}
		flowUtil.updateBizInfo(mainFlowId, bizInfo);// 更新主流程业务荣誉字段表
	}

	//查询当前活动的直接连接活动
	public List<WFActivityDefine> queryNextActivities(long processDefID, String activityDefID) {
		IWFDefinitionQueryManager manager = client.getDefinitionQueryManager();
		try {
			return manager.queryNextActivities(processDefID, activityDefID);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}

	}
	//批量设置流程参数
	public void setRelativeDataBatch(long processInstID,Map<String,Object> map) {
		try {
			client.getRelativeDataManager().setRelativeDataBatch(processInstID, map);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
	}

	public void removeRelativeDataBatch(long processInstID, String nodeName){
		try {
			client.getRelativeDataManager().removeRelativeDataBatch(processInstID, nodeName);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
	}
	//批量设置流程参数
	public void setRelativeData(long processInstID,String key,Object value) {
		try {
			client.getRelativeDataManager().setRelativeData(processInstID, key, value);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
	}
	//查询流程详情
	public WFProcessInst queryProcessInstDetail(long processInstID) {
		try {
			return client.getProcessInstManager().queryProcessInstDetail(processInstID);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}

	}
	//查询活动详情
	public WFActivityInst queryActivityDetail(long actInstID) {
		try {
			return client.getActivityInstManager().findActivityInstByActivityInstID(actInstID);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}

	}
	//查询工作项详情
	public WFWorkItem queryWorkItemDetail(long workItemID) {
		try {
			return client.getWorkItemManager().queryWorkItemDetail(workItemID);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}

	}

	public void finishFreeActivityByActInstId(Long actInstId, String nextActDefId, boolean transactionSpan) {

        try {
            log.info("FlowUtil流程bug日志：" + actInstId);
            List<WFWorkItem> wfWorkItems = client.getWorkItemManager().queryWorkItemsByActivityInstID(actInstId, null);
            log.info("当前活动下工作项数量：" + wfWorkItems.size());
            log.info("当前环节下所有工作项列表：");
            wfWorkItems.forEach(x -> log.info(x.getWorkItemID() + ""));
            List<WFWorkItem> collect = wfWorkItems.stream().filter(x -> x.getCurrentState() == 10).collect(Collectors.toList());
            log.info("过滤后集合大小：" + collect.size());
            log.info("运行工作项列表：");
            collect.forEach(x -> log.info(x.getWorkItemID() + ""));
            // 获取当前活动节点
            Long curWorkItem = wfWorkItems.get(0).getWorkItemID();
            log.info("工作项ID：" + curWorkItem);
            // 判断当前节点是否为自有流节点
            IWFFreeFlowManager iwfFreeFlowManager = client.getFreeFlowManager();
            boolean isFreeActivity = iwfFreeFlowManager.isFreeActivity(curWorkItem);
            log.info("当前工作项所对应的活动系欸但是否为自由节点：" + isFreeActivity);
            if (isFreeActivity) {
                // 设置当前工作项对应的活动节点的下一个活动
                iwfFreeFlowManager.appointNextActivities(curWorkItem, "set", new String[]{nextActDefId});
                // 查询当前节点的后继节点
                List<WFActivityDefine> wfActivityDefines = iwfFreeFlowManager.queryAppointedNextActivities(curWorkItem);
                log.info("当前节点的后续节点列表：");
                wfActivityDefines.stream().forEach(x -> log.info(x.getId()));
                // 完成当前工作项
                client.getWorkItemManager().finishWorkItem(curWorkItem, transactionSpan);
                log.info("审批完成");
            } else {
				try {
					client.getWorkItemManager().finishWorkItem(wfWorkItems.get(0).getWorkItemID(), transactionSpan);
					log.info("审批完成");
				} catch (Exception e) {
					log.error(ProcessResult.ERROR,e);
					throw new MyOwnRuntimeException(e);
				}
			}
        } catch (Exception e) {
            log.error(ProcessResult.ERROR, e);
            throw new MyOwnRuntimeException(e);
        }
    }

	public void finishWorkItemByActInstId(Long actInstId, boolean transactionSpan){
		try {
			log.info("FlowUtil流程bug日志：" + actInstId);
            List<WFWorkItem> wfWorkItems = client.getWorkItemManager().queryWorkItemsByActivityInstID(actInstId, null);
            log.info("当前活动下工作项数量：" + wfWorkItems.size());
            log.info("当前环节下所有工作项列表：");
			wfWorkItems.forEach(x -> log.info(x.getWorkItemID() + ""));
			List<WFWorkItem> collect = wfWorkItems.stream().filter(x -> x.getCurrentState() == 10).collect(Collectors.toList());
			log.info("过滤后集合大小：" + collect.size());
			log.info("运行工作项列表：");
			collect.forEach(x -> log.info(x.getWorkItemID() + ""));
			log.info("工作项ID：" + wfWorkItems.get(0).getWorkItemID());
            client.getWorkItemManager().finishWorkItem(wfWorkItems.get(0).getWorkItemID(), transactionSpan);
            log.info("审批完成");
		} catch (Exception e) {
			log.error(ProcessResult.ERROR,e);
			throw new MyOwnRuntimeException(e);
		}
	}

	public void finishWorkItemByActInstId(String flowDefId, WFActivityInst wfActivityInst, boolean transactionSpan){
		try {
			log.info("=====================================参数=====================================");
			log.info("流程驱动新方法");
			log.info("流程定义ID：" + flowDefId);
			log.info("活动实例：：" + wfActivityInst.toString());
			IWFQueryManager queryManager = client.getCommonQueryManage();
			IDASCriteria criteria = DASManager.createCriteria(flowDefId);
			// activityDefID为WFWorkItem实例的属性名
			criteria.add(ExpressionHelper.eq("activityDefID", wfActivityInst.getActivityDefID()));
			criteria.add(ExpressionHelper.eq("processInstID", wfActivityInst.getProcessInstID()));
			criteria.add(ExpressionHelper.eq("activityInstID", wfActivityInst.getActivityInstID()));
			criteria.add(ExpressionHelper.eq("currentState", 10));
			List<WFWorkItem> queryWorkItems = queryManager.queryWorkItemsCriteria(criteria, null);
			log.info("结果大小：" + queryWorkItems.size());
			log.info("结果列表：");
			queryWorkItems.stream().forEach(x -> log.info(x.getWorkItemID() + ""));
			log.info("第一个工作项id：" + queryWorkItems.get(0).getActivityInstID());
			client.getWorkItemManager().finishWorkItem(queryWorkItems.get(0).getWorkItemID(), transactionSpan);
			log.info("============================================================================");
		} catch (Exception e) {
			log.error(ProcessResult.ERROR,e);
			throw new MyOwnRuntimeException(e);
		}
	}

    public WFWorkItem queryWorkItemByActInstId(Long actInstId) {
        try {
            List<WFWorkItem> wfWorkItems = client.getWorkItemManager().queryWorkItemsByActivityInstID(actInstId, null);
            return wfWorkItems.get(0);
        } catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
        }
    }

	// 完成工作项
	public void finishWorkItem(long workItemID, boolean transactionSpan) {
		try {
			client.getWorkItemManager().finishWorkItem(workItemID, transactionSpan);
		} catch (WFServiceException | WFReasonableException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
	}

	// 完成工作项
	public void finishActivity(long flowId, String activityDefID) {
		try {
			client.getActivityInstManager().finishActivityInstByActivityID(flowId, activityDefID);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
	}

	/**
	 * 根据当前活动ID与目标活动定义ID或ID执行回退
	 *
	 * 回退策略 time 基于两点间的时间回退 path 暂用：基于两点间的路径回退 recent_manual 回退到最近的人工活动 one_step
	 * 单步回退 simple 简单回退
	 */
	public void backActivity(long currentActInstID, long destActInstID, String destActDefID, String rollBackStrategy) {
		IWFBackActivityManager bm = client.getBackActivityManager();
		try {
			/*
			 * WFProcessInst processInst =
			 * client.getProcessInstManager().queryProcessInstDetail(flowId);
			 * if(0!=processInst.getParentActID()) {
			 * currentActInstID=processInst.getParentActID(); }
			 */
			if (StringUtils.isEmpty(destActDefID)) {
				bm.backActivity(currentActInstID, destActInstID, rollBackStrategy);
			} else {
				bm.backActivity(currentActInstID, destActDefID, rollBackStrategy);
			}
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}

	}

	// 查询出当前活动完成后生成下一个活动的工作项.
	public List<WFWorkItem> queryNextWorkItemsByActivityInstID(long actInstID, boolean hasParticipant) {
		try {
			return client.getWorkItemManager().queryNextWorkItemsByActivityInstID(actInstID, hasParticipant);
		} catch (WFServiceException e) {
			throw new MyOwnRuntimeException(e);
		}
	}

	// 根据流程定义ID与活动定义ID查询活动参与者
	public WFActivityDefine getParticipants(long processDefID, String activityDefID) {
		IWFDefinitionQueryManager manager = client.getDefinitionQueryManager();
		try {
			return manager.getActivity(processDefID, activityDefID);
		} catch (WFServiceException e) {
			throw new MyOwnRuntimeException(e);
		}
	}

	// 根据流程定义名称查询所有活动信息
	public List<WFActivityDefine> getActivityDefineByName(String name, boolean hasParticipant) {
		IWFDefinitionQueryManager manager = client.getDefinitionQueryManager();
		try {
			List<WFProcessDefine> list = manager.queryProcessesByName(name);
			if (null==list || list.isEmpty()) {
				throw new MyOwnRuntimeException(CoreConstClass.FLOW_IS_NULL);
			}
			Long processDefID = list.get(0).getProcessDefID();
			List<WFActivityDefine> activities = manager.queryActivitiesOfProcess(processDefID);
			if (hasParticipant) {
				for (WFActivityDefine activity : activities) {
					List<Participant> participants = manager.getActivity(processDefID, activity.getId())
							.getParticipants();
					activity.setParticipants(participants);
				}
			}
			return activities;
		} catch (WFServiceException e) {
			throw new MyOwnRuntimeException(e);
		}
	}

	// 根据活动实列查询后继活动信息 或 根据流程定义名称与活动名称查询活动信息
	public WFActivityDefine getActivityDefineByNameOrID(String name, String activityName,Long actInstID) {
		try {
			if (null != actInstID) {
				List<WFActivityDefine> activityDefines = client.getProcessInstManager().getNextActivitiesMaybeArrived(actInstID);
				if (activityDefines.isEmpty()) {
					throw new MyOwnRuntimeException(CoreConstClass.ACTIVITY_DEFINE_ITEM_IS_NULL);
				} else {
					return activityDefines.get(0);
				}
			}

			IWFDefinitionQueryManager manager = client.getDefinitionQueryManager();
			List<WFProcessDefine> list = manager.queryProcessesByName(name);
			if (null==list || list.isEmpty()) {
				throw new MyOwnRuntimeException(CoreConstClass.FLOW_DEFINE_IS_NULL);
			}
			Long processDefID = list.get(0).getProcessDefID();
			List<WFActivityDefine> activities = manager.queryActivitiesOfProcess(processDefID);
			Optional<WFActivityDefine> first = activities.stream().filter(x -> activityName.equals(x.getName())).findFirst();
			WFActivityDefine wf = first.isPresent()?first.get():null;
			if (wf != null && wf.isSubProcess()) {
				List<WFProcessDefine> subList = manager.queryProcessesByName(wf.getSubProcessDefName());
				Long subProcessDefID = subList.get(0).getProcessDefID();
				List<WFActivityDefine> subActivities = manager.queryActivitiesOfProcess(subProcessDefID);
				List<Participant> participants = new ArrayList<>();
				subActivities.stream().forEach(x -> {
					if (null != x.getParticipants()) {
						participants.addAll(x.getParticipants());
					}
				});
				wf.setParticipants(participants);
			}
			return wf;
		} catch (WFServiceException e) {
			throw new MyOwnRuntimeException(e);
		}
	}

	// 查询指定集合（角色、机构、岗位）的工作项 4.待领取 , 10.运行 , 12.完成 , 13.终结 , 8.挂起
	public List<WFWorkItem> queryCollectiveWorkItems(List<WFParticipant> list, int status, String sortField,
													 boolean includeHistory, PageCond page) {
		try {
			IWFWorklistQueryManager queryManager = client.getWorklistQueryManager();
			return queryManager.queryCollectiveWorkItems(list, status, sortField, includeHistory, page);
		} catch (WFServiceException e) {
			throw new MyOwnRuntimeException(e);
		}

	}

	// 已完成列表
	// •如果第二个参数的值不是ALL(全部)或SELF(自己)、AGENT(代理)、DELEG(代办)、HELP(协办)而且也不是SELF，AGENT，DELEG，HELP的组合，抛出异常。
	public List<WFWorkItem> queryPersonFinishedWorkItems(String personID, String scope, boolean includeHistory,
														 PageCond pageCond) {
		try {
			IWFWorklistQueryManager queryManager = client.getWorklistQueryManager();
			return queryManager.queryPersonFinishedWorkItems(personID, scope, includeHistory, pageCond);
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}

	}

	// 工作项列表
	public List<WFWorkItem> queryPersonWorkItems(String personID, String permission, String scope, PageCond pageCond) {
		IWFWorklistQueryManager queryManager = client.getWorklistQueryManager();
		try {
			return queryManager.queryPersonWorkItems(personID, permission, scope, pageCond);
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}
	}

	// 根据活动实例ID获取后续工作项
	public List<WFWorkItem> getNextWorkItems(long activityInstID) {
		try {
			return client.getWorkItemManager().queryNextWorkItemsByActivityInstID(activityInstID, true);
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}
	}

	public List<WFActivityInst> queryNextActInsts(long activityInstID, boolean isRoute) {
		try {
			IWFActivityInstManager activityInstManager = client.getActivityInstManager();
			List<WFActivityInst> queryNextActInsts = activityInstManager.queryNextActInsts(activityInstID);

			if (null==queryNextActInsts||queryNextActInsts.isEmpty()) {
				throw new MyOwnRuntimeException(CoreConstClass.LIST_EMPTY);
			} else if (isRoute && "route".equals(queryNextActInsts.get(0).getActivityType())) {
				return queryNextActInsts(queryNextActInsts.get(0).getActivityInstID(), isRoute);
			} else {
				return queryNextActInsts;
			}
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}

	}

	/**
	 * 根据流程实例ID，活动定义ID获取 活动实例
	 * @param processInstId
	 * @param activityDefId
	 * @return
	 */
	public WFActivityInst findLastActivityInstByActivityID(Long processInstId, String activityDefId) {
		WFActivityInst wfActivityInst = null;
		try {
			wfActivityInst =  client.getActivityInstManager().findLastActivityInstByActivityID(processInstId, activityDefId);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
		return wfActivityInst;
	}

	/**
	 * 根据流程实例ID，和活动定义ID获取，该活动下工作项第一个工作项
	 * @param processInstId
	 * @param activityDefId
	 * @return
	 */
	public WFWorkItem getWFWorkItemByFlowId(Long processInstId, String activityDefId) {
		try {
			WFActivityInst lastActivityInstByActivityID = findLastActivityInstByActivityID(processInstId, activityDefId);
			if (null == lastActivityInstByActivityID) {
				WFProcessInst wfProcessInst = getParentProcessInst(processInstId);
				lastActivityInstByActivityID = findLastActivityInstByActivityID(wfProcessInst.getProcessInstID(), activityDefId);
			}
			long lastActivityInstID = lastActivityInstByActivityID.getActivityInstID();
			List<WFWorkItem> wfWorkItems = queryWorkItemsByActivityInstID(lastActivityInstID, null);
			return wfWorkItems.get(0);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据流程实例ID，活动定义ID,获取参与者ID
	 * @param processInstId
	 * @param activityDefId
	 * @return
	 */
	public String getActivityInstParticipantByFlowId(Long processInstId, String activityDefId) {
		try {
			WFActivityInst lastActivityInstByActivityID = findLastActivityInstByActivityID(processInstId, activityDefId);
			if (null == lastActivityInstByActivityID) {
				WFProcessInst wfProcessInst = getParentProcessInst(processInstId);
				lastActivityInstByActivityID = findLastActivityInstByActivityID(wfProcessInst.getProcessInstID(), activityDefId);
			}
			long lastActivityInstID = lastActivityInstByActivityID.getActivityInstID();
			List<WFWorkItem> wfWorkItems = queryWorkItemsByActivityInstID(lastActivityInstID, null);
			return wfWorkItems.get(0).getParticipant();
		} catch (Exception e) {
			log.error(ProcessResult.ERROR, e);
			return null;
		}
	}

	/**
	 * 根据子流程实例ID 获取父流程实例ID
	 * @return
	 */
	public WFProcessInst getParentProcessInst(Long subProcessInstId){
		return queryProcessInstDetail(queryProcessInstDetail(subProcessInstId).getParentProcID());
	}

	/**
	 * 根据活动实例ID，返货工作项列表
	 * @param actInstID
	 * @param pageCond
	 * @return
	 */
	public List<WFWorkItem> queryWorkItemsByActivityInstID(long actInstID, PageCond pageCond) {
		List<WFWorkItem> wfWorkItems = null;
		try {
			wfWorkItems = client.getWorkItemManager().queryWorkItemsByActivityInstID(actInstID, pageCond);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
		return wfWorkItems;
	}

	// 根据流程实例ID与活动实例ID获取后续活动参与者
	public List<List<WFParticipant>> getNextActivitiesParticipants(long flowId, long activityInstID) {
		try {
			List<WFActivityDefine> activityDefines = client.getProcessInstManager().getNextActivitiesMaybeArrived(activityInstID);
			List<List<WFParticipant>> list = new ArrayList<>();
			for (WFActivityDefine wfActivityDefine : activityDefines) {
				if (wfActivityDefine.isSubProcess()) {
					List<WFActivityInst> actInsts = queryNextActInsts(activityInstID, true);
					long[] instID = client.getProcessInstManager().querySubProcessInstIDsByActivityInstID(actInsts.get(0).getActivityInstID());
					for (long id : instID) {
						WFProcessInst detail = client.getProcessInstManager().queryProcessInstDetail(instID[0]);
						long processDefID = detail.getProcessDefID();
						IWFDefinitionQueryManager dqm = client.getDefinitionQueryManager();
						WFActivityDefine startActivity = dqm.getStartActivity(processDefID);
						if ("start".equals(startActivity.getType())) {
							WFActivityInst findLastActivity = client.getActivityInstManager().findLastActivityInstByActivityID(instID[0],
									startActivity.getId());
							return getNextActivitiesParticipants(id, findLastActivity.getActivityInstID());
						} else {
							List<WFParticipant> pp = client.getProcessInstManager().getProbableParticipants(flowId, startActivity.getId());
							list.add(pp);
						}
					}
					return list;
				}
				List<WFParticipant> pp = client.getProcessInstManager().getProbableParticipants(flowId, wfActivityDefine.getId());
				list.add(pp);
			}
			return list;
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}
	}

	// 完成第一项工作项返回活动ID
	public WFWorkItem finishFirstWork(long flowId, boolean transactionSpan) {
		try {
			List<WFWorkItem> wfWorkItems1 = client.getWorkItemManager().queryNextWorkItemsByProcessInstID(flowId, false);
			if (null==wfWorkItems1 || wfWorkItems1.isEmpty()) {
				throw new MyOwnRuntimeException(CoreConstClass.WORK_ITEM_IS_NULL);
			}
			client.getWorkItemManager().finishWorkItem(wfWorkItems1.get(0).getWorkItemID(), transactionSpan);
			return wfWorkItems1.get(0);
		} catch (Exception e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
	}

	// 启动流程获取流程实例ID
	public long getFlowId(String defName, String instName, String instDesc) {
		try {
			return client.getProcessInstManager().createAndStartProcessInstance(defName, instName, instDesc);
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}
	}

	// 启动流程设置业务数据返回流程实例ID
	public long getFlowIdAndSetData(String defName, String instName, String instDesc, boolean transcationSpan,
									Map<String, Object> map ) {
		try {
			return client.getProcessInstManager().createAndStartProcInstAndSetRelativeData(defName, instName, instDesc, transcationSpan, map);
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}

	}

	// 启动流程设置业务冗余数据 返回流程实例ID
	public long getFlowIdWithBizInfo(FlowStartVO flowStartVO) {
		this.flowName=flowStartVO.getInstName();
		try {
			long id = client.getProcessInstManager().createProcessInstance(flowStartVO.getDefName(), flowStartVO.getInstName(), flowStartVO.getInstDesc());
			if(null!=flowStartVO.getMap()) {
				client.getRelativeDataManager().setRelativeDataBatch(id, flowStartVO.getMap());
			}
			client.getProcessInstManager().startProcessInstanceWithBizInfo(id, flowStartVO.isTransactionSplit(), flowStartVO.getParams(), flowStartVO.getTableName(), flowStartVO.getBizInfo());
			return id;
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}

	}

	// 获取流程相关数据
	public Object getRelativeData(long processInstID, String paramString) {

		try {
			return client.getRelativeDataManager().getRelativeData(processInstID, paramString);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
	}

	// 修改工作项参与者
	public void updateParticipant(long workItemID, List<WFParticipant> participants) {
		try {
			client.getWorkItemManager().clearWorkItemParticipant(workItemID);
			for (WFParticipant wfParticipant : participants) {
				client.getWorkItemManager().addWorkItemParticipant(workItemID, wfParticipant);
			}
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}

	}	// 修改工作项参与者
	public void reassignWorkItem(long workItemID, List<WFParticipant> participants) {
		try {
			client.getWorkItemManager().reassignWorkItem(workItemID, participants.get(0).getId());
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}

	}

	// 查询角色与部门参与者 typeCode org ,role, orgrole
	public List<WFParticipant> getJointChildParticipant(String typeCode, List<String> participantID) {
		IWFOMService ws = client.getOMService();
		return ws.getJointChildParticipant(typeCode, participantID);

	}

	// 查询角色或部门参与者
	public List<WFParticipant> getAllChildParticipants(String typeCode, String participantID) {
		IWFOMService ws = client.getOMService();
		return ws.getAllChildParticipants(typeCode, participantID);

	}
	// 查询角色或部门参与者
	public List<WFParticipant> getChildParticipants(String typeCode, String participantID,String backCode) {
		IWFOMService ws = client.getOMService();
		return ws.getChildParticipants(typeCode, participantID, backCode);
	}

	public List<WFNotificationInst> queryNotificationsCriteria(State state) {
		IWFNotificationManager nm = client.getNotificationManager();
		try {
			IDASCriteria criteria = DASManager.createCriteria("com.eos.workflow.data.WFNotificationInst");
			criteria.add(ExpressionHelper.eq("state", state));
			return nm.queryNotificationsCriteria(criteria, new PageCond(10));
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
			throw new MyOwnRuntimeException(e);
		}
	}

	public static String[] getStepActivity(String value) {
		String[][] arr = { { "拟稿" }, { "起草部门领导审批", "业务部门会签" }, { "律师审核" }, { "法务专员审核" },
				{ "总部企业发展部领导审批", "省份综合部领导审批", "地市综合部领导审批" }, { "公司领导审批" }, { "承办人确认" } };
		boolean flag = false;
		for (String[] strings : arr) {
			if (flag) {
				return strings;
			}
			for (String strings2 : strings) {
				if (strings2.equals(value)) {
					flag = true;
					break;
				}
			}
		}
		return new String[0];
	}


	public IBPSServiceClient getClient() {
		return client;
	}

	public void openTransaction() {
		globalTxMgr.begin();
	}

	// 提交事物
	public void clientCommit(boolean isClose) {
		if (null != client && onTransaction) {
			globalTxMgr.commit();
		}
		if (isClose) {
			client = null;
		}
	}

	// 回滚事物
	public void clientBack(boolean isClose) {
		if (null != client && onTransaction) {
			globalTxMgr.rollback();
			client = null;
		}
		if (isClose) {
			client = null;
		}
	}

	public void deleteProcessInst(Long processInstId) {
		try {
			client.getProcessInstManager().deleteProcessInstance(processInstId);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
		}
	}


	//4.待领取 , 10.运行 , 12.完成 , 13.终结 , 8.挂起
	public String getWorkStatus(int value) {
		switch (value) {
			case 4:
				return "待领取";
			case 8:
				return "挂起";
			case 10:
				return "运行 ";
			case 12:
				return "完成";
			case 13:
				return "终结";
			default:
				return "";
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public Long getActivityInstID() {
		return activityInstID;
	}

	public void setActivityInstID(Long activityInstID) {
		this.activityInstID = activityInstID;
	}


	public List<WFProcessDefine> queryProcessesByName(String flowDefName) {
		IWFDefinitionQueryManager iwfDefinitionQueryManager = client.getDefinitionQueryManager();
		try {
			return iwfDefinitionQueryManager.queryProcessesByName(flowDefName);
		} catch (WFServiceException e) {
			log.error(ProcessResult.ERROR, e);
		}
		return new ArrayList<>();
	}

	public FlowUtil() {
		super();
	}
	public void init(String userId, String userName, String tenantId, String token, boolean onTransaction) {
		this.onTransaction = onTransaction;
		this.userId = userId;
		this.userName = userName;
		this.tenantId = tenantId;
		BPSLoginManager loginManager = BPSServiceClientFactory.getLoginManager();
		loginManager.setCurrentUser(userId, userName, tenantId, token);
		// 开启事物


		try {
			client = BPSServiceClientFactory.getClient(formName);
			if (onTransaction) {
				globalTxMgr = client.getClientGlobalTransactionManager();
				globalTxMgr.begin();
			}
			log.info("流程当前登陆人名称={},Id={}",userName,userId);
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}
	}
	public void init(String flowName, String userId, String userName, String tenantId, String token, boolean onTransaction) {
		this.flowName = flowName;
		this.onTransaction = onTransaction;
		this.userId = userId;
		this.userName = userName;
		this.tenantId = tenantId;
		BPSLoginManager loginManager = BPSServiceClientFactory.getLoginManager();

		loginManager.setCurrentUser(userId, userName, tenantId, token);
		// 开启事物
		try {
			client = BPSServiceClientFactory.getClient(formName);
			if (onTransaction) {
				globalTxMgr = client.getClientGlobalTransactionManager();
				globalTxMgr.begin();
			}
			log.info("流程当前登陆人名称={},Id={}",userName,userId);
		} catch (Exception e) {
			throw new MyOwnRuntimeException(e);
		}
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}


	public void setProcessInstName(String title, long flowId) throws WFServiceException {
		Map<String, Object> map = new HashMap<>();
		map.put("name", title);
		client.getProcessInstManager().setProcessInstAttributeBatch(flowId, map);
	}

	public List<WFActivityInst> queryActivityInstsByActivityID(Long flowId, String activityDefId, PageCond pageCond) {
		IWFActivityInstManager activityInstManager = client.getActivityInstManager();
		try {
			return activityInstManager.queryActivityInstsByActivityID(flowId, activityDefId, pageCond);
		} catch (WFServiceException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
