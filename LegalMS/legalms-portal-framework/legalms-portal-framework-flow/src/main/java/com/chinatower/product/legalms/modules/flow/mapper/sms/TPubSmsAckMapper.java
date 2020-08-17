package com.chinatower.product.legalms.modules.flow.mapper.sms;

import com.chinatower.product.legalms.modules.flow.entity.sms.TPubSmsAck;

public interface TPubSmsAckMapper {
    int deleteByPrimaryKey(String phone);

    int insert(TPubSmsAck record);

    int insertSelective(TPubSmsAck record);

    TPubSmsAck selectByPrimaryKey(String phone);

    int updateByPrimaryKeySelective(TPubSmsAck record);

    int updateByPrimaryKey(TPubSmsAck record);
}