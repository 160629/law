package com.chinatower.product.legalms.common;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinatower.framework.mq.kafka.consumer.ConsumerMessage;
import com.chinatower.product.legalms.modules.flow.entity.sms.TPubSmsTxd;
import com.chinatower.product.legalms.modules.flow.service.sms.SmsService;
import com.chinatower.product.legalms.modules.flow.service.unview.impl.TFlowCommonServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.weaver.ofs.webservices.vo.UnviewPushVO;

@Component
public class SmsConsumer implements ConsumerMessage {
	private static final Logger logger = LoggerFactory.getLogger(SmsConsumer.class);
	@Autowired
	SmsService smsService;
	@Autowired
	TFlowCommonServiceImpl commonServiceImpl;

	@Override
	public <T> T ack(ConsumerRecord consumerRecord) {
		logger.info("consumer----------value is {}", consumerRecord.value());
		try {
			String string = consumerRecord.value().toString();
			ObjectMapper json = new ObjectMapper();
			Map<String, Object> readValue = json.readValue(string, new TypeReference<HashMap<String, Object>>() {
			});
			if (null == readValue || null == readValue.get("topic") || null == readValue.get("data")) {
				return (T) string;
			}
			String topic = readValue.get("topic").toString();
			String data = json.writeValueAsString(readValue.get("data"));
			if ("retry".equals(topic)) {
				List<TPubSmsTxd> txds = json.readValue(data, new TypeReference<List<TPubSmsTxd>>() {
				});
				for (TPubSmsTxd txd : txds) {
					smsService.sendsms(txd);
				}
			}else if ("tasks".equals(topic)) {
				List<TPubSmsTxd> txds = json.readValue(data, new TypeReference<List<TPubSmsTxd>>() {
				});
				for (TPubSmsTxd td : txds) {
					String content = MessageFormat.format(CoreConstClass.CONTENT_TASKS,td.getBusinessType(),
							td.getBusinessId());
					td.setContent(content);
					smsService.sendsms(td);
				}
		
			}else if ("unview".equals(topic)) {
				List<TPubSmsTxd> txds = json.readValue(data, new TypeReference<List<TPubSmsTxd>>() {
				});
				for (TPubSmsTxd td : txds) {
					String content = MessageFormat.format(CoreConstClass.CONTENT_UNVIEW, td.getBusinessId());
					td.setContent(content);
					smsService.sendsms(td);
				}
			}else if ("unviewPush".equals(topic)) {
				List<UnviewPushVO> arrayList = json.readValue(data, new TypeReference<List<UnviewPushVO>>() {
				});
				commonServiceImpl.unviewPush1(arrayList, new ObjectMapper());
			} else if ("unviewDonePush".equals(topic)) {
				List<UnviewPushVO> arrayList = json.readValue(data, new TypeReference<List<UnviewPushVO>>() {
				});
				commonServiceImpl.unviewDonePush1(arrayList, new ObjectMapper());
			} else {
				logger.info("consumer----------待续 ");
			}

		} catch (Exception e) {
			logger.info("consumer----------接收异常", e);
		}
		return null;
	}
}