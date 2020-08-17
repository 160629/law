package com.chinatower.product.legalms.modules.license.service.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.license.entity.OrgBeanVO;
import com.chinatower.product.legalms.modules.license.entity.OrgTreeVO;
import com.chinatower.product.legalms.modules.license.entity.depleader.OrgDepLeader;
import com.chinatower.product.legalms.modules.license.entity.floworgtree.CommenPermission;
import com.chinatower.product.legalms.modules.license.mapper.OrgBeanVOMappper;
import com.chinatower.product.legalms.modules.license.service.CommonOrgTreeService;
import com.chinatower.product.legalms.modules.license.service.OrgDepLeaderService;
import com.chinatower.product.legalms.modules.license.service.OrgTreeCommonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Date: 2019/11/27 14:40
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class OrgTreeCommonServiceImpl implements OrgTreeCommonService {

    @Autowired
    private OrgBeanVOMappper orgBeanVOMappper;

    @Autowired
    private CommonOrgTreeService commonOrgTreeService;

    @Autowired
    private OrgDepLeaderService orgDepLeaderService;


    public static final String ORDERBY = "999999";

    public static final String INFO = "获取人员组织信息失败";


    @Override
    public ProcessResult orgTreeCommonService(CommenPermission commenPermission) {
//        1 纠纷处理综合查询所属公司/ 案件卷宗查询涉案单位/部门（异步）
//        2 协办/转办综合查询页面所属公司;协助执行综合查询执行单位
//        3 案件交办起草抄送单位
//        4 案件协办起草协办部门 法律文书起草执行部门（异步）
//        5 案件交办起草主送单位
//        6 案件交办综合查询转出单位
//        7 案件交办综合查询主送单位
//        8 集约化查询；
//        9 本级及下级单位 10 本级及上级单位 11 所有单位
//        12 卷宗管理列表涉案单位/部门（异步）
//        13 协办起草协办单位，法律文书起草执行单位  协助执行执行单位（带flowid）
//        14 法律支撑咨询单位/部门（异步）
//        15人员短信设置组织查询
//        16 协助执行执行单位
//        17 协助执行综合查询起草单位
        String flag = commenPermission.getFlag();
        String param = commenPermission.getParam();
        String orgCode = commenPermission.getOrgCode();
        String flowId = commenPermission.getFlowId();
        UserInfo userInfo = RequestHolder.getUserInfo();
        String currCode = userInfo.getDeptId();
        String orgLevel = userInfo.getOrgLevel();
        String unitCode = userInfo.getUnitCode();
        String enterpType = RequestHolder.getenterpType(unitCode);
        switch (flag) {
            case "14":
            case "1":
                List<OrgBeanVO> list = getOrgTree(flag, userInfo, commenPermission);
                List<OrgBeanVO> orgBeanVOS = buildResultInfo(orgCode, list);
                List<OrgTreeVO> orgTree = responseOrgTree(orgBeanVOS,true);
                return new ProcessResult(ProcessResult.SUCCESS, "", orgTree);
            case "8":
                commenPermission.setParam("1");
                return commonOrgTreeService.selectUserOrgTreeInfo(commenPermission);
            case "15":
                commenPermission.setParam("2");
                return commonOrgTreeService.selectUserOrgTreeInfo(commenPermission);
            case "12":
                commenPermission.setParam("1");
                return commonOrgTreeService.selectUserOrgTreeInfoByCase(commenPermission);
            case "16":
                List<OrgBeanVO> executeOrgList = buildExecuteList("2", currCode, orgLevel, enterpType);
                List<OrgTreeVO> executeResult = responseOrgTree(executeOrgList,false);
                return new ProcessResult(ProcessResult.SUCCESS, "", executeResult.stream().sorted(Comparator.comparing(OrgTreeVO::getId)));
            case "17":
                List<OrgBeanVO> draftOrgList = buildDraftList("2", currCode, orgLevel, enterpType);
                List<OrgTreeVO> draftResult = responseOrgTree(draftOrgList,false);
                return new ProcessResult(ProcessResult.SUCCESS, "", draftResult.stream().sorted(Comparator.comparing(OrgTreeVO::getId)));
            case "2":
                List<OrgBeanVO> orgList = buildResultList(flag, currCode, orgLevel, enterpType);
                List<OrgTreeVO> companyResult = responseOrgTree(orgList,false);
                return new ProcessResult(ProcessResult.SUCCESS, "", companyResult.stream().sorted(Comparator.comparing(OrgTreeVO::getId)));
            case "3":
                String org = StringUtils.isNotBlank(orgCode) ? orgCode:currCode;
                List<OrgBeanVO> orgListThree = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, org, enterpType);
                List<OrgTreeVO> deptResult = responseOrgTree(orgListThree,false);
                return new ProcessResult(ProcessResult.SUCCESS, "", deptResult);
            case "4":
                String orgCodes = StringUtils.isNotBlank(orgCode) ? orgCode:currCode;
                List<OrgBeanVO> orgListFour = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, orgCodes, enterpType).stream().filter(x -> !x.getOrgName().contains(SystemInfo.DEPT_NAME_LEADER)).collect(Collectors.toList());
                return new ProcessResult(ProcessResult.SUCCESS, "", responseOrgTree(orgListFour,false));
            case "5":
                ProcessResult caseAssignResult = buildCaseAssignInfo(param, currCode, userInfo, enterpType);
                if (caseAssignResult != null) return caseAssignResult;
                break;
            case "6":
                String provinceCode = RequestHolder.getProvinceCode(currCode);
                List<OrgBeanVO> orgListSix = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, provinceCode, enterpType);
                List<OrgTreeVO> outResult = responseOrgTree(orgListSix,false);
                return new ProcessResult(ProcessResult.SUCCESS, "", outResult);
            case "7":
                List<OrgBeanVO> orgListSeven = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
                List<OrgTreeVO> mainResult = responseOrgTree(orgListSeven,false);
                return new ProcessResult(ProcessResult.SUCCESS, "", mainResult);
            case "9":
                List<OrgBeanVO> orgListNine = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
                List<OrgTreeVO> downResult = responseOrgTree(orgListNine,false);
                return new ProcessResult(ProcessResult.SUCCESS, "", downResult);
            case "10":
                List<OrgBeanVO> orgListTen = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
                ProcessResult upResult = buildUpResultInfo(currCode, orgListTen, enterpType, null);
                if (upResult != null) return upResult;
                break;
            case "11":
                List<OrgBeanVO> orgListEleven = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
                List<OrgTreeVO> allResult = responseOrgTree(orgListEleven,false);
                return new ProcessResult(ProcessResult.SUCCESS, "", allResult);
            case "13":
                if (flowId.endsWith(SystemInfo.FLOWID_HEAD)) {
                    List<OrgBeanVO> orgListHead = orgBeanVOMappper.selectOrgTreeByWhere("9", orgLevel, currCode, enterpType);
                    return new ProcessResult(ProcessResult.SUCCESS, "", responseOrgTree(orgListHead,false));
                }
                if (flowId.endsWith(SystemInfo.FLOWID_PROVINCE)) {
                    List<OrgBeanVO> orgListProvince = orgBeanVOMappper.selectOrgTreeByWhere("11", orgLevel, currCode, enterpType);
                    return new ProcessResult(ProcessResult.SUCCESS, "", responseOrgTree(orgListProvince,false));
                }
                if (flowId.endsWith(SystemInfo.FLOWID_CITY)) {
                    List<OrgBeanVO> orgListCity = orgBeanVOMappper.selectOrgTreeByWhere("10", orgLevel, currCode, enterpType);
                    ProcessResult upResults = buildUpResultInfo(currCode, orgListCity, enterpType, param);
                    if (upResults != null) return upResults;
                }
                break;
            default:
                log.error("组织查询参数无法匹配");

        }
        /*无条件查全部组织*/
        List<OrgBeanVO> orgList = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
        List<OrgTreeVO> orgTree = responseOrgTree(orgList,false);
        return new ProcessResult(ProcessResult.SUCCESS, "", orgTree);
    }

    private List<OrgBeanVO> buildExecuteList(String flag, String currCode, String orgLevel, String enterpType) {
        List<OrgBeanVO> orgList = new ArrayList<>();
        if (SystemInfo.ORG_LEVEL_ONE.equals(orgLevel)) {
            orgList = orgBeanVOMappper.selectOrgTreeByWhere("9", orgLevel, currCode, enterpType);
        } else if (SystemInfo.ORG_LEVEL_TWO.equals(orgLevel)) {
            orgList = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
            List<OrgBeanVO> orgTreelist = buildHeadTrees(currCode);
            orgList.addAll(orgTreelist);
        } else if (SystemInfo.ORG_LEVEL_THREE.equals(orgLevel)) {
            orgList = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
            List<OrgBeanVO> orgTree = buildProvinceInfos(currCode, enterpType);
            orgList.addAll(orgTree);
        }
        return orgList;
    }

    private List<OrgBeanVO> buildResultList(String flag, String currCode, String orgLevel, String enterpType) {
        List<OrgBeanVO> orgList = new ArrayList<>();
        if (SystemInfo.ORG_LEVEL_ONE.equals(orgLevel)) {
            orgList = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
        } else if (SystemInfo.ORG_LEVEL_TWO.equals(orgLevel)) {
            orgList = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
            List<OrgBeanVO> orgTreelist = buildHeadTrees(currCode);
            orgList.addAll(orgTreelist);
        } else if (SystemInfo.ORG_LEVEL_THREE.equals(orgLevel)) {
            orgList = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
            List<OrgBeanVO> orgTree = buildProvinceInfos(currCode, enterpType);
            orgList.addAll(orgTree);
        }
        return orgList;
    }

    private List<OrgBeanVO> buildDraftList(String flag, String currCode, String orgLevel, String enterpType) {
        List<OrgBeanVO> orgList = new ArrayList<>();
        if (SystemInfo.ORG_LEVEL_ONE.equals(orgLevel)) {
            orgList = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
        } else if (SystemInfo.ORG_LEVEL_TWO.equals(orgLevel)) {
            orgList = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
            List<OrgBeanVO> orgTreelist = buildHeadTrees(currCode);
            orgList.addAll(orgTreelist);
        } else if (SystemInfo.ORG_LEVEL_THREE.equals(orgLevel)) {
            orgList = orgBeanVOMappper.selectOrgTreeByWhere(flag, orgLevel, currCode, enterpType);
            List<OrgBeanVO> orgTree = buildProvinceInfoByDraft(currCode, enterpType);
            orgList.addAll(orgTree);
        }
        return orgList;
    }

    private List<OrgBeanVO> buildResultInfo(String orgCode, List<OrgBeanVO> list) {
        List<OrgBeanVO> orgBeanVOS = new ArrayList<>();
        if (StringUtils.isEmpty(orgCode)) {
            orgBeanVOS = orgBeanVOMappper.selectOrgTreeByCode(Arrays.asList(list.get(0).getOrgParentCode()));
        } else {
            List<String> collect = list.stream().map(OrgBeanVO::getOrgCode).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                orgBeanVOS = orgBeanVOMappper.selectOrgTreeByCode(collect);
            }
        }
        return orgBeanVOS;
    }


    private List<OrgBeanVO> getOrgTree(String flag, UserInfo info, CommenPermission commenPermission) {
        List<OrgBeanVO> list = new ArrayList<>();
        if ("14".equals(flag)) {
            list = getAll(flag, info, commenPermission);
        } else if ("1".equals(flag)) {
            List<Object> roleCodeList = info.getRoleCodeList();// 获取当前人的角色列表
            String roleLevel = getRoleLevel(roleCodeList);// 判断并获取当前人角色权限
            switch (roleLevel) {
                case "all"://所有
                    list = getAll(flag, info, commenPermission);
                    break;
                case "fgld":// 分管部门
                    buildOrgDpetResult(info, list, commenPermission);
                    break;
                case "bmry":// 自己部门
                case "mr":// 自己
                    buildOtherResult(info, list, commenPermission);
                    break;
                default:
            }
        }

        return list;
    }

    private void buildOtherResult(UserInfo info, List<OrgBeanVO> list, CommenPermission commenPermission) {
        if (StringUtils.isEmpty(commenPermission.getOrgCode())) {
            OrgBeanVO orgBeanVO = new OrgBeanVO();
            orgBeanVO.setOrgParentCode(info.getOrgId());
            orgBeanVO.setOrgName(info.getOrgName());
            //"4"代表部门
            orgBeanVO.setOrgLevel(SystemInfo.ORG_LEVEL_THREE);
            list.add(orgBeanVO);
        } else {
            OrgBeanVO orgBeanVO = new OrgBeanVO();
            orgBeanVO.setOrgCode(info.getDeptId());
            orgBeanVO.setOrgName(info.getDeptName());
            //"4"代表部门
            orgBeanVO.setOrgLevel(SystemInfo.ORG_LEVEL_FOUR);
            list.add(orgBeanVO);
        }
    }

    private void buildOrgDpetResult(UserInfo info, List<OrgBeanVO> list, CommenPermission commenPermission) {
        if (StringUtils.isEmpty(commenPermission.getOrgCode())) {
            OrgBeanVO orgBean = new OrgBeanVO();
            orgBean.setOrgParentCode(info.getOrgId());
            orgBean.setOrgName(info.getOrgName());
            //"3"代表公司
            orgBean.setOrgLevel(SystemInfo.ORG_LEVEL_THREE);
            list.add(orgBean);
        } else {
            List<OrgDepLeader> orgDepLeaders = orgDepLeaderService.selectDeptCodeByAccountId(info.getLoginAcct());
            for (int i = 0; i < orgDepLeaders.size(); i++) {
                OrgBeanVO orgBeanVO = new OrgBeanVO();
                orgBeanVO.setOrgCode(orgDepLeaders.get(i).getOrgCode());
                orgBeanVO.setOrgName(orgDepLeaders.get(i).getOrgName());
                orgBeanVO.setOrgParentCode(orgDepLeaders.get(i).getOrgParentCode());
                //"4"代表部门
                orgBeanVO.setOrgLevel(SystemInfo.ORG_LEVEL_FOUR);
                list.add(orgBeanVO);
            }
        }
    }

    private List<OrgBeanVO> getAll(String flag, UserInfo info, CommenPermission commenPermission) {
        List<OrgBeanVO> list = null;
        String deptId = info.getDeptId();
        String orgLevel = info.getOrgLevel();
        String orgId = info.getOrgId();
        String enterpType = RequestHolder.getenterpType(info.getUnitCode());
        if (StringUtils.isEmpty(commenPermission.getOrgCode())) {
            //根据用户信息判断当前登录人权限级别
            list = buildUpOrgInfo(flag, commenPermission, list, orgLevel, deptId, orgId, enterpType);
        } else {
            list = orgBeanVOMappper.selectUnderOrgInfo(commenPermission.getOrgCode());
        }
        return list;
    }


    private List<OrgBeanVO> buildUpOrgInfo(String flag, CommenPermission commenPermission, List<OrgBeanVO> list, String orgLevel, String deptId, String orgId, String enterpType) {
        if (StringUtils.isEmpty(commenPermission.getOrgCode())) {
            list = buildAreaInfo(flag, list, orgLevel, deptId, orgId, enterpType, null);
        }
        return list;
    }

    private List<OrgBeanVO> buildAreaInfo(String flag, List<OrgBeanVO> list, String orgLevel, String deptId, String orgId, String enterpType, String superRole) {
        if ("14".equals(flag)) {
            list = orgBeanVOMappper.selectAllOrgTree(enterpType, "01", superRole, null);
        } else if ("1".equals(flag)) {
            switch (orgLevel) {
                case SystemInfo.ORG_LEVEL_ONE:// 总部
                    list = orgBeanVOMappper.selectAllOrgTree(enterpType, orgLevel, superRole, null);
                    break;
                case SystemInfo.ORG_LEVEL_TWO:// 省份
                    list = orgBeanVOMappper.selectAllOrgTree(enterpType, orgLevel, superRole, orgId);
                    break;
                case SystemInfo.ORG_LEVEL_THREE:// 地市
                    list = orgBeanVOMappper.selectAllOrgTree(null, orgLevel, superRole, deptId);
                    break;
                default:
                    log.error(INFO);
            }
        }

        return list;
    }


    private ProcessResult buildCaseAssignInfo(String param, String currCode, UserInfo userInfo, String enterpType) {
        String orgLevel = userInfo.getOrgLevel();
        /*判断是总部获取省份*/
        if (SystemInfo.ORG_LEVEL_ONE.equals(orgLevel)) {
            List<OrgBeanVO> orgTreelist = new ArrayList<>();
            /*单独构造省份部门*/
            List<OrgBeanVO> provinceTree = orgBeanVOMappper.selectOrgTreeByWhere("02", null, currCode, enterpType);
            for (int i = 0; i < provinceTree.size(); i++) {
                orgTreelist.add(provinceTree.get(i));
            }
            //构造当前人归属单位组织树
            List<OrgTreeVO> orgTree = responseOrgTree(orgTreelist,false);
            return new ProcessResult(ProcessResult.SUCCESS, "", orgTree);
        }
        /*判断是省份获取地市*/
        if (SystemInfo.ORG_LEVEL_TWO.equals(orgLevel)) {
            List<OrgBeanVO> orgTreelist = new ArrayList<>();
            /*单独构造地市部门  把集约地市给屏蔽掉*/
            List<OrgBeanVO> cityTree = orgBeanVOMappper.selectOrgTreeByLimit(currCode, enterpType);
            for (int i = 0; i < cityTree.size(); i++) {
                orgTreelist.add(cityTree.get(i));
            }
            //案件交办省分法务人员处理环节特殊处理
            if (StringUtils.isNotBlank(param) && "sffwrycl".equals(param)) {
                OrgBeanVO orgBeanVO = new OrgBeanVO();
                orgBeanVO.setOrgCode(userInfo.getOrgId());
                orgBeanVO.setOrgLevel(userInfo.getOrgLevel());
                orgBeanVO.setOrgName(userInfo.getOrgName());
                orgTreelist.add(orgBeanVO);
            }
            //构造当前人归属单位组织树
            List<OrgTreeVO> orgTree = responseOrgTree(orgTreelist,false);
            return new ProcessResult(ProcessResult.SUCCESS, "", orgTree.stream().sorted(Comparator.comparing(OrgTreeVO::getId)));
        }
        return null;
    }


    private ProcessResult buildUpResultInfo(String currCode, List<OrgBeanVO> orgList, String enterpType, String param) {
        String orgLevel = orgList.get(0).getOrgLevel();
        //省份级别
        if (orgLevel.equals(SystemInfo.ORG_LEVEL_TWO)) {
            List<OrgTreeVO> orgTree = getProvinceInfos(currCode, enterpType);
            return new ProcessResult(ProcessResult.SUCCESS, "", orgTree);
        }
        //地市级别
        if (orgLevel.equals(SystemInfo.ORG_LEVEL_THREE)) {
            List<OrgTreeVO> orgTree = getCityInfos(currCode, enterpType, param);
            return new ProcessResult(ProcessResult.SUCCESS, "", orgTree);
        }
        return null;
    }

    private List<OrgBeanVO> buildProvinceInfoByDraft(String currCode, String enterpType) {
        List<OrgBeanVO> orgTreelist = new ArrayList<>();
        /*单独构造省份部门*/
        List<OrgBeanVO> provinceTree = orgBeanVOMappper.selectOrgTreeByWhere("3", null, RequestHolder.getProvinceCode(currCode), enterpType);
        for (int i = 0; i < provinceTree.size(); i++) {
            if (!SystemInfo.ORG_LEVEL_FOUR.equals(provinceTree.get(i).getOrgLevel())) {
                orgTreelist.add(provinceTree.get(i));
            }
        }
        //构造当前人归属单位组织树
        return orgTreelist;
    }

    private List<OrgBeanVO> buildProvinceInfos(String currCode, String enterpType) {
        /*单独构造总部部门*/
        List<OrgBeanVO> orgTreelist = buildHeadTrees(currCode);
        if (orgTreelist.isEmpty()) {
            responseOrgTree(orgTreelist,false);
        }
        /*单独构造省份部门*/
        List<OrgBeanVO> provinceTree = orgBeanVOMappper.selectOrgTreeByWhere("3", null, RequestHolder.getProvinceCode(currCode), enterpType);
        for (int i = 0; i < provinceTree.size(); i++) {
            if (!SystemInfo.ORG_LEVEL_FOUR.equals(provinceTree.get(i).getOrgLevel())) {
                orgTreelist.add(provinceTree.get(i));
            }
        }
        //构造当前人归属单位组织树
        return orgTreelist;
    }

    private List<OrgTreeVO> getProvinceInfos(String currCode, String enterpType) {
        /*单独构造总部部门*/
        List<OrgBeanVO> orgTreelist = buildHeadTrees(currCode);
        if (orgTreelist.isEmpty()) {
            responseOrgTree(orgTreelist,false);
        }
        /*单独构造省份部门*/
        List<OrgBeanVO> provinceTree = orgBeanVOMappper.selectOrgTreeByWhere("3", null, currCode, enterpType);
        for (int i = 0; i < provinceTree.size(); i++) {
            if (!SystemInfo.ORG_LEVEL_FOUR.equals(provinceTree.get(i).getOrgLevel())) {
                orgTreelist.add(provinceTree.get(i));
            }
        }
        //构造当前人归属单位组织树
        return responseOrgTree(orgTreelist,false);
    }


    private List<OrgTreeVO> getCityInfos(String currCode, String enterpType, String param) {
        List<OrgBeanVO> orgTreelist = new ArrayList<>();
        /*单独构造省份部门*/
        List<OrgBeanVO> provinceTree = orgBeanVOMappper.selectOrgTreeByCompanyCode(Arrays.asList(RequestHolder.getProvinceCode(currCode)));
        for (int i = 0; i < provinceTree.size(); i++) {
            if (!SystemInfo.ORG_LEVEL_FOUR.equals(provinceTree.get(i).getOrgLevel())) {
                orgTreelist.add(provinceTree.get(i));
            }
        }
        List<OrgBeanVO> cityTree = orgBeanVOMappper.selectOrgTreeByWhere("3", null, currCode, enterpType);
        for (int i = 0; i < cityTree.size(); i++) {
            if (!SystemInfo.ORG_LEVEL_FOUR.equals(cityTree.get(i).getOrgLevel())) {
                orgTreelist.add(cityTree.get(i));
            }
        }
        //构造当前人归属单位组织树
        return responseOrgTree(SystemInfo.FLOW_LINK_FWRYZS.equals(param) ? orgTreelist.stream().filter(x -> !SystemInfo.ORG_LEVEL_THREE.equals(x.getOrgLevel())).collect(Collectors.toList()) : orgTreelist,false);
    }


    private List<OrgBeanVO> buildHeadTrees(String currCode) {
        List<OrgBeanVO> orgTreelist = new ArrayList<>();
        List<String> companyCode = new ArrayList<>();
        List<OrgBeanVO> result = orgBeanVOMappper.selectHeadCompany(currCode);
        for (int i = 0; i < result.size(); i++) {
            companyCode.add(result.get(i).getCompanyCode());
        }
        if (companyCode.isEmpty()) {
            return orgTreelist;
        }
        List<OrgBeanVO> headTree = orgBeanVOMappper.selectOrgTreeByCompanyCode(companyCode);
        for (int i = 0; i < headTree.size(); i++) {
            if (!SystemInfo.ORG_LEVEL_FOUR.equals(headTree.get(i).getOrgLevel())) {
                orgTreelist.add(headTree.get(i));
            }
        }
        return orgTreelist;
    }


    /*构建树*/
    private List<OrgTreeVO> responseOrgTree(List<OrgBeanVO> orgList,boolean result) {
        List<OrgTreeVO> orgTree = new ArrayList<>();
        IntStream.range(0, orgList.size()).forEach(i -> {
            OrgTreeVO orgTreeVO = new OrgTreeVO();
            String orgCode = orgList.get(i).getOrgCode();
            orgTreeVO.setAsyn(0);
            orgTreeVO.setOrgLevel(orgList.get(i).getOrgLevel());
            orgTreeVO.setDisabled("true");
            orgTreeVO.setId(orgCode);
            orgTreeVO.setPid(orgList.get(i).getOrgParentCode() == null ? orgList.get(i).getOrgCode() : orgList.get(i).getOrgParentCode());
            if (!SystemInfo.ORG_LEVEL_FOUR.equals(orgList.get(i).getOrgLevel())&&result) {
                orgTreeVO.setIsParent("true");
            }
            orgTreeVO.setOrderby(ORDERBY);
            //如果是省分本部公司 将其type改为公司3   规则：0  1  2 假节点（河北分公司） 3 公司（总 省 地） 4 部门
            if (SystemInfo.ORG_LEVEL_TWO.equals(orgList.get(i).getOrgLevel()) && orgCode.endsWith("0001")) {
                orgTreeVO.setType(Integer.valueOf("03"));
            } else if (SystemInfo.ORG_LEVEL_ONE.equals(orgList.get(i).getOrgLevel()) && orgCode.endsWith("100001")) { //如果是总部 将其type改为公司
                orgTreeVO.setType(Integer.valueOf("03"));
            } else {
                orgTreeVO.setType(Integer.valueOf(orgList.get(i).getOrgLevel()));
            }
            orgTreeVO.setVal(orgList.get(i).getOrgName());
            orgTree.add(orgTreeVO);
        });
        return orgTree;
    }
    /**
     * 功能描述: 角色判断
     *
     * @param roleCodeList
     * @return java.lang.String
     * @auther: guodong
     * @date: 2019/12/21 15:13
     */
    private String getRoleLevel(List<Object> roleCodeList) {
        String[] selectAllRoleList = {
                "CHNTLEGALMS_36",//法务部门分管领导
                "CHNTLEGALMS_13",//法务初审人（法务专员）
                "CHNTLEGALMS_17",//法务审核人（法务人员）
                "CHNTLEGALMS_33",//法务部门负责人
                "CHNTLEGALMS_23",//董事长
                "CHNTLEGALMS_39",//公司总经理
                "CHNTLEGALMS_0",//超级系统管理员
                "CHNTLEGALMS_7"//系统管理员
        };
        List<String> list = Arrays.asList(selectAllRoleList);
        String role;
        /*判断所有*/
        for (int i = 0; i < roleCodeList.size(); i++) {
            role = (String) roleCodeList.get(i);
            if (list.contains(role)) {
                return "all";
            }
        }
        if (roleCodeList.contains("CHNTLEGALMS_43")) {// 部门人员
            return "bmry";
        }
        return "mr";
    }


}
