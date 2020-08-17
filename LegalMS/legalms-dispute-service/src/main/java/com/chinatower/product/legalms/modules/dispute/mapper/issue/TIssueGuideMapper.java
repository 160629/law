package com.chinatower.product.legalms.modules.dispute.mapper.issue;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueGuide;
import com.chinatower.product.legalms.modules.dispute.vo.issue.ListParam;
import com.chinatower.product.legalms.modules.dispute.vo.issue.TIssueGuideVO;
import com.chinatower.product.legalms.modules.dispute.vo.push.LawSum;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TIssueGuideListVO;

public interface TIssueGuideMapper {
    int deleteByPrimaryKey(String guideId);

    int insert(TIssueGuide record);

    int insertSelective(@Param("record")TIssueGuide record);
    
    int insertSelectiveTemp(@Param("record")TIssueGuide record);
    
    String  selectCode(String unitName,String moduleCode,String orgCode);
    
    TIssueGuide selectByPrimaryKey(String guideId);
    
    List<TIssueGuideVO> selectOne(String guideId);
    
    List<TIssueGuideVO>  selectAllByCase(@Param("param") ListParam param,@Param("unitId") String unitId);
    
    List<TIssueGuide>  selectAll(ListParam param);

    int updateByPrimaryKeySelective(TIssueGuide record);

    int updateByPrimaryKeyWithBLOBs(TIssueGuide record);

    int updateByPrimaryKey(TIssueGuide record);
    
    Map<String, Object> selectCase(@Param("caseId") String caseId);
    
    List<Map<String, Object>> selectFile(@Param("businessId") String businessId);
    

	Map<String, Object> selectByType(String tableName,String fieldName, String fieldValue);

	int deleteTIssueGuide(List<String> list);
	
	int inserttest(List<String> list);
	
	List<Map<String,String>>  dictMaps(@Param("dictType")String dictType);
	
	List<Map<String,String>>  getExcelDate();

	List<LawSum> getLawSum();

	List<Map<String,Object>>  getSubjectMatter();

    List<TIssueGuide> selectWholeData(@Param("param") TIssueGuideListVO param);
}