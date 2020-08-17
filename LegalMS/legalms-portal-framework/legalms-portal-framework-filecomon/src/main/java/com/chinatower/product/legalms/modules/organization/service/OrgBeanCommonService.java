package com.chinatower.product.legalms.modules.organization.service;

import java.util.List;

/**
 * @Date: 2020/6/10 15:13
 * @Description:
 */
public interface OrgBeanCommonService {

    /**
     * 功能描述:返回指定公司code组织部门信息
     * @auther: guodong
     * @param orgCode
     * @return java.util.List<java.lang.String>
     * @date: 2020/6/10 15:15
     */
    public List<String> selectOrgInfoByOrgCode (String orgCode);

    /**
     * 功能描述:返回指定code组织部门信息（集约）
     * @auther: guodong
     * @param orgCode
     * @return java.util.List<java.lang.String>
     * @date: 2020/6/10 15:15
     */
    public List<String> selectOrgInfoLimitByOrgCode (String orgCode);


    /**
     * 功能描述: 指定orgCode递归查询子节点（不包括部门）
     * @auther: guodong
     * @param orgCode
     * @return java.util.List<java.lang.String>
     * @date: 2020/6/11 20:30
     */
    public List<String> selectOrgListByOrgCode (String orgCode);

    /**
     * 功能描述: 查询指定分orgCode下所有公司Code (例：河北分公司)
     * @auther: guodong
     * @param
     * @return java.util.List<java.lang.String>
     * @date: 2020/8/8 1:39
     */
    public List<String> selectCompanyCodeListBySpeCode(List<String> orgCodeList);

}
