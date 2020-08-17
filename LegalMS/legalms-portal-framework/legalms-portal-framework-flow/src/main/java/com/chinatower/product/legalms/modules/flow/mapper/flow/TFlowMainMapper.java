package com.chinatower.product.legalms.modules.flow.mapper.flow;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowMain;
import com.chinatower.product.legalms.modules.flow.vo.common.RelationshipVO;

public interface TFlowMainMapper {
    int deleteByPrimaryKey(Long flowId);

    int insert(TFlowMain record);

    int insertSelective(TFlowMain record);

    TFlowMain selectByPrimaryKey(Long flowId);
    
    List<TFlowMain> selectList();

    int updateByPrimaryKeySelective(TFlowMain record);

    int updateByPrimaryKey(TFlowMain record);
    
    //卷宗关联表增加数据
    int addRelationship(RelationshipVO relationshipVO);
    //卷宗关联表修改数据
    int updateRelationship(RelationshipVO relationshipVO);

    TFlowMain selectByBusinessId(String id);

}