package com.chinatower.product.legalms.modules.dispute.mapper.legislation;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.dispute.entity.LegislationVO;
import com.chinatower.product.legalms.modules.dispute.vo.legislation.LegislationParam;
import com.chinatower.product.legalms.modules.dispute.vo.legislation.PageParam;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TLegislationListVO;
import com.chinatower.product.legalms.modules.flow.vo.common.RelationshipVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowLogVO;

public interface LegislationMapper {

    int addLegislationInfo(LegislationVO legislationVO);

    int updateLegislation(LegislationVO legislationVO);

    List<LegislationVO> selectAll(@Param("param") PageParam param,@Param("loginAcct") String loginAcct);


    String selectOdd(@Param("zb") String zb, @Param("hngs") String hngs);

    int insertSelectiveTemp(LegislationVO model);

    int updateByPrimaryKeySelective(LegislationVO model);

    /*根据id查询法律文书转办详情*/
    List<LegislationParam> findOneLegislation(String id);

    /*查询历史审批记录*/
    TFlowLogVO selectCurrActivity(Long flowId);

    /*批量删除*/
    int deleteLegislation(List<String>  ids);

    int insertSelective(@Param("legislationVO") LegislationVO legislationVO);

    //卷宗关联表增加数据
    int addRelationship(@Param("relationshipVO")RelationshipVO relationshipVO);
    //卷宗关联表修改数据
    int updateRelationship(@Param("relationshipVO") RelationshipVO relationshipVO);

    LegislationVO selectByPrimaryKey(String id);

    List<LegislationVO> selectAlles(PageParam param, String loginAcct);

    int updateByPrimaryKey(LegislationVO legislationVO);

    List<LegislationVO> selectWholeData(@Param("param") TLegislationListVO param);
    

    List<Map<String, String>> dictMaps(String dictType);

    List<Map<String, String>> getExcelDate(String loginAcct);

    String getHeadOrgCode(String enterpType);
}
