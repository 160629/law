package com.chinatower.product.legalms.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chinatower.framework.cloud.feignlog.InfoFeginLog;
import com.chinatower.product.legalms.modules.dispute.mapper.issue.TIssueGuideMapper;
import com.chinatower.product.legalms.modules.dispute.vo.push.LawSum;
import com.chinatower.product.legalms.modules.dispute.vo.push.LawSums;
import com.chinatower.product.legalms.modules.flow.entity.unview.TFlowUnview;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.provider.call.tcase.BraceServiceClient;

@Component
@JobAround(serviceName = "legalms-dispute-service")
public class ScheduledJob {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledJob.class);

	@Autowired
	ScheduledProperties properties;
	@Autowired
	TIssueGuideMapper guideMapper;
	@Autowired
	BraceServiceClient braceServiceClient;

	// 法律系统统计信息推送接口
	@Scheduled(cron = "${job.lawSumService}")
	public void lawSumService() {
		List<LawSum> sums = guideMapper.getLawSum();
		Double caseSumMoney = 0.0;
		Double indictedCaseMoney = 0.0;
		Integer sumCase = 0;
		Integer sumMajorCase = 0;
		Integer yearNumberIndictedCase = 0;
		Integer yearSumCase = 0;
		for (LawSum x : sums) {
			caseSumMoney = caseSumMoney + x.getCaseSumMoney();
			indictedCaseMoney = indictedCaseMoney + x.getIndictedCaseMoney();
			sumCase = sumCase + x.getSumCase();
			sumMajorCase = sumMajorCase + x.getSumMajorCase();
			yearNumberIndictedCase = yearNumberIndictedCase + x.getYearNumberIndictedCase();
			yearSumCase = yearSumCase + x.getYearSumCase();
		}
		LawSum lawSum = new LawSum(yearSumCase, yearNumberIndictedCase, sumMajorCase, sumCase, caseSumMoney,
				indictedCaseMoney);
		sums.add(lawSum);
		LawSums lawSums = new LawSums(sums);
		try {
			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(lawSums);
			logger.info("法律系统统计信息推送接口开始=={}", jsonObject);
			MDC.put(InfoFeginLog.LOG_SENDCODE, "60.6006");
			MDC.put(InfoFeginLog.LOG_SERVERCODE, "50.5012");
			String lawSum2 = braceServiceClient.lawSum(jsonObject);
			logger.info("法律系统统计信息推送接口结果=={}", lawSum2);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

	}

	// 案由信息统计接口
	@Scheduled(cron = "${job.subjectMatterService}")
	public void subjectMatterService() {
		List<Map<String, Object>> subjectMatter = guideMapper.getSubjectMatter();
		Map<String, Object> hashMap = new HashMap<>();
		hashMap.put("serviceId", "law");
		hashMap.put("list", subjectMatter);

		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(hashMap);
		logger.info("法律系统案由信息统计接口开始=={}", jsonObject);
		MDC.put(InfoFeginLog.LOG_SENDCODE, "60.6006");
		MDC.put(InfoFeginLog.LOG_SERVERCODE, "50.5012");
		String matter = braceServiceClient.subjectMatter(jsonObject);
		logger.info("法律系统案由信息统计接口结果=={}", matter);

	}

	@Autowired
	TFlowCommonService tFlowCommonService;

	@Scheduled(cron = "${job.unviewPushRetry}")
	public void unviewPushRetry() {
		logger.info("定时待阅重新OA推送开始------------------");
		List<TFlowUnview> list = tFlowCommonService.pushSelectList("0", "0");
		if (!list.isEmpty()) {
			String lawKafka = tFlowCommonService.getLawKafka();
			tFlowCommonService.unviewPush(list,lawKafka);
		}
		list = tFlowCommonService.pushSelectList("1", "0");
		if (!list.isEmpty()) {
			tFlowCommonService.unviewDonePushRetry(list);
		}

	}
}
