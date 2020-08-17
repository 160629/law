package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.depleader.OrgDepLeader;
import com.chinatower.product.legalms.modules.license.entity.depleader.OrgDepTreeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgDepLeaderMapper {

    int deleteLeaderByOrgCode(String orgCode);

    int insert(OrgDepLeader record);

    int insertSelective(OrgDepLeader record);

    int addLeaderInfo (OrgDepLeader record);

    int addOrgDepInfo (OrgDepLeader record);

    OrgDepLeader selectLeaderByOrgcode(@Param("orgCode") String orgCode, @Param("accountName") String accountName);

    List<OrgDepLeader> selectLeaderInfo(OrgDepLeader orgDepLeader);

    int updateLeaderByOrgCode(OrgDepLeader orgDepLeader);

    int updateByPrimaryKey(OrgDepLeader record);

    List<OrgDepTreeVO> selectOrgTree(@Param("deptId") String currCode,
                                     @Param("orgLevel") String orgLevel,
                                     @Param("superRole") String superRole,
                                     @Param("enterpType") String enterpType);

    List<OrgDepTreeVO>  selectLeaderTree (OrgDepLeader orgDepLeader);

    List<OrgDepLeader> selectDeptCodeByAccountId(String loginAcct);

    List<OrgDepLeader> selectAll();

    int initLeaderData(@Param("leadeOrgCode")String leadeOrgCode,
                       @Param("accountId")String accountId,
                       @Param("orgCode")String orgCode);
}