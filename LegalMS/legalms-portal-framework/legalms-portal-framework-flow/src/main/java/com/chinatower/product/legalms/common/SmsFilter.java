package com.chinatower.product.legalms.common;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

import com.chinatower.framework.mq.kafka.filter.CustomFilter;
@Component
public class SmsFilter implements CustomFilter {

    @Override
    public boolean rule(ConsumerRecord consumerRecord) {
        return false;
    }
}