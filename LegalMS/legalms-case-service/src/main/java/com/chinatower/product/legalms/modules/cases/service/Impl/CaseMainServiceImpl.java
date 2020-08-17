package com.chinatower.product.legalms.modules.cases.service.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.cases.entity.*;
import com.chinatower.product.legalms.modules.cases.issue.ListParam;
import com.chinatower.product.legalms.modules.cases.mapper.CaseMainMappper;
import com.chinatower.product.legalms.modules.cases.mapper.CaseTransferVOMapper;
import com.chinatower.product.legalms.modules.cases.mapper.FileShareVOMapper;
import com.chinatower.product.legalms.modules.cases.service.CaseMainService;
import com.chinatower.product.legalms.modules.cases.service.RelationshipService;
import com.chinatower.product.legalms.modules.cases.service.TFlowMainService;
import com.chinatower.product.legalms.modules.organization.service.OrgBeanCommonService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Service
public class CaseMainServiceImpl implements CaseMainService {

    @Autowired
    private CaseMainMappper caseMainMappper;

    @Autowired
    private FileShareVOMapper fileShareVOMapper;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private CaseTransferVOMapper caseTransferVOMapper;

    @Autowired
    private TFlowMainService tFlowMainService;

    @Autowired
    OrgBeanCommonService orgBeanCommonService;

    @Override
    public PageInfo<CaseMainVO> selectAllCaseMain(ListParam param) {
        UserInfo userInfo = RequestHolder.getUserInfo();
        OrgBeanVO orgBeanVO = null;
        List<Object> roleCodeList = userInfo.getRoleCodeList();// 获取当前人的角色列表
        String legalRoleCode = getLegalRoleCode(roleCodeList);
        List<String> deptIds = new ArrayList<>();
        String orgCode = userInfo.getOrgId();
        switch (legalRoleCode) {
            case "cjgly":
                // 查询整个集团
                orgCode = "000000";
                orgBeanVO = caseMainMappper.selectOrgInfoByOrgCode(orgCode);
                param.setOrgPath(orgBeanVO.getOrgPath() + "%");
                break;
            case "fwgly":
                orgBeanVO = caseMainMappper.selectOrgInfoByOrgCode(orgCode);
                param.setOrgPath(orgBeanVO.getOrgPath() + "%");
                break;
            case "fw":
                param.setCreatorAccountId(userInfo.getLoginAcct());
                deptIds.add(userInfo.getDeptId());
                break;
            // 其他角色暂无此菜单
//            case "qt":
//                break;
            default:
                return null;
        }

        if (StringUtils.isNotBlank(param.getId())) {
            orgBeanVO = caseMainMappper.selectOrgInfoByOrgCode(param.getId());
            param.setOrgPath(orgBeanVO.getOrgPath() + "%");
        }
        if ("02".equals(userInfo.getOrgLevel())) {
            deptIds = orgBeanCommonService.selectOrgInfoLimitByOrgCode(orgCode);
        } else {
            deptIds = orgBeanCommonService.selectOrgInfoByOrgCode(orgCode);
        }
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<CaseMainVO> caseMainVOS = caseMainMappper.selectAllCaseMain(param, deptIds);
        return new PageInfo<>(caseMainVOS);
    }

    private String getLegalRoleCode(List<Object> roleCodeList) {
        if (roleCodeList.contains("CHNTLEGALMS_0")) {
            // 超级管理员
            return "cjgly";
        } else if (roleCodeList.contains("CHNTLEGALMS_7")) {
            // 法务管理员（系统管理员）
            return "fwgly";
        } else if (roleCodeList.contains("CHNTLEGALMS_17") || roleCodeList.contains("CHNTLEGALMS_13")) {
            // 法务人员（法务审核人），法务专员（法务初审人）
            return "fw";
        } else {
            // 其他角色
            return "qt";
        }
    }

