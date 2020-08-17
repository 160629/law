package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.OrgInfoBeanVO;
import org.apache.ibatis.annotations.Param;

public interface OrgInfoBeanVOMappper {
    Integer selectAllOrgInfoBean(@Param("orgCode") String orgCode);

    void addOrginfoBean(OrgInfoBeanVO orgInfoBeanVO);

    int updateOrginfoBean(OrgInfoBeanVO orgInfoBeanVO);

}
