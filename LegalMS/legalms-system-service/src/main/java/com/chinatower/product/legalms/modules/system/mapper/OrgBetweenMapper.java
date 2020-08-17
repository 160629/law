package com.chinatower.product.legalms.modules.system.mapper;

import com.chinatower.product.legalms.modules.system.entity.OrgInfoBeanVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgBetweenMapper {
    List<OrgInfoBeanVO> selectOrgBeanByCode(@Param("org_code") String orgCode);
}
