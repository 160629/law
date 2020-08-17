package com.chinatower.product.legalms.modules.dispute.service.legislation;


import java.util.List;
import java.util.Map;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.LegislationVO;
import com.chinatower.product.legalms.modules.dispute.vo.legislation.LegislationParam;
import com.chinatower.product.legalms.modules.dispute.vo.legislation.PageParam;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TLegislationListVO;
import com.chinatower.product.legalms.modules.flow.entity.common.BusinessLogVO;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;


public interface LegislationService {

    /*法律文书转办列表查询*/
    ProcessResult selectAll(PageParam param);

    /*法律文书暂存*/
    int tempLegislation(AddFlowVO<LegislationVO> vo,UserInfo info,LegislationVO legislationVO);

    /*法律文书转办详情接口*/
    LegislationParam findOneLegislation(String id, String actId, BusinessLogVO businessLogVO);

    /*批量逻辑删除*/
    int deleteLegislation(List<String> ids);
    
    /*法律文书转办起草*/
    int addLegislationInfo(AddFlowVO<LegislationVO> vo, FlowUtil flowUtil, UserInfo info,LegislationVO legislationVO);




    /*根据主键修改*/
    int updateByPrimaryKey(LegislationVO legislationVO);


    /*起草记录流程信息*/
    int addFlow(AddFlowVO<LegislationVO> vo, FlowUtil flowUtil, UserInfo info, TFlowLog log);
    /*法律文书综合查询*/
    ProcessResult findLegislationList(TLegislationListVO param);

    String selectCode(String unitCode, String orgCode);

    int updateByPrimaryKeySelective(LegislationVO legislationVO);

    ProcessResult addTFlowLog(AddFlowLogVO vo);

    List<Map<String, Object>> getExcelDate(String loginAcct);
}
