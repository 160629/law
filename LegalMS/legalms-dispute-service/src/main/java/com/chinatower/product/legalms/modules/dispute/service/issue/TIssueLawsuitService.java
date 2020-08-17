package com.chinatower.product.legalms.modules.dispute.service.issue;

import java.util.List;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueLawsuit;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.issue.TIssueLawsuitVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueLawsuitListVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;

/**
 * <p>
 * 引诉信息表 服务类
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
public interface TIssueLawsuitService {
	
	int addTIssueLawsuit (AddFlowVO<TIssueLawsuit> vo,FlowUtil flowUtil,UserInfo info);
	
	int tempTIssueLawsuit (AddFlowVO<TIssueLawsuit> vo,UserInfo info);
	
	List<TIssueLawsuit> selectAll(ListParam param);
	
	int addFlow(AddFlowVO<TIssueLawsuit> vo, FlowUtil flowUtil, UserInfo info,TFlowLog log);
	
	TIssueLawsuitVO findOne(String lawsuitId, String actId);
	
	int deleteTIssueGuide(List<String> lawsuitIds);
	
	int deleteByPrimaryKey(String lawsuitId);
	
	int updateByPrimaryKey(TIssueLawsuit lawsuit);
	
	int updateByPrimaryKeySelective(TIssueLawsuit lawsuit);
	
	List<TIssueLawsuitVO>  selectAllByCase(ListParam param, String unitId);

	TIssueLawsuit selectByPrimaryKey(String lawsuitId);
	
	int autoSaveCase(String approveItemType,String id,String pid,UserInfo userInfo);

	ProcessResult findTIssueLawsuitList(TIssueLawsuitListVO param);
	
	ProcessResult addTFlowLog(AddFlowLogVO vo);
	
	int updateCaseId(TIssueLawsuit lawsuit);
	
    boolean verifyCaseId(TIssueLawsuit model);
}
