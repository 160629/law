package com.chinatower.product.legalms.modules.flow.mapper.unview;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.unview.TFlowUnview;
import com.chinatower.product.legalms.modules.flow.vo.unview.TFlowUnviewConfigVO;


public interface TFlowUnviewCommonMapper {
   

    int insertSelective(TFlowUnview record);
    
	int insertBatch(@Param("list") List <TFlowUnview> list);
    
	String selectLawsuitCaseId(String id);
	
	String getLawKafka();

	String selectLegislationCaseId(String id);

	String selectjointlyCaseId(String id);

	Map<String, Object> selectCase(String caseId);
	
    List<TFlowUnviewConfigVO> selectByType(@Param("shortFlowName")String shortFlowName,
    		@Param("configOrder")Integer configOrder);
    
	List<TFlowDelegate> selectDelegates(TFlowDelegate record);
	
	int updateDelegateStatusByPrimaryKey(TFlowDelegate tFlowDelegate);

	Map<String, Object> test(); 
	
	int updateByPrimaryKeyStatus(@Param("itemType") String itemType,
			@Param("itemId")String itemId,@Param("itemName") String itemName,
			@Param("itemStatusName")String itemStatusName,
			@Param("itemStatus")String itemStatus);
	
	List<TFlowUnview>  selectByViewIds(@Param("viewIds")List<String> viewIds);
	
	List<TFlowUnview>  pushSelectList(@Param("viewStatus")String viewStatus,
			@Param("pushFlag")String pushFlag);
	
	int updatePushFlag(@Param("viewId") String viewId,@Param("pushFlag")String pushFlag);
	
	int updateLawKafka(@Param("status") String status);

}