package com.chinatower.product.legalms.modules.organization.mapper;

import com.chinatower.product.legalms.common.OrgBeanVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2020/6/10 15:22
 * @Description:
 */
public interface OrgBeanCommonMappper {


    List<OrgBeanVO> selectOrgInfoByOrgCode(String orgCode);

    List<OrgBeanVO> selectOrgInfoLimitByOrgCode(String orgCode);

    List<OrgBeanVO> selectOrgListByOrgCode(String orgCode);

    List<OrgBeanVO> selectCompanyCodeListBySpeCode(@Param("orgCodeList") List<String> orgCodeList);
}