    @Override
    public ProcessResult selectAllCaseMains(ListParam param) {
        UserInfo userInfo = RequestHolder.getUserInfo();
        String superRole = null;
        //判断是否为超级管理员
        List<Object> superAdmin = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_0")).collect(Collectors.toList());
        if (!superAdmin.isEmpty()) {
            superRole = superAdmin.get(0).toString();
        }
        //判断是否为系统管理员
        List<Object> admin = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_7")).collect(Collectors.toList());
        if (admin.isEmpty() && superAdmin.isEmpty()) {
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.NO_ADMIN_POWER);
        }
        List<OrgBeanVO> orgBeanVOS = caseTransferVOMapper.selectOrgTreeByWhere(RequestHolder.getUserInfo().getOrgLevel(), RequestHolder.getCurrCode(), null);
        List<String> collect = orgBeanVOS.stream().map(OrgBeanVO::getCompanyCode).collect(Collectors.toList());
        if(superRole==null ){
            param.setCompanyCodeList(collect);
        }
        if (param.getTypeList() != null) {
            List<String> allList = new ArrayList<>();
            List<String> singleList = new ArrayList<>();
            List<String> OrgCodeList = new ArrayList<>();
            for (int i = 0; i < param.getTypeList().size(); i++) {
                if ("2".equals(param.getTypeList().get(i))) {//2代表*分公司 （省本部和地市的父节点）
                    allList.add(param.getOrgCodeList().get(i));
                } else if ("3".equals(param.getTypeList().get(i))) {//3 代表公司（包括总部 省 地 公司）
                    singleList.add(param.getOrgCodeList().get(i));
                }
            }
            if(!allList.isEmpty()){
                List<String> companyCodeList = orgBeanCommonService.selectCompanyCodeListBySpeCode(allList);
                OrgCodeList.addAll(companyCodeList);
            }
            if(!singleList.isEmpty()){
                OrgCodeList.addAll(singleList);
            }
            if(!OrgCodeList.isEmpty()){
                param.setSingleOrgCodeList(OrgCodeList);
            }
        }
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<CaseMainVO> list = caseMainMappper.selectAllCaseMains(param, null);
        PageInfo<CaseMainVO> pageInfo = new PageInfo<>(list);
        return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SELECT_CASE_MAINS_SUCCESS, pageInfo);
    }



    @Override
    public int addCaseMain(CaseMainVO caseMainVO) {
        return caseMainMappper.addCaseMain(caseMainVO);
    }

    @Override
    public CaseMainVO selectCaseMain(String caseId) {
        return caseMainMappper.selectCaseMainById(caseId);
    }

    @Override
    public String updateExplain(CaseMainVO caseMainVO) {
//        StringBuilder sb = new StringBuilder();
//        RelationshipVO relationshipVO = new RelationshipVO();
//        relationshipVO.setCaseId(caseMainVO.getCaseId());
//        List<RelationshipVO> relationshipVOS = relationshipService.selectRelationship(relationshipVO);
//        for (int i=0;i<relationshipVOS.size();i++){
//            String tFlowMain = tFlowMainService.selectFlowMainById(relationshipVOS.get(i).getBusinessId(), relationshipVOS.get(i).getBusinessType());
//            if (StringUtils.isNotBlank(tFlowMain)) {
//                sb.append("《" + tFlowMain + "》");
//                sb.append(",");
//            }
//        }
//        if (StringUtils.isNotBlank(sb.toString())){
//            sb.append("审批流程未结束");
//            setReplace(sb);
//            return sb.toString();
//        }else{
        String caseId = caseMainVO.getCaseId();
        String caseExplain = caseMainVO.getCaseExplain();
        String caseFinishWay = caseMainVO.getCaseFinishWay();
        Date caseFinishTime = caseMainVO.getCaseFinishTime();
        return caseMainMappper.updateExplain(caseId, caseExplain, caseFinishWay, caseFinishTime) + "";
//        }
    }

    private void setReplace(StringBuilder sb) {
        String sr = sb.toString();
        if (sb.equals(CaseInfo.S_GUIDE)) {
            sr.replace(CaseInfo.S_GUIDE, CaseInfo.C_GUIDE);
        }
        if (sb.equals(CaseInfo.S_LAWSUIT)) {
            sr.replace(CaseInfo.S_LAWSUIT, CaseInfo.C_LAWSUIT);
        }
        if (sb.equals(CaseInfo.S_ASSIGN)) {
            sr.replace(CaseInfo.S_ASSIGN, CaseInfo.C_ASSIGN);
        }
        if (sb.equals(CaseInfo.S_LEGISLATION)) {
            sr.replace(CaseInfo.S_LEGISLATION, CaseInfo.C_LEGISLATION);
        }
        if (sb.equals(CaseInfo.S_JOINTLY)) {
            sr.replace(CaseInfo.S_JOINTLY, CaseInfo.C_JOINTLY);
        }
    }

    @Override
    public int updaterulingClassesById(String caseId, String rulingClasses, String caseReviewGrade) {
        return caseMainMappper.updaterulingClassesById(caseId, rulingClasses, caseReviewGrade);
    }

    @Override
    public int updateCaseMain(CaseMainVO caseMainVO) {
        return caseMainMappper.updateCaseMainById(caseMainVO);
    }

    @Override
    public int deleteCaseMainByIds(String caseId) {
        return caseMainMappper.deleteCaseMainByIds(caseId, CaseInfo.DELETESTATUS);
    }

    @Override
    public String updateCaseStatusById(CaseMainVO caseMainVO) {
        StringBuilder sb = new StringBuilder();
        RelationshipVO relationshipVO = new RelationshipVO();
        relationshipVO.setCaseId(caseMainVO.getCaseId());
        List<RelationshipVO> relationshipVOS = relationshipService.selectRelationship(relationshipVO);
        for (int i = 0; i < relationshipVOS.size(); i++) {
            String tFlowMain = tFlowMainService.selectFlowMainById(relationshipVOS.get(i).getBusinessId(), relationshipVOS.get(i).getBusinessType());
            if (StringUtils.isNotBlank(tFlowMain)) {
                sb.append("《" + tFlowMain + "》");
                sb.append(",");
            }
        }
        if (StringUtils.isNotBlank(sb.toString())) {
            sb.append("审批流程未结束");
            setReplace(sb);
            return sb.toString();
        } else {
            String caseId = caseMainVO.getCaseId();
            String caseExplain = caseMainVO.getCaseExplain();
            String caseFinishWay = caseMainVO.getCaseFinishWay();
            Date caseFinishTime = caseMainVO.getCaseFinishTime();
            int a = caseMainMappper.updateExplain(caseId, caseExplain, caseFinishWay, caseFinishTime);
            String caseStatus = caseMainVO.getCaseStatus();
            a += caseMainMappper.updateCaseStatusById(caseId, caseStatus);
            return a + "";
        }
    }

    @Override
    public List<CaseMainVO> selectAllRelevanceCaseMain(ListParam param) {
        List<CaseMainVO> caseMainVOS = null;
        if (param.getFlag().equals(CaseInfo.FLAG_ONE)) {// 纠纷处理 关联案件
            UserInfo userInfo = RequestHolder.getUserInfo();
            String orgId = userInfo.getOrgId();
            caseMainVOS = caseMainMappper.selectAllRelevanceCaseMain(param, orgId);
        }
        if (param.getFlag().equals(CaseInfo.FLAG_TWO)) { // 法律文书，案件协办 关联案件
            UserInfo userInfo = RequestHolder.getUserInfo();
            String loginAcct = userInfo.getLoginAcct();
            caseMainVOS = caseMainMappper.selectAllRelevanceCaseMains(param, loginAcct);
        }
        if (param.getFlag().equals(CaseInfo.FLAG_THREE)) {// 案件交办 关联案件
            UserInfo userInfo = RequestHolder.getUserInfo();
            String orgId = userInfo.getOrgId();
            caseMainVOS = caseMainMappper.selectAllRelevanceCaseMain2(param, orgId);
        }
        return caseMainVOS;
    }

