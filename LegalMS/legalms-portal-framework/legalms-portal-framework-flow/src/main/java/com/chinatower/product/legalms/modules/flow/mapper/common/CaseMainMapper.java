package com.chinatower.product.legalms.modules.flow.mapper.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinatower.product.legalms.modules.flow.entity.common.CaseMainVO;

/**
 * @author 刘晓亮
 * @create 2019-11-28
 */
public interface CaseMainMapper {
    List<CaseMainVO> selectCase(@Param("caseIds") String[] caseIds);

    CaseMainVO selectCaseOne(String jointlyCase);
}
