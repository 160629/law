package com.chinatower.product.legalms.modules.flow.mapper.sms;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.sms.TPubSmsTxd;
import com.chinatower.product.legalms.modules.flow.vo.sms.SmsLogList;
import com.chinatower.product.legalms.modules.flow.vo.sms.SmsLogParam;

public interface TPubSmsTxdMapper {
    int deleteByPrimaryKey(Long txdId);

    int insert(TPubSmsTxd record);

    int insertSelective(TPubSmsTxd record);

    TPubSmsTxd selectByPrimaryKey(Long txdId);
    
    TPubSmsTxd selectBySendId(String sendId);

    int updateByPrimaryKeySelective(TPubSmsTxd record);

    int updateByPrimaryKeyWithBLOBs(TPubSmsTxd record);

    int updateByPrimaryKey(TPubSmsTxd record);
    
    List<SmsLogList> smsLogList(SmsLogParam param);

	List<String> selectTypes();
}