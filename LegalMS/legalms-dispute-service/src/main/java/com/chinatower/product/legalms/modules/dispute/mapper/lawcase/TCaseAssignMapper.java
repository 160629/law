package com.chinatower.product.legalms.modules.dispute.mapper.lawcase;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.dispute.entity.lawcase.TCaseAssign;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.issue.TCaseAssignVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TCaseAssignListVO;

public interface TCaseAssignMapper {
    int deleteByPrimaryKey(String assignId);

    int insert(TCaseAssign record);

    int insertSelective(@Param("record")TCaseAssign record);

    int insertSelectiveTemp(@Param("record")TCaseAssign record);
    
    TCaseAssign selectByPrimaryKey(String assignId);
    
    List<TCaseAssign>  selectAll(@Param("param")ListParam param,@Param("unitId") String unitId);

    int updateByPrimaryKeySelective(TCaseAssign record);

    int updateByPrimaryKey(TCaseAssign record);

	int deleteTCaseAssign(List<String> assignIds);
	
    List<TCaseAssignVO> selectOne(String assignId);
    
    TCaseAssignVO selectAssignAndFlow(String assignId);

    List<TCaseAssign> selectWholeData(@Param("param")TCaseAssignListVO param);
}