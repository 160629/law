package com.chinatower.product.legalms.modules.dispute.api.flow;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.DisputeConstClass.FAILURE;
import com.chinatower.product.legalms.common.ScheduledProperties;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowUnviewService;
import com.chinatower.product.legalms.modules.flow.entity.unview.TFlowUnview;
import com.chinatower.product.legalms.modules.flow.vo.sms.SmsLogList;
import com.chinatower.product.legalms.modules.flow.vo.sms.SmsLogParam;
import com.chinatower.product.legalms.modules.flow.vo.unview.TFlowUnviewParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 审批表 前端控制器
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
@RestController
@Api(tags = { "已阅待阅接口" })
public class TFlowUnviewApiImpl implements TFlowUnviewApi {
	private static final String CLASS_CATCH = "TFlowUnviewApiImpl类捕捉异常";
	private static final Logger logger = LoggerFactory.getLogger(TFlowUnviewApiImpl.class);
	@Autowired
	TFlowUnviewService service;
	@Autowired
	ScheduledProperties properties;
	@Override
	@ApiOperation(value = "分页查询已阅待阅")
	public ProcessResult selectAll(@RequestBody TFlowUnviewParam record) {
		try {
			PageHelper.startPage(record.getPageNum(), record.getPageSize());
			List<TFlowUnview> list = service.selectAll(record);
			PageInfo<TFlowUnview> pageInfo = new PageInfo<>(list);
			return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			return new ProcessResult(ProcessResult.ERROR,FAILURE.FAILURE_MESS);
		}
	}
	@Override
	@ApiOperation(value = "新增或修改已阅待阅")
	public ProcessResult addOrUpdate(TFlowUnview record) {
		try {
			int i = service.addOrUpdate(record);
			return new ProcessResult(ProcessResult.SUCCESS, "", i);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}
	@Override
	@ApiOperation(value = "类别列表")
	public ProcessResult selectViewTypes(String viewStatus) {
		try {
			//List<String> list = service.selectViewTypes(viewStatus);
			String[] str={"纠纷处理","案件交办","案件协办","法律文书办理","委托代办","案件移交","证照申请","授权申请"};
			List<String> list = Arrays.asList(str);
			return new ProcessResult(ProcessResult.SUCCESS, "", list);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}
	@Override
	public ProcessResult updateStatus(@RequestBody List<String> viewIds) {
		int i = service.updateStatus(viewIds);
		return new ProcessResult(ProcessResult.SUCCESS, "", i);
	}
	@Override
	@ApiOperation(value = "单个待阅")
	public ProcessResult findOne(String viewId) {
		logger.info("单个待阅参数:viewId={}", viewId);
		String formurl = properties.getFormurl();
		TFlowUnview findOne = service.findOne(viewId);
		if(null==findOne) {
			return new ProcessResult(ProcessResult.ERROR, "数据不存在", null);
		}
		findOne.setViewUrl(formurl+findOne.getViewUrl());
		return new ProcessResult(ProcessResult.SUCCESS, "", findOne);
	}
	
	//短信发送确认与短信回复接口
	/**
	 * @param msgtype 	       返回消息类型   0 表示短信回复消息   2 表示短信发送确认消息
	 * @param phone        手机号
	 * @param receivetime  接收时间
	 * @param sendtime	       发送时间 (msgtype=0的情况)
	 * @param sendid	       批次ID (群发的情况多个手机号批次ID是一样的)
	 * @param state		       状态码
	 * @param content	       消息内容 (msgtype=0的情况)
	 */
	@Override
	public String backMsg(String msgtype, String phone, String receivetime, String sendtime,
			String sendid,String state, String content) {
		logger.info("backMsg接口：msgtype:{},phone:{},receivetime:{},sendtime:{},sendid:{},state:{},content:{}", msgtype,
				phone, receivetime, sendtime, sendid, state, content);
		service.backMsg(msgtype, phone, receivetime, sendtime, sendid, state, content);
		//返回OK表示已收到信息
		return "ok";

	}
	@Override
	public ProcessResult smsLogList(@RequestBody SmsLogParam param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<SmsLogList> list = service.smsLogList(param);
		PageInfo<SmsLogList> pageInfo = new PageInfo<>(list);
		return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
	}
	@Override
	public ProcessResult selectTypes() {
		List<String> list = service.selectTypes();
		return new ProcessResult(ProcessResult.SUCCESS, "", list);
	}
	

}
