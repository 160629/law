package com.chinatower.product.legalms.modules.flow.service.unview.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.framework.mq.kafka.api.KafkaUtils;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.CoreConstClass;
import com.chinatower.product.legalms.common.FlowScheduledProperties;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.sms.TPubSmsTxd;
import com.chinatower.product.legalms.modules.flow.entity.unview.TFlowUnview;
import com.chinatower.product.legalms.modules.flow.mapper.unview.TFlowUnviewCommonMapper;
import com.chinatower.product.legalms.modules.flow.service.common.OrgBeanService;
import com.chinatower.product.legalms.modules.flow.service.common.UserInfoService;
import com.chinatower.product.legalms.modules.flow.service.sms.SmsService;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.chinatower.product.legalms.modules.flow.vo.sms.SmsProperties;
import com.chinatower.product.legalms.modules.flow.vo.unview.AutoView;
import com.chinatower.product.legalms.modules.flow.vo.unview.TFlowUnviewConfigVO;
import com.chinatower.product.legalms.vo.CacheModel;
import com.chinatower.provider.call.oauth.RedisServiceClient;
import com.eos.workflow.data.WFActivityInst;
import com.eos.workflow.data.WFWorkItem;
import com.eos.workflow.omservice.WFParticipant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.weaver.ofs.webservices.OfsTodoDataWebServiceHttpBindingStub;
import cn.com.weaver.ofs.webservices.OfsTodoDataWebServiceLocator;
import cn.com.weaver.ofs.webservices.vo.UnviewPushVO;

@Service
public class TFlowCommonServiceImpl implements TFlowCommonService {
	private static final Logger logger = LoggerFactory.getLogger(TFlowCommonServiceImpl.class);
	@Autowired
	TFlowUnviewCommonMapper unviewMapper;
	@Autowired
	FlowScheduledProperties properties;
	@Autowired
	SmsProperties smsProperties;
	@Autowired
	RedisServiceClient redisServiceClient;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	OrgBeanService orgBeanService;

	@Autowired
	private KafkaUtils kafkaUtils;

	@Override
	public int autoUnView(String shortFlowName, String title, String id, WFParticipant login,
			List<WFParticipant> toers) {
		AutoView view = new AutoView(shortFlowName, title, id, login, toers);
		return autoUnView(view);
	}

	@Override
	public int autoUnView(String shortFlowName, WFParticipant login, List<WFParticipant> toers, Integer caseNum,
			String cause) {
		AutoView view = new AutoView(shortFlowName, login, toers, caseNum, cause);
		return autoUnView(view);
	}

	@Override
	public int autoUnView(String shortFlowName, WFParticipant login, List<WFParticipant> toers, String startTime,
			String endTime, Integer configOrder) {
		AutoView autoView = new AutoView(shortFlowName, login, toers, configOrder, startTime, endTime);
		return autoUnView(autoView);
	}

	@Override
	public int autoUnView(AutoView autoView) {
		return autoUnView(autoView, null);
	}

	@Override
	public int autoUnView(AutoView autoView, UserInfo info) {
		logger.info("TFlowServiceImpl生成待阅参数id:{}=====autoView:{}=====info:{}", autoView.getId(), autoView, info);
		List<WFParticipant> toers = autoView.getToers();
		String shortFlowName = autoView.getShortFlowName();
		int last = StringUtils.ordinalIndexOf(shortFlowName, ".", 4);
		last = last > 0 ? last : shortFlowName.length();
		shortFlowName = shortFlowName.substring(0, last);
		Integer configOrder = autoView.getConfigOrder();
		if (StringUtil.isEmpty(shortFlowName) || null == toers || toers.isEmpty()) {
			return 0;
		}
		List<TFlowUnviewConfigVO> sumList = unviewMapper.selectByType(shortFlowName, configOrder);
		List<TFlowUnviewConfigVO> list0 = sumList.stream().filter(x -> x.getConfigSendType() == 0)
				.collect(Collectors.toList());

		List<TFlowUnview> views = new ArrayList<>();
		if (!list0.isEmpty()) {
			views.addAll(autoUnViewZero(autoView, list0));
		}
		List<TFlowUnviewConfigVO> list1 = sumList.stream().filter(x -> x.getConfigSendType() == 1)
				.collect(Collectors.toList());
		if (!list1.isEmpty()) {
			views.addAll(autoUnViewOne(autoView, list1, info));
		}
		List<TFlowUnviewConfigVO> list2 = sumList.stream().filter(x -> x.getConfigSendType() == 2)
				.collect(Collectors.toList());
		if (!list2.isEmpty()) {
			views.addAll(autoUnViewTwo(autoView, list2, info));
		}
		if (views.isEmpty()) {
			return 0;
		}
		List<TFlowUnview> distinctUnview = distinctUnview(views);
		unviewMapper.insertBatch(distinctUnview);
		try {
			String lawKafka = getLawKafka();
			smsSend(sumList.get(0), distinctUnview, lawKafka);
			unviewPush(distinctUnview, lawKafka);
		} catch (Exception e) {
			logger.info("待阅OA推送或短信发送异常-------{}", e);
		}
		return 1;
	}

