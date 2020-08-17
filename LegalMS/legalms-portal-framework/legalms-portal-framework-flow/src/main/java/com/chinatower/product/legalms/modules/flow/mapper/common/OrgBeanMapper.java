package com.chinatower.product.legalms.modules.flow.mapper.common;


import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2019/12/2 20:29
 * @Description:
 */
public interface OrgBeanMapper {

    OrgBeanVO selectOrgInfoByOrgCode(String deptId);

	OrgBeanVO selectOrgLevel(String companyCode);

	List<OrgBeanVO> selectOrgInfoByWhere(@Param("orgCode") String orgCode,
								         @Param("param") String param);

	OrgBeanVO selectHeadBycurrCode(String currCode);

	OrgBeanVO selectProvincePathByCode(String provinceCode);

	OrgBeanVO selectOrgLevelByorgCode(OrgBeanVO orgBeanVO);

    List<OrgBeanVO> selectOrgListByOrgCode(String orgCode);
}
