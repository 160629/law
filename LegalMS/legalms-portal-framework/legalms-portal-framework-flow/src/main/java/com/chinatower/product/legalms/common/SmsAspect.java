package com.chinatower.product.legalms.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.framework.mq.kafka.api.KafkaUtils;
import com.chinatower.product.legalms.modules.flow.entity.sms.TPubSmsTxd;
import com.chinatower.product.legalms.modules.flow.entity.sms.TSysDictData;
import com.chinatower.product.legalms.modules.flow.mapper.sms.TSysDictDataMapper;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.fasterxml.jackson.databind.ObjectMapper;



@Component
@Aspect
public class SmsAspect {
	private static final  Logger logger = LoggerFactory.getLogger(SmsAspect.class);
	
	
	@Autowired
	private KafkaUtils kafkaUtils;
	
    @Autowired
    TSysDictDataMapper tSysDictDataMapper;
    @Autowired
    TFlowCommonService tFlowCommonService;
    
    @Pointcut("@annotation(com.chinatower.product.legalms.common.SmsAround)")
	public void smsPointcut() {
		logger.info("发送短信工作切入点");
	}
    public List<TPubSmsTxd> getSmsContent(Object[] args) {
		List<TPubSmsTxd> contents=new ArrayList<>();
        if(args.length<1) {
        	logger.info("方法参数错误");
        	return contents;
        }
        Object obj = args[0];
        String dictValue="";
        String title="";
        int smsFlag=0;
        List<OrgParticipantVO> orgs;
        if(obj instanceof HttpServletRequest) {
        	HttpServletRequest request = (HttpServletRequest) obj;
    		AddFlowLogVO vo=(AddFlowLogVO) request.getAttribute("vo");
    		dictValue=vo.getModuleName();
    		title=vo.getTitle();
    		orgs = vo.getOrgs();
    		smsFlag=null==vo.getSmsFlag()?0:vo.getSmsFlag();
        }else if(obj instanceof AddFlowLogVO) {
        	AddFlowLogVO vo=(AddFlowLogVO)obj;
    		dictValue=vo.getModuleName();
    		title=vo.getTitle();
    		orgs = vo.getOrgs();
    		smsFlag=null==vo.getSmsFlag()?0:vo.getSmsFlag();
        }else if(obj instanceof AddFlowVO) {
        	AddFlowVO vo=(AddFlowVO)obj;
    		dictValue=vo.getModuleName();
    		title=vo.getTitle();
    		orgs = vo.getOrgs(); 
    		smsFlag=null==vo.getSmsFlag()?0:vo.getSmsFlag();
        }else {
        	logger.info("方法参数类型错误");
        	return contents;
        }
		List<TSysDictData> byTypes = tSysDictDataMapper.selectByType(CoreConstClass.SYS_FLOE_TYPE, null, dictValue);
		if(byTypes.isEmpty() || smsFlag==1) {
		  	logger.info("数据字典配置错误smsFlag:{}",smsFlag);
        	return contents;
		}
		TSysDictData dictData = byTypes.get(0);
		if("1".equals(dictData.getStatus())) {
			return contents;
		}
		return getSmsTxds(title,contents,dictData, orgs);
    }
    public List<TPubSmsTxd>  getSmsTxds(String title,List<TPubSmsTxd> contents ,TSysDictData dictData,List<OrgParticipantVO> orgs){
    	for (OrgParticipantVO vo : orgs) {
    		String phone = "Y".equals(dictData.getIsDefault())?dictData.getRemark():vo.getPhone();
    		if(StringUtil.isEmpty(phone)) {
    			continue;
    		}
    		TPubSmsTxd txd = new TPubSmsTxd(phone,title,dictData.getDictCabel(),vo.getReceiverId(),vo.getReceiverName());
    		contents.add(txd);
		};
		return contents;
    }

	@Around("smsPointcut()")
	public ProcessResult around(ProceedingJoinPoint point){
		logger.info("待办开始发送短信前置工作");
		ProcessResult proceed =null;
	    try {
	    	proceed = (ProcessResult) point.proceed();
			if (null!= proceed && ProcessResult.SUCCESS.equals(proceed.getResultStat())) {
				List<TPubSmsTxd> contents = getSmsContent(point.getArgs());
				logger.info("待办开始发送短信参数contents:{}",contents);
				if(null== contents || contents.isEmpty()) {
					return proceed;
				}
				String lawKafka = tFlowCommonService.getLawKafka();
				String txt2;
				if ("1".equals(lawKafka)) {
					ObjectMapper objectMapper = new ObjectMapper();
					Map<String, Object> map = new HashMap<>();
					map.put("topic", "tasks");
					map.put("data", contents);
					String paramJson = objectMapper.writeValueAsString(map);
					txt2 = kafkaUtils.asyncSend(CoreConstClass.LAW_TOPIC, paramJson);
				} else {
					txt2 = tFlowCommonService.txt2(contents);
				}

				logger.info("待办开始发送短信结果:{}",txt2);
			} 
			return proceed;
		} catch (Throwable e) {
			logger.info("待办发送短信工作异常",e);
			return proceed;
		}
	}

}