	public List<TFlowUnview> distinctUnview(List<TFlowUnview> views) {
		List<TFlowUnview> views1 = new ArrayList<>();
		views.stream().collect(Collectors.groupingBy(TFlowUnview::getViewTitle)).values().forEach(x -> views1
				.addAll(x.stream().filter(distinctByKey(TFlowUnview::getToerId)).collect(Collectors.toList()))

		);
		return views1;
	}

	public <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	public List<TFlowUnview> autoUnViewOne(AutoView autoView, List<TFlowUnviewConfigVO> list, UserInfo info) {

		List<TFlowUnview> views = new ArrayList<>();
		String shortFlowName = autoView.getShortFlowName().replace(".mrg.", ".");
		for (TFlowUnviewConfigVO config : list) {
			Integer order = config.getConfigOrder();
			String[] split = config.getConfigRoles().split(",");
			List<AccountLogic> list2 = null;
			if (!shortFlowName.startsWith(config.getShortFlowName())) {
				continue;
			}
			if (order == 11) {
				list2 = userInfoService.selectUserByRoleCode(Arrays.asList(split), info, "02");
			} else if (order == 21 || order == 12) {
				list2 = userInfoService.selectUserByRoleCode(Arrays.asList(split), info, "01");
			} else if (order == 10) {
				WFParticipant part = autoView.getToers().get(0);
				list2 = userInfoService.selectUserInfoByOrgAndUserCode(part.getId(), config.getConfigRoles());
			}
			if (null != list2) {
				List<String> collect = list2.stream().map(AccountLogic::getAccountName).collect(Collectors.toList());
				logger.info("autoUnViewOne发送collect:{}", collect);
				getUnviewOne(autoView, views, info, config, list2);
			}

		}
		logger.info("autoUnViewOne生成待阅条数-----views.size()={}", views.size());
		return views;
	}

	public int getUnviewOne(AutoView autoView, List<TFlowUnview> views, UserInfo info, TFlowUnviewConfigVO config,
			List<AccountLogic> list2) {
		logger.info("getUnviewOne生成待阅参数-----autoView={},-----views={},-----config={}", autoView, views, config);
		String shortFlowName = config.getShortFlowName();
		String title = autoView.getTitle();
		Integer order = config.getConfigOrder();
		String stateName = "";
		if (order == 12) {
			stateName = orgBeanService
					.selectProOrgInfoByOrgCode(RequestHolder.getProvinceCode(autoView.getPutLoginDept())).getOrgName();
		}
		String viewTitle = null;
		String viewComent = null;
		if ((shortFlowName.equals(CoreConstClass.FLOW_GUIDE_CITY)
				|| shortFlowName.equals(CoreConstClass.FLOW_LAWSUIT_CITY)) && order == 11) {
			viewTitle = MessageFormat.format(config.getTitle(), autoView.getCityOrgName());
			viewComent = MessageFormat.format(config.getComent(), autoView.getCityOrgName(), autoView.getDeptName(),
					autoView.getPutLoginName(), title);
		} else if ((shortFlowName.equals(CoreConstClass.FLOW_GUIDE_CITY)
				|| shortFlowName.equals(CoreConstClass.FLOW_LAWSUIT_CITY)) && order == 12) {
			viewTitle = MessageFormat.format(config.getTitle(), stateName, autoView.getCityOrgName());
			viewComent = MessageFormat.format(config.getComent(), stateName, autoView.getCityOrgName(),
					autoView.getDeptName(), autoView.getPutLoginName(), title);
		} else if (shortFlowName.equals(CoreConstClass.FLOW_GUIDE_STATE)
				|| shortFlowName.equals(CoreConstClass.FLOW_LAWSUIT_STATE)) {
			viewTitle = MessageFormat.format(config.getTitle(), autoView.getStateOrgName());
			viewComent = MessageFormat.format(config.getComent(), autoView.getStateOrgName(), autoView.getDeptName(),
					autoView.getPutLoginName(), title);
		} else if (shortFlowName.equals(CoreConstClass.FLOW_LAWCASE)) {
			viewTitle = MessageFormat.format(config.getTitle(), title);
			viewComent = MessageFormat.format(config.getComent(), title);
		} else {
			return 0;
		}

		String viewUrl = MessageFormat.format(config.getConfigUrl(), autoView.getId());
		for (int i = 0; i < list2.size() && null != viewTitle; i++) {
			AccountLogic logic = list2.get(i);
			TFlowUnview record = new TFlowUnview(StringUtil.getId(), config.getConfigName(), logic.getAccountId(),
					logic.getAccountName(), config.getConfigUrlName(), info.getLoginAcct(), info.getLoginName());
			record.setViewUrl(viewUrl);
			record.setViewTitle(viewTitle);
			record.setViewComent(viewComent);
			record.setOpenType(config.getOpenType());
			record.setApproveItemId(autoView.getId());
			views.add(record);
		}
		return 1;
	}

