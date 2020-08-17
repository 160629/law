package com.chinatower.product.legalms.modules.flow.service.sms.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.modules.flow.entity.sms.TPubSmsTxd;
import com.chinatower.product.legalms.modules.flow.mapper.sms.TPubSmsTxdMapper;
import com.chinatower.product.legalms.modules.flow.service.sms.SmsService;
import com.chinatower.product.legalms.modules.flow.vo.sms.SmsParam;
import com.chinatower.product.legalms.modules.flow.vo.sms.SmsProperties;
import com.chinatower.product.legalms.utils.DateUtils;
import com.chinatower.product.legalms.utils.HttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SmsServiceImpl implements SmsService{
	private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
	@Autowired
	SmsProperties sp;
	
	@Autowired
	TPubSmsTxdMapper smsTxdMapper;
	@Override
	public String sendsms(TPubSmsTxd txd) {
		Integer retry = null==txd.getRetry()?1:txd.getRetry();
		if(retry>2) {
			return "retry is ok";
		}
		//接收的短信内容
		String content = txd.getContent();  
		//发送时间
		String mttime = DateUtils.format(new Date(), DateUtils.DATE_STR_PATTERN);   //发送时间
		//签名+内容
		content=sp.getSign()+content;      
		//密码+发送时间+签名+内容,截取MD5加密后的32位密文
		String str = sp.getPwd() + mttime + content;
		String pwd = DigestUtils.md5DigestAsHex(str.getBytes()).substring(0, 32);
		//组装参数对象
		SmsParam smsParam = new SmsParam(sp.getName(),pwd,mttime,content,txd.getPhones());
		ObjectMapper mapper = new ObjectMapper();
		try {
			//参数对象转字符串
			String paramJson = mapper.writeValueAsString(smsParam);
			//调用服务商短信发送接口
			String post = HttpUtils.post(sp.getUrl(), paramJson);
			logger.info("短信发送服务接口返回：{}",post);
			Map<String, Object> returnMse = mapper.readValue(post, Map.class);
			String string = returnMse.get("code").toString();
			txd.setTxdCode(string);
			txd.setMttime(mttime);
			txd.setSign(sp.getSign());
			if(!"200".equals(string) && StringUtil.isEmpty(txd.getSendPid())) {
				smsTxdMapper.insertSelective(txd);
				logger.info("短信发送失败重发phones:{}重发次数：{}",txd.getPhones(),retry);
				txd.setRetry(retry+1);
				sendsms(txd);
			}else {
				String sendid = returnMse.get("sendid").toString();
				txd.setSendId(sendid);
				smsTxdMapper.insertSelective(txd);
			}
			/**
			 *	
			 *      
			 *   	1.可根据返回状态判断是否发送成功，请求接口返回失败可重复调用方法重复发送
			 * 		保存发送记录到 t_pub_sms_txd 表
			 */
			//短信发送完成返回信息
			return post;
		} catch (Exception e) {
			logger.info("短信发送异常",e);
		}
		
		/**
		 *	
		 *      2.短信发送接口调用失败可重复调用方法重复发送，并保存短信发送记录
		 * 
		 */
		return "短信发送接口调用失败";
	}
	@Override
	public TPubSmsTxd selectSmsTxdById(Long txdId) {
		return smsTxdMapper.selectByPrimaryKey(txdId);
	}

}
