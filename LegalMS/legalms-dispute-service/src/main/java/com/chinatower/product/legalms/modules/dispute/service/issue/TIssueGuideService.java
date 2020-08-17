package com.chinatower.product.legalms.modules.dispute.service.issue;

import java.util.List;
import java.util.Map;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueGuide;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.issue.TIssueGuideVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueGuideListVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.eos.workflow.omservice.WFParticipant;

/**
 * <p>
 * 引诉信息表 服务类
 * </p>
 *
 * @author wangyong
 * @since 2019-09-30
 */
public interface TIssueGuideService {

	int insertSelective(TIssueGuide guide);

	List<TIssueGuide> selectAll(ListParam param);
	
	 List<TIssueGuideVO>  selectAllByCase(ListParam param, String unitId);
	
	Map<String,Object> selectByType(String clazz,String fieldName, String fieldValue);

	int addTIssueGuide (AddFlowVO<TIssueGuide> vo,FlowUtil flowUtil,UserInfo info);
	
	int tempTIssueGuide (AddFlowVO<TIssueGuide> vo,UserInfo info);

	int addFlow(AddFlowVO<TIssueGuide> vo, FlowUtil flowUtil, UserInfo info,TFlowLog log);

	int deleteByPrimaryKey(String guideId);

	Map<String,Object> testLocal();
	
	void addOrgsUndone(List<OrgParticipantVO> orgs) ;
	
	void addUndone(WFParticipant participant);

	TIssueGuideVO findOne(String guideId, String actId);

	int deleteTIssueGuide(List<String> guideIds);
	
	int updateByMap(Map<String, Object> map);
	
	int updateByPrimaryKey(TIssueGuide guide);
	
	int updateByPrimaryKeySelective(TIssueGuide guide);
	
	int inserttest(String key);

	TIssueGuide selectByPrimaryKey(String guideId);
	
	Map<String, String> getDictMap(String dictType);
	
	List<Map<String,Object>>  getExcelDate();

//	List<TIssueGuide> findTIssueJointlyList(ListParam param, UserInfo info, List<OrgDepLeader> orgDepLeaders);
	ProcessResult findTIssueGuideList(TIssueGuideListVO param);

    String  selectCode(String unitName,String moduleCode,String orgCode);
    
    ProcessResult addTFlowLog(AddFlowLogVO vo);
}