	public List<TFlowUnview> autoUnViewTwo(AutoView autoView, List<TFlowUnviewConfigVO> list, UserInfo info) {
		List<TFlowUnview> unviewTwo = getUnviewTwo(autoView, list, info);
		logger.info("autoUnViewTwo生成待阅条数-----unviewTwo.size()={}", unviewTwo.size());
		return unviewTwo;

	}

	public List<TFlowUnview> getUnviewTwo(AutoView autoView, List<TFlowUnviewConfigVO> list, UserInfo info) {
		logger.info("getUnviewTwo生成待阅参数-----autoView={},-----list={},-----info={}", autoView, list, info);
		String[] involvedDeptId = { autoView.getInvolvedDeptId() };
		List<TFlowUnview> views = new ArrayList<>();
		for (TFlowUnviewConfigVO config : list) {
			String[] split2 = config.getConfigRoles().split(",");
			List<AccountLogic> list2 = userInfoService.selectCopyUserByRoleAndOrgCode(Arrays.asList(split2),
					Arrays.asList(involvedDeptId));
			String viewTitle = MessageFormat.format(config.getTitle(), autoView.getTitle());
			String viewComent = MessageFormat.format(config.getComent(), autoView.getAssignOrgName(),
					autoView.getSeedOrgName(), autoView.getTitle());
			String viewUrl = MessageFormat.format(config.getConfigUrl(), autoView.getId());
			if (!StringUtil.isEmpty(autoView.getInvolvedAccountId())) {
				AccountLogic logic1 = new AccountLogic(autoView.getInvolvedAccountId(),
						autoView.getInvolvedAccountName());
				list2.add(logic1);
			}
			for (AccountLogic logic : list2) {
				TFlowUnview record = new TFlowUnview(StringUtil.getId(), config.getConfigName(), logic.getAccountId(),
						logic.getAccountName(), config.getConfigUrlName(), info.getLoginAcct(), info.getLoginName());
				record.setViewUrl(viewUrl);
				record.setViewTitle(viewTitle);
				record.setViewComent(viewComent);
				record.setOpenType(config.getOpenType());
				record.setApproveItemId(autoView.getId());
				views.add(record);
			}
		}
		return views;

	}

	public List<TFlowUnview> autoUnViewZero(AutoView autoView, List<TFlowUnviewConfigVO> list) {
		logger.info("autoUnViewZero生成待阅参数-----autoView={},-----list={}", autoView, list);
		List<WFParticipant> toers = autoView.getToers();
		String shortFlowName = autoView.getShortFlowName();
		List<TFlowUnview> views = new ArrayList<>();
		if (StringUtil.isEmpty(shortFlowName) || null == toers || toers.isEmpty()) {
			return views;
		}
		Map<String, Object> caseMap = new HashMap<>();
		// Integer lawsuitOrder = null;verifyAutoUnView(shortFlowName, id, caseMap,
		// list, toers);
		if (null == list || list.size() != toers.size()) {
			logger.info("待阅配置参数为空list:{}", list);
			return views;
		}

		for (int i = 0; i < toers.size(); i++) {
			TFlowUnview unview = getUnviewZero(autoView, list.get(i), toers.get(i), caseMap);
			if (null != unview) {
				views.add(unview);
			}
		}
		logger.info("autoUnViewZero生成待阅条数-----views.size()={}", views.size());
		return views;
	}

