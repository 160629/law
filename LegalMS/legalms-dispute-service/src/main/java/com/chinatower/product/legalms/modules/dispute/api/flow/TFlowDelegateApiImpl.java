package com.chinatower.product.legalms.modules.dispute.api.flow;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.common.DisputeConstClass.FAILURE;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowDelegateService;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.eos.foundation.common.utils.DateUtil;
import com.eos.workflow.omservice.WFParticipant;
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
@Api(tags = { "委托接口" })
public class TFlowDelegateApiImpl extends BaseController implements TFlowDelegateApi {
	private static final String CLASS_CATCH = "TFlowDelegateApiImpl类捕捉异常";
	private static final Logger logger = LoggerFactory.getLogger(TFlowDelegateApiImpl.class);
	@Autowired
	TFlowDelegateService service;

	@Autowired
	TFlowCommonService commonService;
	@Override
	@ApiOperation(value = "分页查询委托")
	public ProcessResult selectAll(@RequestBody TFlowDelegate record,Integer pageNum,Integer pageSize) {
		try {
			UserInfo info = RequestHolder.getUserInfo();
			record.setLoginAcct(info.getLoginAcct());
			//列表查询
			record.setApproveItemType("1");
			PageInfo<TFlowDelegate> pageInfo = service.selectAll(record,pageNum, pageSize);
			return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			return new ProcessResult(ProcessResult.ERROR,FAILURE.FAILURE_MESS);
		}
	}
	@Override
	@ApiOperation(value = "新增或修改委托")
	public ProcessResult addOrUpdate(@RequestBody TFlowDelegate record) {
		try {
			UserInfo info = RequestHolder.getUserInfo();
			if(info.getLoginAcct().equals(record.getToerId())) {
				return new ProcessResult(ProcessResult.ERROR, "委托代办不可以委托自己");
			}
			int k=0;
			if(null==record.getDelegateId()) {
				TFlowDelegate tFlowDelegate = new TFlowDelegate(info.getLoginAcct());
				List<TFlowDelegate> selectAll = service.selectAll(tFlowDelegate);
				if(null != selectAll && !selectAll.isEmpty()) {
					return new ProcessResult(ProcessResult.WARN, info.getLoginName()+"存在进行中的委托", 0);
				}
				k=1;
			}
			StringUtil.copyProperties(record,info);
			int i = service.addOrUpdate(record);
			if(k==1) {
				WFParticipant login = new WFParticipant(info.getLoginAcct(), info.getLoginName(), null);
				List<WFParticipant> toers = Arrays.asList(new WFParticipant(record.getToerId(),record.getToerName(), null));
				commonService.autoUnView("t_flow_delegate",  login, toers, DateUtil.format(record.getStartTime(), DisputeConstClass.DATE_TIME) , DateUtil.format(record.getEndTime(), DisputeConstClass.DATE_TIME),1);
			}
			return new ProcessResult(ProcessResult.SUCCESS, "", i);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}
	@Override
	@ApiOperation(value = "结束委托")
	public ProcessResult endDelegate(@RequestBody List<TFlowDelegate> delegates) {
		try {
			int i=0;
			UserInfo info = RequestHolder.getUserInfo();
			if(!delegates.isEmpty()) {
				i = service.endDelegate(delegates);
				delegates.forEach(x->{
					WFParticipant login = new WFParticipant(info.getLoginAcct(), info.getLoginName(), null);
					List<WFParticipant> toers = Arrays.asList(new WFParticipant(x.getToerId(),x.getToerName(), null));
					commonService.autoUnView("t_flow_delegate",  login, toers, null, DateUtil.format(new Date(), DisputeConstClass.DATE_TIME),2);
				});
			}
			return new ProcessResult(ProcessResult.SUCCESS, "", i);
		} catch (Exception e) {
			logger.info(CLASS_CATCH,e);
			return new ProcessResult(ProcessResult.ERROR, FAILURE.FAILURE_MESS);
		}
	}
	@Override
    public ProcessResult setLawKafa(String lawKafa){
		if("1".equals(lawKafa) || "0".equals(lawKafa) ) {
	    	commonService.updateLawKafka(lawKafa);
		}
		String lawKafka = commonService.getLawKafka();
		return new ProcessResult(ProcessResult.SUCCESS, "", lawKafka);
    }
}
