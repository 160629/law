package com.chinatower.product.legalms.common;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chinatower.framework.mq.kafka.handler.ProducerHandler;

@Component
public class SmsHanler implements ProducerHandler {
	private static final Logger logger = LoggerFactory.getLogger(SmsHanler.class);

    @Override
    public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
    	logger.info("DemoHanler sendTo topic success:{}",producerRecord.toString());
    }

    @Override
    public void onError(ProducerRecord producerRecord, Exception recordMetadata) {
    	logger.error("DemoHanler sendTo topic error:{}", producerRecord.toString());
    }
}