	/*
	 * public Integer verifyAutoUnView(String shortFlowName, String id, Map<String,
	 * Object> caseMap, List<TFlowUnviewConfigVO> list, List<WFParticipant> toers) {
	 * Integer lawsuitOrder = null; String caseId = null; if
	 * (shortFlowName.startsWith(CoreConstClass.FLOW_LAWSUIT)) { caseId =
	 * unviewMapper.selectLawsuitCaseId(id); } else if
	 * (shortFlowName.startsWith(CoreConstClass.FLOW_LEGISLATION)) { caseId =
	 * unviewMapper.selectLegislationCaseId(id); } else if
	 * (shortFlowName.startsWith(CoreConstClass.FLOW_JOINLY)) { caseId =
	 * unviewMapper.selectjointlyCaseId(id); } if
	 * (shortFlowName.startsWith(CoreConstClass.FLOW_LAWSUIT) &&
	 * StringUtil.isEmpty(caseId)) { list.remove(list.size() - 1); lawsuitOrder = 2;
	 * } else if (null != caseId && toers.size() > 1) { caseMap =
	 * unviewMapper.selectCase(caseId); WFParticipant part = toers.get(1);
	 * part.setId(caseMap.get("creator_account_id") + "");
	 * part.setName(caseMap.get("creator_account_name") + ""); } return
	 * lawsuitOrder; }
	 */

	public TFlowUnview getUnviewZero(AutoView autoView, TFlowUnviewConfigVO config, WFParticipant toer,
			Map<String, Object> caseMap) {
		WFParticipant login = autoView.getLogin();
		Integer configOrder = autoView.getConfigOrder();
		String shortFlowName = autoView.getShortFlowName();
		String title = autoView.getTitle();
		String endTime = autoView.getEndTime();
		MessageFormat.format(config.getTitle(), login.getName());
		TFlowUnview record = new TFlowUnview(StringUtil.getId(), config.getConfigName(), toer.getId(), toer.getName(),
				config.getConfigUrlName(), login.getId(), login.getName());
		record.setOpenType(config.getOpenType());
		record.setApproveItemId(autoView.getId());
		configOrder = null == configOrder ? config.getConfigOrder() : configOrder;
		if (shortFlowName.equals("t_flow_delegate") && configOrder == 1) {
			String format = MessageFormat.format(config.getTitle(), login.getName());
			record.setViewTitle(format);
			String format1 = MessageFormat.format(config.getComent(), login.getName(), autoView.getStartTime(),
					endTime);
			record.setViewComent(format1);
		} else if (shortFlowName.equals("t_flow_delegate") && configOrder == 2) {
			String format = MessageFormat.format(config.getTitle(), login.getName());
			record.setViewTitle(format);
			String format1 = MessageFormat.format(config.getComent(), login.getName(), endTime);
			record.setViewComent(format1);
		} else if (shortFlowName.equals("t_case_transfer")) {
			record.setViewTitle(config.getTitle());
			String format1 = MessageFormat.format(config.getComent(), login.getName(), autoView.getCaseNum(),
					autoView.getCause());
			record.setViewComent(format1);
			record.setViewUrl(config.getConfigUrl());
		} else if (shortFlowName.startsWith(CoreConstClass.FLOW_LAWSUIT) && configOrder == 2) {
			String format = MessageFormat.format(config.getTitle(), title);
			record.setViewTitle(format);
			String format1 = MessageFormat.format(config.getComent(), title);
			record.setViewComent(format1);
			record.setViewUrl(config.getConfigUrl());
		} else if (shortFlowName.startsWith(CoreConstClass.FLOW_LAWSUIT) && configOrder == 3) {
			String format = MessageFormat.format(config.getTitle(), caseMap.get("case_title"));
			record.setViewTitle(format);
			String format1 = MessageFormat.format(config.getComent(), caseMap.get("case_title"), title);
			record.setViewComent(format1);
			record.setViewUrl(config.getConfigUrl());
		} else if (shortFlowName.startsWith("com.tower")) {
			String format = MessageFormat.format(config.getTitle(), title);
			record.setViewTitle(format);
			String format1 = MessageFormat.format(config.getComent(), title);
			record.setViewComent(format1);
			String format2 = MessageFormat.format(config.getConfigUrl(), autoView.getId());
			record.setViewUrl(format2);
		} else {
			return null;
		}
		return record;
	}

