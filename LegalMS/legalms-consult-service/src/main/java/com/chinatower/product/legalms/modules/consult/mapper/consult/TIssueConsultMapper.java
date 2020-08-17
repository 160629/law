package com.chinatower.product.legalms.modules.consult.mapper.consult;

import com.chinatower.product.legalms.modules.consult.entity.consult.TIssueConsult;
import com.chinatower.product.legalms.modules.consult.vo.issue.ConsultListParam;
import com.chinatower.product.legalms.modules.consult.vo.issue.TIssueConsultVO;
import com.chinatower.product.legalms.modules.consult.vo.querylist.TIssueConsultListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface TIssueConsultMapper {
    String selectCode(@Param("unitName") String unitName, @Param("moduleCode") String moduleCode, @Param("orgCode") String orgCode);

    int updateByPrimaryKey(@Param("record") TIssueConsult record);

    int insertSelective(@Param("consult")TIssueConsult consult);

    int deleteByPrimaryKey(String id);

    List<TIssueConsult> selectWholeData(@Param("param") TIssueConsultListVO param);

    List<TIssueConsultVO> selectOne(String id);

    List<Map<String, Object>> selectFile(@Param("businessId") String businessId);

    List<TIssueConsult> selectAll(@Param("consultListParam")ConsultListParam consultListParam);

    int deleteTIssueConsult(@Param("ids")List<String> ids);

    int updateByPrimaryKeySelective(@Param("tIssueConsult")TIssueConsult tIssueConsult);

    TIssueConsult selectByPrimaryKey(@Param("approveItemId")String approveItemId);

    List<TIssueConsult> selectAllDatas(@Param("param") TIssueConsultListVO param);
    
}
