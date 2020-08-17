package com.chinatower.product.legalms.modules.flow.mapper.sms;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.sms.TPubSmsBack;

public interface TPubSmsBackMapper {
    int deleteByPrimaryKey(@Param("phone") String phone, @Param("receiveTime") String receiveTime);

    int insert(TPubSmsBack record);

    int insertSelective(TPubSmsBack record);

    TPubSmsBack selectByPrimaryKey(@Param("phone") String phone, @Param("receiveTime") String receiveTime);

    int updateByPrimaryKeySelective(TPubSmsBack record);

    int updateByPrimaryKeyWithBLOBs(TPubSmsBack record);
}