package com.chinatower.product.legalms.modules.dispute.mapper.issue;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueLawsuit;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.issue.TIssueLawsuitVO;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueLawsuitListVO;

public interface TIssueLawsuitMapper {
    int deleteByPrimaryKey(String lawsuitId);

    int insert(TIssueLawsuit record);

    int insertSelective(@Param("record") TIssueLawsuit record);

    int insertSelectiveTemp(@Param("record") TIssueLawsuit record);

    TIssueLawsuit selectByPrimaryKey(String lawsuitId);

    List<TIssueLawsuit> selectAll(ListParam param);

    int updateByPrimaryKeySelective(TIssueLawsuit record);

    int updateByPrimaryKey(TIssueLawsuit record);

    List<TIssueLawsuitVO> selectOne(String lawsuitId);

    TIssueLawsuitVO selectLawsuitAndFlow(String lawsuitId);

    int deleteTIssueLawsuit(List<String> list);

    List<TIssueLawsuitVO> selectAllByCase(@Param("param") ListParam param, @Param("unitId") String unitId);

    List<TIssueLawsuit> selectWholeData(@Param("param") TIssueLawsuitListVO param);
    
    int verifyCaseId(@Param("caseId") String caseId);
}