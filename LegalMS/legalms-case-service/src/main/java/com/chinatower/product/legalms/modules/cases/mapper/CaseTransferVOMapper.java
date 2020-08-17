package com.chinatower.product.legalms.modules.cases.mapper;


import com.chinatower.product.legalms.modules.cases.entity.CaseStatusVO;
import com.chinatower.product.legalms.modules.cases.entity.CaseTransfer;
import com.chinatower.product.legalms.modules.cases.entity.OrgBeanVO;
import com.chinatower.product.legalms.modules.cases.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CaseTransferVOMapper {
    int deleteByPrimaryKey(String caseId);

    int insert(CaseTransfer record);

    int insertSelective(CaseTransfer record);

    CaseTransfer selectByPrimaryKey(String caseId);

    int updateByPrimaryKeySelective(CaseTransfer record);

    int updateByPrimaryKey(CaseTransfer record);


    int selectInfoBycaseId(String caseid);

    int updateInfoBycaseId(CaseTransfer caseTransfer);


    List<CaseTransfer> selectCaseTransferInfo(CaseTransfer caseTransfer);

    void addCaseTransferInfo(CaseTransfer caseTransfer);

    UserInfo selectUserInfo (@Param("accountId") String accountId);



    List<OrgBeanVO> selectOrgTreeByWhere(@Param("orgLevel") String orgLevel,
                                         @Param("deptId") String currCode,
                                         @Param("superRole") String superRole);

    List<CaseStatusVO> selectLawSuitStatusByCaseId(CaseStatusVO caseStatusVO);

    List<CaseStatusVO> selectJointlyStatusByCaseId(CaseStatusVO caseStatusVO);

    List<CaseStatusVO> selectCaseLegislationStatusByCaseId(CaseStatusVO caseStatusVO);


    List<Map<String, String>> getExcelDate();

    List<Map<String, String>> dictMaps(String dictType);
}