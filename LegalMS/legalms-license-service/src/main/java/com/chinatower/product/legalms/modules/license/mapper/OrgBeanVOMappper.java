package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.OrgBeanVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrgBeanVOMappper extends Mapper<OrgBeanVO> {

    List<OrgBeanVO> selectAllorgBean();

    int addorgBean(OrgBeanVO orgBeanVO);

    int updateorgBean(OrgBeanVO orgBeanVO);

    int deleteorgBean(OrgBeanVO orgBeanVO);

    int selectAllOrgBeanbyCode(String orgCode);

    void insertOrgBeanbyCode(OrgBeanVO orgBeanVO);

    void updateOrgBeanbyCode(OrgBeanVO orgBeanVO);



    List<OrgBeanVO> selectOrgInfoByCode(String orgCode);

    List<OrgBeanVO> selectOrgTreeByCode(List<String> list);

    List<OrgBeanVO> selectOrgbeanByAreaName();


    List<OrgBeanVO> selectOrgTreeByWhere(@Param("flag") String flag,
                                         @Param("orgLevel") String orgLevel,
                                         @Param("deptId") String currCode,
                                         @Param("enterpType") String enterpType);

    List<OrgBeanVO> selectOrgTreeByCompanyCode(List<String> companyCodeList);

    List<OrgBeanVO> selectHeadCompany(String str);

    List<OrgBeanVO> selectProvinceCompany(String str);


    List<OrgBeanVO> selectCaseReceptInfoByLevel(@Param("roleList") List<String> roleList,
                                                @Param("param") String param,
                                                @Param("orgLevel") String orgLevel,
                                                @Param("superRole") String superRole);

    List<OrgBeanVO> selectBelongUser(@Param("param") String param,
                                     @Param("orgLevel") String orgLevel,
                                     @Param("superRole") String superRole);

    List<OrgBeanVO> selectAllOrgTree(@Param("param") String param,
                                     @Param("orgLevel") String orgLevel,
                                     @Param("superRole") String superRole,
                                     @Param("orgCode") String orgCode);


    OrgBeanVO selectOrgInfoByorgCode(@Param("orgCode")String orgCode,@Param("param")String param);

    List<OrgBeanVO> selectOrgCodeListByOrgCode(String orgCode);

    List<OrgBeanVO> selectOrgInfoList(@Param("orgCodeList") List<String> orgCodeList);

    List<OrgBeanVO> selectUserInfoByOrgCode(@Param("orgCode") String orgCode, @Param("param") String param);

    List<OrgBeanVO> selectUnderOrgInfo(String orgCode);

    List<OrgBeanVO> selectOrgInfoLists(@Param("orgCodeList") List<String> collect);

    List<OrgBeanVO> selectUnderOrgInfoByCase(@Param("orgCode")String orgCode,
                                             @Param("orgLevel")String orgLevel);

    List<OrgBeanVO> selectOrgTreeByLimit(@Param("deptId") String currCode,
                                         @Param("enterpType") String enterpType);

    List<OrgBeanVO> selectNextTopOrgInfo(String orgCode);

    List<OrgBeanVO> selectNextOrgInfo(@Param("orgCode") String orgCode, @Param("param") String param);

    int selectOrgProviderInfoByOrgCode(String serviceCode);

  
}