/*    @Override
    public List<CaseMainVO> selectAllRelevanceCaseMains(ListParam param) {
        UserInfo userInfo = RequestHolder.getUserInfo();
        String loginAcct = userInfo.getLoginAcct();
        List<CaseMainVO> list = caseMainMappper.selectAllRelevanceCaseMains(param,loginAcct);
        return list;
    }*/

    @Override
    public PageInfo<CaseMainVO> selectAllCaseMainByDept(ListParam param) {
        UserInfo userInfo = RequestHolder.getUserInfo();
        List<CaseMainVO> caseMainList = null;
        List<Object> roleCodeList = userInfo.getRoleCodeList();// 获取当前人的角色列表
        String roleLevel = getRoleLevel(roleCodeList);// 判断并获取当前人角色权限
        OrgBeanVO orgBeanVO = null;
        switch (roleLevel) {
            case "whole":
                if (StringUtils.isNotBlank(param.getId())) {
                    orgBeanVO = caseMainMappper.selectOrgInfoByOrgCode(param.getId());
                    param.setOrgPath(orgBeanVO.getOrgPath() + "%");
                } else {
                    orgBeanVO = caseMainMappper.selectOrgInfoByOrgCode("000000");// 集团 最高级
                    param.setOrgPath(orgBeanVO.getOrgPath() + "%");
                }
                break;
            case "all":
                if (StringUtils.isNotBlank(param.getId())) {
                    orgBeanVO = caseMainMappper.selectOrgInfoByOrgCode(param.getId());
                    param.setOrgPath(orgBeanVO.getOrgPath() + "%");
                } else {
                    orgBeanVO = caseMainMappper.selectOrgInfoByOrgCode(userInfo.getOrgId());
                    String path = orgBeanVO.getOrgPath();
                    if ("01".equals(userInfo.getOrgLevel()) || "02".equals(userInfo.getOrgLevel())) {
                        path = path.substring(0, orgBeanVO.getOrgPath().length() - 1);
                        path = path.substring(0, path.lastIndexOf(','));// 解析出省份公司组织树
                    }
                    param.setOrgPath(path + "%");
                }
                break;
            case "bmfzr":
                orgBeanVO = caseMainMappper.selectOrgInfoByOrgCode(userInfo.getDeptId());
                param.setOrgPath(orgBeanVO.getOrgPath() + "%");
                break;
            case "mr":
                param.setInvolvedAccountId(userInfo.getLoginAcct());
                param.setInvolvedAccountName(userInfo.getLoginName());
                break;
            default:
                break;
        }
        PageHelper.startPage(param.getPageNum(), param.getPageSize());// 分页对象  分页查询
        caseMainList = caseMainMappper.selectAllCase(param);
        return new PageInfo<>(caseMainList);
//        String orgLevel = userInfo.getOrgLevel();
//        if(orgLevel.equals(CaseInfo.ORG_LEVEL_1)){
//            String unitCode = userInfo.getUnitCode();
//            if(unitCode.equals(CaseInfo.UNIT_CODE_A)){
//                unitCode=CaseInfo.UNIT_CODE_1;
//            }else if(unitCode.equals(CaseInfo.UNIT_CODE_B)){
//                unitCode=CaseInfo.UNIT_CODE_2;
//            }else if(unitCode.equals(CaseInfo.UNIT_CODE_C)){
//                unitCode=CaseInfo.UNIT_CODE_3;
//            }
//            return caseMainMappper.selectAllCaseMainByDeptA(unitCode,param);
//        }else if (orgLevel.equals(CaseInfo.ORG_LEVEL_2)){
//            String orgId =userInfo.getOrgId();
//            return caseMainMappper.selectAllCaseMainByDeptB(orgId,param);
//        }else if (orgLevel.equals(CaseInfo.ORG_LEVEL_3)){
//            String orgId =userInfo.getOrgId();
//            return caseMainMappper.selectAllCaseMainByDeptC(orgId,param);
//        }else{
//            return null;
//        }

    }

    public String getRoleLevel(List<Object> roleCodeList) {
        String[] selectAllRoleList = {
                "CHNTLEGALMS_13",//法务初审人（法务专员）
                "CHNTLEGALMS_17",//法务审核人（法务人员）
                "CHNTLEGALMS_33",//法务部门负责人
                "CHNTLEGALMS_23",//董事长
                "CHNTLEGALMS_39",//公司总经理
                "CHNTLEGALMS_7",//系统管理员
                "CHNTLEGALMS_16",//地市法务接口人
                "CHNTLEGALMS_36"//地市法务接口人

        };
        List<String> list = Arrays.asList(selectAllRoleList);
        String role = null;
        if (roleCodeList.contains("CHNTLEGALMS_0")) {// 超级管理员
            return "whole";
        }
        for (int i = 0; i < roleCodeList.size(); i++) {
            role = (String) roleCodeList.get(i);
            if (list.contains(role)) {
                return "all";
            }
        }
        if (roleCodeList.contains("CHNTLEGALMS_43")) {
            return "bmfzr";
        }
        return "mr";
    }

    @Override
    public int selectAllCaseMainByCount(String caseId) {
        return caseMainMappper.selectAllCaseMainByCount(caseId);
    }

    @Override
    @Transactional
    public int addAutoCaseMain(CaseMainAutoVO caseMainAutoVO) {
        //获取用户信息
        caseMainAutoVO.setDrafterUnit(caseMainAutoVO.getCreatorUnitId());
        caseMainAutoVO.setCreatorAccountName(caseMainAutoVO.getCreatorAccountName());
        caseMainAutoVO.setCreatorAccountId(caseMainAutoVO.getCreatorAccountId());
        caseMainAutoVO.setCreatorCell(caseMainAutoVO.getCreatorCell());
        caseMainAutoVO.setCreatorDeptId(caseMainAutoVO.getCreatorDeptId());
        caseMainAutoVO.setCreatorDeptName(caseMainAutoVO.getCreatorDeptName());
        caseMainAutoVO.setCreatorUnitId(caseMainAutoVO.getCreatorUnitId());
        caseMainAutoVO.setCreatorUnitName(caseMainAutoVO.getCreatorUnitName());
        caseMainAutoVO.setCaseStatus(CaseInfo.ZD_STATUS);
        caseMainAutoVO.setRulingClasses(CaseInfo.ZD_RULING_CLASSES_2);


        //添加到卷宗关联表
        RelationshipVO relationshipVO = new RelationshipVO();
        relationshipVO.setBusinessId(caseMainAutoVO.getBusinessId());
        relationshipVO.setBusinessType(caseMainAutoVO.getBusinessType());
        relationshipVO.setCaseId(caseMainAutoVO.getCaseId());
        relationshipVO.setTittle(caseMainAutoVO.getCaseTitle());
        relationshipVO.setModuleName(caseMainAutoVO.getModuleName());
        relationshipVO.setIsAuto(caseMainAutoVO.getIsAuto());
        relationshipVO.setIsDelete("0");
        relationshipVO.setCode(caseMainAutoVO.getCode());
        relationshipService.addRelationship(relationshipVO);
        /**
         if(StringUtils.isNotBlank(caseMainAutoVO.getCaseFile())){
         String[] arr = caseMainAutoVO.getCaseFile().split(",");
         for(int i=0;i<arr.length;i++){
         if(StringUtils.isNotBlank(arr[i])){
         String shareid = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong();
         FileShareVO fileShareVO = new FileShareVO();
         fileShareVO.setFileShareBusinessKey(caseMainAutoVO.getCaseId());
         fileShareVO.setFileId(arr[i]);
         fileShareVO.setFileShareType("casemain");
         fileShareVO.setFileShareId(shareid);

         fileShareVOMapper.insertFileShare(fileShareVO);
         }
         }
         }
         **/
        return caseMainMappper.addAutoCaseMain(caseMainAutoVO);
    }

    @Override
    public Map<String, String> getDictMap(String dictType) {
        List<Map<String, String>> dictMaps = caseMainMappper.dictMaps(dictType);
        Map<String, String> hashMap = new HashMap<>(dictMaps.size());
        for (Map<String, String> map : dictMaps) {
            hashMap.put(map.get("dict_value"), map.get("dict_cabel"));
        }
        return hashMap;
    }

    @Override
    public List<String> selectCode(String unitCode, String codeNull, String orgCode) {
        return caseMainMappper.selectCode(unitCode, codeNull, orgCode);
    }

    @Override
    @Transactional
    public int deleteCaseMainByIdsArray(List<CaseMainVO> caseMainVOS) {
        caseMainVOS.forEach(x -> x.setCaseStatus("5"));
        return caseMainMappper.deleteCaseMainByIdsArray(caseMainVOS);
    }

    @Override
    @Transactional
    public int nullifyCaseByIdsArray(String oldCaseId, String newCaseId) {
        // 查询与作废的卷宗相关关联的业务表单
        List<RelationshipVO> relationshipVOS = null ;
        if (StringUtils.isNotBlank(oldCaseId)) {
            relationshipVOS = relationshipService.selectRelationshipByCaseIds(oldCaseId);
        }
        // 查询新关联卷宗详情
        CaseMainVO caseMainVO = null;
        if (StringUtils.isNotBlank(newCaseId)) {
            caseMainVO = caseMainMappper.selectCaseMainById(newCaseId);
        }
        int i = 0;
        if (relationshipVOS != null && !relationshipVOS.isEmpty() && caseMainVO != null) {
            // 更新相关联业务表
            caseMainMappper.updateInvolveBusinessTablesByRelationshipVOS(relationshipVOS, caseMainVO);

            // 更新关联关系
            relationshipVOS.forEach(x -> {
                x.setIsDelete("1");
                x.setIsAuto("0");
            });
            relationshipService.updateRelationshipsCaseId(relationshipVOS, newCaseId);
        }
        // 删除作废卷宗
        CaseMainVO caseMainVO2 = new CaseMainVO();
        caseMainVO2.setCaseId(oldCaseId);
        caseMainVO2.setCaseStatus("4");
        ArrayList<CaseMainVO> caseMainVOS = new ArrayList<>();
        caseMainVOS.add(caseMainVO2);
        i = caseMainMappper.deleteCaseMainByIdsArray(caseMainVOS);
        return i;
    }

    @Override
    public List<CaseMainVO> selectNewCaseMainList(NewCaseListParam param) {
        return caseMainMappper.selectNewCaseMainList(param);
    }


    @Override
    public List<Map<String, Object>> getExcelDate() {
        List<Map<String, String>> list = caseMainMappper.getExcelDate();
        Map<String, String> resultMap = getDictMap("sys_case_type");
        Map<String, String> resultMap1 = getDictMap("sys_case_status");
        List<Map<String, Object>> excels = new ArrayList<>(list.size());
        for (Map<String, String> map : list) {
            Map<String, Object> map1 = new LinkedHashMap<>();
            map1.put("案件标题", map.get("caseTitle"));
            map1.put("案件编号", map.get("caseCode"));
            map1.put("对方诉讼主体", map.get("otherLawsuitBody"));
            map1.put("我方争议主体", map.get("ourLawsuitBodyName"));
            String str = map.get("deputeType");
            String deputeType = null == str ? "" : resultMap.get(str);
            map1.put("案件类型", deputeType);
            map1.put("争议金额(元)", map.get("caseDeputeMoney"));
            map1.put("最后更新时间", map.get("lastUpdateTime"));
            String str1 = map.get("caseStatus");
            String caseStatus = null == str1 ? "" : resultMap1.get(str1);
            map1.put("案件状态", caseStatus);
            excels.add(map1);
        }
        return excels;
    }

}