	@Override
	public void delegateWorkItem(FlowUtil flowUtil, WFActivityInst lastActivityInstByActivityID) {
		List<WFWorkItem> wfWorkItems = flowUtil
				.queryNextWorkItemsByActivityInstID(lastActivityInstByActivityID.getActivityInstID(), false);
		for (int j = 0; wfWorkItems != null && j < wfWorkItems.size(); j++) {
			TFlowDelegate delegate = verifyDelegate(wfWorkItems.get(j).getParticipant());
			if (null != delegate) {
				logger.info("delegateWorkItem方法进入:{}", lastActivityInstByActivityID.getActivityInstID());
				flowUtil.init(wfWorkItems.get(j).getParticipant(), wfWorkItems.get(j).getPartiName(),
						CoreConstClass.TENANLID, null, true);
				flowUtil.delegateWorkItem(delegate.getToerId(), delegate.getToerName(),
						wfWorkItems.get(j).getWorkItemID());
				flowUtil.clientCommit(true);
			}
		}
	}

	private void addUndone(WFParticipant participant) {
		CacheModel model = new CacheModel().setKey(CoreConstClass.REDIS_SIGN_UNDONE);
		Map<String, Object> query = redisServiceClient.query(model);
		if (null == query) {
			logger.info("redis数据query==", query);
		} else {
			Object data = query.get("data");
			ArrayList<String> list = null == data ? new ArrayList<>() : (ArrayList) data;
			String id = participant.getId();
			if (!list.contains(id))
				list.add(id);
			model.setValue(list);
			Map<String, Object> result = null == data ? redisServiceClient.insert(model)
					: redisServiceClient.update(model);
			logger.info(result.toString());
		}
	}

	private void addOrgsUndone(List<OrgParticipantVO> orgs) {
		CacheModel model = new CacheModel().setKey(CoreConstClass.REDIS_SIGN_UNDONE);
		Map<String, Object> query = redisServiceClient.query(model);
		if (null == query) {
			logger.info("redis数据query==", query);
		} else {
			Object data = query.get("data");
			ArrayList<String> list = null == data ? new ArrayList<>() : (ArrayList) data;
			for (OrgParticipantVO pvo : orgs) {
				String id = pvo.getDepaInterPersonP().getId();
				if (!list.contains(id))
					list.add(pvo.getDepaInterPersonP().getId());
			}
			model.setValue(list);
			Map<String, Object> result = null == data ? redisServiceClient.insert(model)
					: redisServiceClient.update(model);
			logger.info(result.toString());
		}

	}

	@Override
	public void setApprovalAccountId(List<OrgParticipantVO> orgs, WFParticipant participant) {
		if (null != orgs && !orgs.isEmpty()) {
			addOrgsUndone(orgs);
		} else {
			addUndone(participant);
		}
	}

	@Override
	public TFlowDelegate verifyDelegate(String loginAcct) {
		logger.info("TFlowServiceImpl验证是否委托方法参数=====loginAcct:{}", loginAcct);
		if (StringUtil.isEmpty(loginAcct)) {
			return null;
		}
		TFlowDelegate tFlowDelegate = new TFlowDelegate(loginAcct);
		List<TFlowDelegate> tFlowDelegates = selectDelegates(tFlowDelegate);
		if (tFlowDelegates != null && !tFlowDelegates.isEmpty()) {
			return tFlowDelegates.get(0);
		}
		return null;
	}

	@Override
	public List<TFlowDelegate> selectDelegates(TFlowDelegate record) {

		List<TFlowDelegate> selectAll = unviewMapper.selectDelegates(record);
		ArrayList<TFlowDelegate> list1 = new ArrayList<>();
		for (TFlowDelegate td : selectAll) {
			long time = new Date().getTime();
			if (null != td.getStartTime() && "17".equals(td.getDelegateStatus())
					&& td.getStartTime().getTime() < time) {
				list1.add(td.setDelegateStatus("18"));
			} else if (null != td.getEndTime() && "18".equals(td.getDelegateStatus())
					&& td.getEndTime().getTime() < time) {
				list1.add(td.setDelegateStatus("19"));
			}
		}
		if (!list1.isEmpty()) {
			int i = 0;
			for (TFlowDelegate tFlowDelegate : list1) {
				i += unviewMapper.updateDelegateStatusByPrimaryKey(tFlowDelegate);
			}
			selectAll = selectDelegates(record);
			logger.info("委托待办修改数据：", i);
		}
		return selectAll;
	}

