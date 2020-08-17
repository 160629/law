package com.chinatower.product.legalms.modules.cases.mapper;

import com.chinatower.product.legalms.modules.cases.entity.TIssueLawsuitVO;

import java.util.List;

public interface TIssueLawsuitMapper {

    List<TIssueLawsuitVO> selectAllById(TIssueLawsuitVO tIssueLawsuitVO);
}