package com.chinatower.product.legalms.modules.cases.mapper;

import com.chinatower.product.legalms.modules.cases.entity.TIssueGuideVO;
import org.apache.ibatis.annotations.Param;

public interface TIssueGuideMapper {

    TIssueGuideVO selectAllById(@Param("guideId") String guideId);
}