	public String getRoleLevel(List<Object> roleCodeList) {
		String[] selectAllRoleList = { "CHNTLEGALMS_13", // 法务初审人（法务专员）
				"CHNTLEGALMS_17", // 法务审核人（法务人员）
				"CHNTLEGALMS_33", // 法务部门负责人
				"CHNTLEGALMS_23", // 董事长
				"CHNTLEGALMS_39", // 公司总经理
				"CHNTLEGALMS_7", // 系统管理员
				"CHNTLEGALMS_16", // 地市法务接口人
				"CHNTLEGALMS_36",// 法务部门分管领导

		};
		List<String> list = Arrays.asList(selectAllRoleList);
		String role;
		if (roleCodeList.contains("CHNTLEGALMS_0")) {// 超级管理员
			return "whole";
		}
		for (int i = 0; i < roleCodeList.size(); i++) {
			role = (String) roleCodeList.get(i);
			if (list.contains(role)) {
				return "all";
			}
		}

//		if (roleCodeList.contains("CHNTLEGALMS_36")) {// 分管领导
//			return "fgld";
//		}
		if (roleCodeList.contains("CHNTLEGALMS_43") || list.contains("CHNTLEGALMS_24")) {// 部门人员
			return "bmry";
		}

		return "mr";
	}

	@Override
	public Map<String, Object> test() {
		return unviewMapper.test();
	}

	@Override
	public int updateByPrimaryKeyStatus(String itemType, String itemId, String itemName, String itemStatus) {

		logger.info("updateByPrimaryKeyStatus修改方法参数" + "=====itemType:{},itemId:{},itemName:{},itemStatus:{}", itemType,
				itemId, itemName, itemStatus);
		Map<String, String> map = new HashMap<>();
		map.put("t_issue_lawsuit", "lawsuit_status");
		map.put("t_case_assign", "assign_status");
		map.put("t_issue_consult", "state");
		map.put("t_case_legislation", "state");
		map.put("t_issue_jointly", "jointly_status");
		map.put("t_issue_assist", "state");
		String itemStatusName = map.get(itemType);
		if (StringUtil.strsEmpty(itemType, itemId, itemName, itemStatusName, itemStatus)) {
			return 0;
		}
		return unviewMapper.updateByPrimaryKeyStatus(itemType, itemId, itemName, itemStatusName, itemStatus);

	}

	public int smsSend(TFlowUnviewConfigVO configVO, List<TFlowUnview> temps, String lawKafka) {
		if (null == configVO || null == temps || temps.isEmpty() || StringUtil.isEmpty(configVO.getStatus())
				|| "1".equals(configVO.getStatus())) {
			return 0;
		}
		logger.info("待阅发送短信开始temps.size:{}", temps.size());
		List<TPubSmsTxd> contents = new ArrayList<>();
		for (TFlowUnview unview : temps) {
			AccountLogic account = userInfoService.selectUserInfo(unview.getToerId());
			String phone = account.getMobile();
			if ("Y".equals(configVO.getIsDefault())) {
				phone = configVO.getRemark();
			}
			if (StringUtil.isEmpty(phone)) {
				continue;
			}
			TPubSmsTxd txd = new TPubSmsTxd(phone, unview.getViewTitle(), configVO.getConfigName(),unview.getToerId(),unview.getToerName());
			contents.add(txd);
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			String txt3;
			if (contents.isEmpty()) {
				txt3 = "contents.isEmpty";
			} else if ("1".equals(lawKafka)) {
				Map<String, Object> map = new HashMap<>();
				map.put("topic", "unview");
				map.put("data", contents);
				String paramJson = mapper.writeValueAsString(map);
				txt3 = kafkaUtils.asyncSend(CoreConstClass.LAW_TOPIC, paramJson);
			} else {
				txt3 = txt3(contents);
			}
			logger.info("待阅发送短信工作结果{}", txt3);
		} catch (Exception e) {
			logger.info("待阅发送短信工作异常", e);
			return 0;
		}
		return 1;

	}

