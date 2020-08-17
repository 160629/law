package com.chinatower.product.legalms.modules.flow.service.common;


import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;

import java.util.List;

/**
 * @Date: 2019/12/2 20:26
 * @Description:
 */
public interface OrgBeanService {

    public OrgBeanVO selectOrgInfoByOrgCode(String deptId);

	public ProcessResult selectOrgLevelByorgCode(OrgBeanVO orgBeanVO);

	public ProcessResult selectProvincePathByCode();

	public OrgBeanVO selectProOrgInfoByOrgCode(String deptId);

	/**
	 * 功能描述:查询组织子节点（不包括部门）
	 * @auther: guodong
	 * @param orgCode
	 * @return java.util.List<java.lang.String>
	 * @date: 2020/6/11 20:30
	 */
	public List<String> selectOrgListByOrgCode (String orgCode);

}