	@Autowired
	SmsService smsService;

	@Override
	public String txt1(TPubSmsTxd bySendId) {
		return smsService.sendsms(bySendId);
	}

	@Override
	public String txt2(List<TPubSmsTxd> contents) {
		for (TPubSmsTxd td : contents) {
			String content = MessageFormat.format(CoreConstClass.CONTENT_TASKS,td.getBusinessType(),
					td.getBusinessId());
			td.setContent(content);
			smsService.sendsms(td);
		}
		return "2";

	}

	@Override
	public String txt3(List<TPubSmsTxd> contents) {

		for (TPubSmsTxd td : contents) {
			String content = MessageFormat.format(CoreConstClass.CONTENT_UNVIEW, td.getBusinessId());
			td.setContent(content);
			smsService.sendsms(td);
		}
		return "3";
	}

	public String unviewPush1(List<UnviewPushVO> arrayList, ObjectMapper objectMapper) {
		OfsTodoDataWebServiceHttpBindingStub service = getService();
		try {
			for (UnviewPushVO un : arrayList) {
				String string = objectMapper.writeValueAsString(un);
				logger.info("待阅OA推送参数==={}", string);
				String json = service.receiveTodoRequestByJson(string);
				logger.info("待阅OA推送结果==={}", json);
				HashMap<String, String> map = objectMapper.readValue(json,
						new TypeReference<HashMap<String, String>>() {
						});
				if ("1".equals(map.get("operResult"))) {
					unviewMapper.updatePushFlag(un.getFlowid(), "1");
				} else if ("0".equals(map.get("operResult"))) {
					unviewMapper.updatePushFlag(un.getFlowid(), un.getPushFlag() + "0");
				}
			}
		} catch (Exception e) {
			logger.info("unviewPush1待阅OA推送异常==={}", e);
		}
		return ProcessResult.SUCCESS;

	}

	@Override
	public String unviewPush(List<TFlowUnview> temps, String lawKafka) {
		String params = properties.getUnviewUrl();
		List<UnviewPushVO> arrayList = new ArrayList<>();
		for (TFlowUnview un : temps) {
			String flag = un.getPushFlag();
			flag = StringUtil.isEmpty(flag) ? "0" : flag;
			String toerId = un.getToerId();
			String userId = toerId.split("_")[0];
			String viewUrl = params + un.getViewId() + "&userId=" + userId + "&appAcctId=" + toerId;
			UnviewPushVO vo = new UnviewPushVO(un.getViewId(), un.getViewTitle(), un.getViewType(), un.getViewType(),
					un.getLoginAcct(), un.getToerId());
			vo.setPcurl(viewUrl);
			vo.setCreatedatetime(new Date());
			vo.setPushFlag(flag);
			arrayList.add(vo);
		}
		String txt3;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			if (arrayList.isEmpty()) {
				txt3 = "arrayList.isEmpty";
			} else if ("1".equals(lawKafka)) {
				Map<String, Object> map = new HashMap<>();
				map.put("topic", "unviewPush");
				map.put("data", arrayList);
				String paramJson = objectMapper.writeValueAsString(map);
				txt3 = kafkaUtils.asyncSend(CoreConstClass.LAW_TOPIC, paramJson);
			} else {
				txt3 = unviewPush1(arrayList, objectMapper);
			}
			logger.info("unviewPush待阅OA推送工作结果{}", txt3);
		} catch (Exception e) {
			logger.info("unviewPush待阅OA推送异常==={}", e);
		}
		return ProcessResult.SUCCESS;
	}

	public String unviewDonePush1(List<UnviewPushVO> arrayList, ObjectMapper objectMapper) {
		OfsTodoDataWebServiceHttpBindingStub service = getService();
		try {
			for (UnviewPushVO vo : arrayList) {
				String viewId = vo.getFlowid();
				String flag = vo.getPushFlag();
				String string = objectMapper.writeValueAsString(vo);
				logger.info("待阅OA修改推送参数==={}", string);
				String json = service.processDoneRequestByJson(string);
				logger.info("待阅OA修改推送结果==={}", json);
				HashMap<String, String> map = objectMapper.readValue(json,
						new TypeReference<HashMap<String, String>>() {
						});
				if ("0".equals(map.get("operResult"))) {
					if (map.get("message").endsWith("不存在")) {
						logger.info("待阅OA修改推送不存在");
						unviewMapper.updatePushFlag(viewId, "111");
					} else {
						unviewMapper.updatePushFlag(viewId, flag + "0");
					}
				} else {
					unviewMapper.updatePushFlag(viewId, "1");
				}
			}
		} catch (Exception e) {
			logger.info("unviewDonePush1待阅OA修改推送异常==={}", e);
		}
		return ProcessResult.SUCCESS;
	}

	@Override
	public String unviewDonePush(List<TFlowUnview> list) {
		List<UnviewPushVO> arrayList = new ArrayList<>();
		for (TFlowUnview temp : list) {
			String flag = temp.getPushFlag();
			flag = StringUtil.isEmpty(flag) ? "0" : flag;
			String viewId = temp.getViewId();
			UnviewPushVO vo = new UnviewPushVO(viewId, temp.getViewTitle(), temp.getViewType(), temp.getViewType(),
					temp.getToerId());
			vo.setPushFlag(flag);
			arrayList.add(vo);
		}
		String txt3;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String lawKafka = getLawKafka();
			if (arrayList.isEmpty()) {
				txt3 = "arrayList.isEmpty";
			} else if ("1".equals(lawKafka)) {
				Map<String, Object> map = new HashMap<>();
				map.put("topic", "unviewDonePush");
				map.put("data", arrayList);
				String paramJson = objectMapper.writeValueAsString(map);
				txt3 = kafkaUtils.asyncSend(CoreConstClass.LAW_TOPIC, paramJson);
			} else {
				txt3 = unviewDonePush1(arrayList, objectMapper);
			}
			logger.info("unviewDonePush待阅OA修改推送工作结果{}", txt3);
		} catch (Exception e) {
			logger.info("unviewDonePush待阅OA修改推送异常==={}", e);
		}
		return ProcessResult.SUCCESS;
	}

	@Override
	public String unviewDonePushPre(List<String> viewIds) {
		List<TFlowUnview> list = unviewMapper.selectByViewIds(viewIds);
		unviewDonePush(list);
		return ProcessResult.SUCCESS;
	}

	public OfsTodoDataWebServiceHttpBindingStub getService() {
		OfsTodoDataWebServiceLocator si = new OfsTodoDataWebServiceLocator();
		OfsTodoDataWebServiceHttpBindingStub service = null;
		String url = smsProperties.getWebServiceUrl();
		try {
			service = (OfsTodoDataWebServiceHttpBindingStub) si.getPort(OfsTodoDataWebServiceHttpBindingStub.class,
					url);
		} catch (ServiceException e) {
			logger.info("url={}--------------getService异常-----{}", url, e);
		}
		return service;
	}

	@Override
	public void unviewDonePushRetry(List<TFlowUnview> list1) {
			try {
				unviewDonePush(list1);
			} catch (Exception e) {
				logger.info("待阅重新OA修改推送异常==={}", e);
			}
/*		logger.info("待阅重新OA修改迭代推送------------------------------list1.size={}", list1.size());
		if (!list1.isEmpty()) {
			try {
				unviewPush(list1, lawKafka);
				unviewDonePushRetry(list1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
	}

	@Override
	public List<TFlowUnview> pushSelectList(String viewStatus, String pushFlag) {
		return unviewMapper.pushSelectList(viewStatus, pushFlag);
	}

	@Override
	public void updateLawKafka(String status) {
		int i = unviewMapper.updateLawKafka(status);
		CacheModel model = new CacheModel().setKey(CoreConstClass.REDIS_SIGN_KAFKA);
		Map<String, Object> query = redisServiceClient.query(model);
		if (i > 0) {
			if (null == query) {
				logger.info("updateLawKafka数据query==", query);
			} else {
				Object data = query.get("data");
				model.setValue(status);
				Map<String, Object> result = null == data ? redisServiceClient.insert(model)
						: redisServiceClient.update(model);
				logger.info(result.toString());
			}
		}
	}

	@Override
	public String getLawKafka() {
		CacheModel model = new CacheModel().setKey(CoreConstClass.REDIS_SIGN_KAFKA);
		Map<String, Object> query = redisServiceClient.query(model);
		if (null == query || StringUtil.isEmpty(query.get("data"))) {
			logger.info("updateLawKafka数据query==", query);
			return unviewMapper.getLawKafka();
		} else {
			return query.get("data").toString();
		}

	}

}
