package com.chinatower.product.legalms.modules.license.service.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.license.entity.AccountLogicVO;
import com.chinatower.product.legalms.modules.license.entity.OrgBeanVO;
import com.chinatower.product.legalms.modules.license.entity.OrgTreeVO;
import com.chinatower.product.legalms.modules.license.entity.floworgtree.CommenPermission;
import com.chinatower.product.legalms.modules.license.mapper.AccountLogicVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.OrgBeanVOMappper;
import com.chinatower.product.legalms.modules.license.service.OrgTreePersonService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2020/8/4 14:36
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
@Transactional(rollbackFor = Exception.class)
public class OrgTreePersonServiceImpl implements OrgTreePersonService {

    public static final String INFO = "获取人员组织信息失败";

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);


    @Autowired
    private OrgBeanVOMappper orgBeanVOMappper;

    @Autowired
    private AccountLogicVOMapper accountLogicVOMapper;

    /**
     * 功能描述: 公共查人树
     *
     * @param commenPermission
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2019/12/27 11:33
     */
    @Override
    public ProcessResult selectOrgTreeAndPerson(CommenPermission commenPermission) {
        ProcessResult orgTree = null;
        String flag = commenPermission.getFlag();
        switch (flag) {
            case "1":
                orgTree = selectEntrustedOrgTree();//委托代办查部门人员
                break;
            case "2":
                orgTree = selectCaseTransferOrgTree();//卷宗移交查法务人员
                break;
            case "3":
                orgTree = selectCaseTransferBelongToOrgTree();//卷宗移交查归属人
                break;
            case "4":
                orgTree = selectUserOrgTreeInfo(commenPermission);//组织机构模块查人
                break;
            case "5":
                orgTree = selectCaseReferOrgTree(commenPermission);//卷宗涉案查人
                break;
            default:
                return null;

        }
        return orgTree;
    }

    public ProcessResult selectCaseReferOrgTree(CommenPermission commenPermission) {
        List<OrgBeanVO> list = new ArrayList<>();
        List<AccountLogicVO> accountlist = new ArrayList<>();
        //获取用户信息
        UserInfo userInfo = RequestHolder.getUserInfo();
        if (userInfo == null) {
            return new ProcessResult(ProcessResult.ERROR, SystemInfo.GET_USER_ERROR);
        }
        String orgLevel = userInfo.getOrgLevel();
        String deptId = userInfo.getDeptId();
        String orgId = userInfo.getOrgId();
        String unitCode = userInfo.getUnitCode();
        String enterpType = RequestHolder.getenterpType(unitCode);
        //判断是否为超级管理员
        List<Object> superAdmin = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_0")).collect(Collectors.toList());
        String superRole = null;
        if (!superAdmin.isEmpty()) {
            superRole = superAdmin.get(0).toString();//超级管理员角色
        }
        if (StringUtils.isEmpty(commenPermission.getOrgCode())) {
            //根据用户信息判断当前登录人权限级别
            list = buildUpOrgInfoByCase(list, orgLevel, deptId, orgId, enterpType, superRole);
        } else if (StringUtils.isNotEmpty(commenPermission.getOrgCode()) && StringUtils.isEmpty(superRole)) {
            list = orgBeanVOMappper.selectUnderOrgInfoByCase(commenPermission.getOrgCode(), orgLevel);
        } else if (StringUtils.isNotEmpty(commenPermission.getOrgCode()) && StringUtils.isNotEmpty(superRole)) {
            list = orgBeanVOMappper.selectUnderOrgInfo(commenPermission.getOrgCode());
        }
        List<AccountLogicVO> accountLogicVOList = new ArrayList<>();
        if (StringUtils.isBlank(commenPermission.getParam())) {
            //"0" 查t_pub_org
            List<OrgBeanVO> reslut = orgBeanVOMappper.selectUserInfoByOrgCode(commenPermission.getOrgCode(), "0");
            accountLogicVOList = buildResultListInfo(accountlist, reslut);
        }
        List<OrgTreeVO> orgTreeVOS = buildResposeInfoByCase(list, accountLogicVOList, commenPermission.getOrgCode());
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", orgTreeVOS);
    }

    private List<OrgTreeVO> buildResposeInfoByCase(List<OrgBeanVO> list, List<AccountLogicVO> accountLogicVOList, String orgCode) {
        List<OrgTreeVO> orgTreeList = new ArrayList<>();
        List<OrgBeanVO> orgBeanVOS = new ArrayList<>();
        if (StringUtils.isEmpty(orgCode)) {
            orgBeanVOS = orgBeanVOMappper.selectOrgTreeByCode(Arrays.asList(list.get(0).getOrgParentCode()));
        } else {
            List<String> collect = list.stream().map(OrgBeanVO::getOrgCode).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                orgBeanVOS = orgBeanVOMappper.selectOrgTreeByCode(collect);
            }
        }
        for (int i = 0; i < orgBeanVOS.size(); i++) {
            buildOtherResult(orgTreeList, orgBeanVOS, i);
        }
        for (int j = 0; j < accountLogicVOList.size(); j++) {
            buildUserResultByCase(accountLogicVOList, orgTreeList, j);
        }
        return orgTreeList;
    }

    private void buildUserResultByCase(List<AccountLogicVO> accountLogicVOList, List<OrgTreeVO> orgTreeList, int j) {
        OrgTreeVO orgTreeVO = new OrgTreeVO();
        orgTreeVO.setAsyn(0);
        orgTreeVO.setDisabled(SystemInfo.FALSE_TREE);
        //判断主从账号组织ID
        orgTreeVO.setId(accountLogicVOList.get(j).getAccountId());
        orgTreeVO.setPid(accountLogicVOList.get(j).getAccountOrgId());
        orgTreeVO.setIsParent(SystemInfo.FALSE_TREE);
        orgTreeVO.setOrderby("999999");
        orgTreeVO.setType(5);
        orgTreeVO.setOrgId(accountLogicVOList.get(j).getCompanyCode());//公司id
        orgTreeVO.setOrgName(accountLogicVOList.get(j).getCompanyName());//公司名称
        orgTreeVO.setDeptName(accountLogicVOList.get(j).getOrgName());//部门名称
        orgTreeVO.setVal(accountLogicVOList.get(j).getAccountCurUserName());
        orgTreeList.add(orgTreeVO);
    }

    public ProcessResult selectUserOrgTreeInfo(CommenPermission commenPermission) {

        List<OrgBeanVO> list = new ArrayList<>();
        List<AccountLogicVO> list1 = new ArrayList<>();
        //获取用户信息
        UserInfo userInfo = RequestHolder.getUserInfo();
        if (userInfo == null) {
            return new ProcessResult(ProcessResult.ERROR, SystemInfo.GET_USER_ERROR);
        }
        String orgLevel = userInfo.getOrgLevel();
        String deptId = userInfo.getDeptId();
        String orgId = userInfo.getOrgId();
        String unitCode = userInfo.getUnitCode();
        String enterpType = RequestHolder.getenterpType(unitCode);
        //判断是否为超级管理员
        List<Object> superAdmin = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_0")).collect(Collectors.toList());
        //判断是否为系统管理员
        List<Object> admin = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_7")).collect(Collectors.toList());
        if (admin.isEmpty() && superAdmin.isEmpty()) {
            return new ProcessResult(ProcessResult.ERROR, "无系统管理员权限");
        }
        String superRole = null;
        if (!superAdmin.isEmpty()) {
            superRole = superAdmin.get(0).toString();//超级管理员角色
        }
        if (StringUtils.isEmpty(commenPermission.getOrgCode())) {
            //根据用户信息判断当前登录人权限级别
            list = buildUpOrgInfo(list, orgLevel, deptId, orgId, enterpType, superRole);
        } else if (StringUtils.isNotEmpty(commenPermission.getOrgCode()) && StringUtils.isNotEmpty(superRole)) {
            if (commenPermission.getOrgCode().equals(SystemInfo.UNIT_CODE_MAX)) {
                //查询所有组织code ：000000 的下一级节点（包括服务商组织）
                list = orgBeanVOMappper.selectNextTopOrgInfo(commenPermission.getOrgCode());
            } else {
                list = orgBeanVOMappper.selectNextOrgInfo(commenPermission.getOrgCode(), commenPermission.getParam());
            }
        } else if (StringUtils.isNotEmpty(commenPermission.getOrgCode()) && StringUtils.isEmpty(superRole)) {
            list = orgBeanVOMappper.selectUnderOrgInfo(commenPermission.getOrgCode());
        }
        List<AccountLogicVO> accountLogicVOList = new ArrayList<>();
        if (StringUtils.isNotEmpty(commenPermission.getOrgCode()) && StringUtils.isNotEmpty(commenPermission.getParam())) {
            List<OrgBeanVO> reslut = orgBeanVOMappper.selectUserInfoByOrgCode(commenPermission.getOrgCode(), commenPermission.getParam());
            accountLogicVOList = buildResultListInfo(list1, reslut);
        }
        List<OrgTreeVO> orgTreeVOS = buildTreeInfo(list, accountLogicVOList, commenPermission.getOrgCode());
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", orgTreeVOS);
    }

    private List<OrgBeanVO> buildUpOrgInfoByCase(List<OrgBeanVO> list, String orgLevel, String deptId, String orgId, String enterpType, String superRole) {
        if (StringUtils.isNotEmpty(superRole)) {
            OrgBeanVO orgBeanVO = new OrgBeanVO();
            orgBeanVO.setOrgParentCode(SystemInfo.UNIT_CODE_MAX);
            list.add(orgBeanVO);
        } else {
            list = buildAreaInfoByCase(list, orgLevel, deptId, orgId, enterpType, superRole);
        }
        return list;
    }

    private List<OrgBeanVO> buildAreaInfoByCase(List<OrgBeanVO> list, String orgLevel, String deptId, String orgId, String enterpType, String superRole) {
        switch (orgLevel) {
            case SystemInfo.ORG_LEVEL_ONE:// 总部
                OrgBeanVO orgBeanVO = new OrgBeanVO();
                orgBeanVO.setOrgParentCode(orgId);
                list.add(orgBeanVO);
                break;
            case SystemInfo.ORG_LEVEL_TWO:// 省份
                list = orgBeanVOMappper.selectAllOrgTree(enterpType, orgLevel, superRole, orgId);
                break;
            case SystemInfo.ORG_LEVEL_THREE:// 地市
                list = orgBeanVOMappper.selectAllOrgTree(null, orgLevel, superRole, deptId);
                break;
            default:
                logger.error(INFO);
        }
        return list;
    }

    private List<OrgTreeVO> buildTreeInfo(List<OrgBeanVO> list, List<AccountLogicVO> accountLogicVOList, String orgCode) {
        List<OrgTreeVO> orgTreeList = new ArrayList<>();
        List<OrgBeanVO> orgBeanVOS = new ArrayList<>();
        if (StringUtils.isEmpty(orgCode)) {
            orgBeanVOS = orgBeanVOMappper.selectOrgTreeByCode(Arrays.asList(list.get(0).getOrgParentCode()));
        } else {
            orgBeanVOS.addAll(list);
        }
        for (int i = 0; i < orgBeanVOS.size(); i++) {
            buildOtherResult(orgTreeList, orgBeanVOS, i);
        }
        for (int j = 0; j < accountLogicVOList.size(); j++) {
            buildUserResult(accountLogicVOList, orgTreeList, j);
        }
        return orgTreeList;
    }


    private void buildOtherResult(List<OrgTreeVO> orgTreeList, List<OrgBeanVO> orgBeanVOS, int i) {
        OrgTreeVO orgTreeVO = new OrgTreeVO();
        orgTreeVO.setAsyn(0);
        orgTreeVO.setDisabled(SystemInfo.TRUE_TREE);
        orgTreeVO.setId(orgBeanVOS.get(i).getOrgCode());
        orgTreeVO.setPid(orgBeanVOS.get(i).getOrgParentCode());
        orgTreeVO.setIsParent(SystemInfo.TRUE_TREE);
        orgTreeVO.setOrderby(orgBeanVOS.get(i).getOrgLevel());
        //如果是省分本部公司 将其type改为公司3   规则：0 中国铁塔集团  1 中国铁塔 2 假节点（河北分公司） 3 公司（总 省 地） 4 部门 5 人
        if (SystemInfo.ORG_LEVEL_TWO.equals(orgBeanVOS.get(i).getOrgLevel()) && orgBeanVOS.get(i).getOrgCode().endsWith("0001")) {
            orgTreeVO.setType(Integer.valueOf("03"));
        } else if (SystemInfo.ORG_LEVEL_ONE.equals(orgBeanVOS.get(i).getOrgLevel()) && orgBeanVOS.get(i).getOrgCode().endsWith("100001")) { //如果是总部 将其type改为公司
            orgTreeVO.setType(Integer.valueOf("03"));
        } else {
            orgTreeVO.setType(Integer.valueOf(orgBeanVOS.get(i).getOrgLevel()));
        }
        orgTreeVO.setVal(orgBeanVOS.get(i).getOrgName());
        orgTreeVO.setTableType(orgBeanVOS.get(i).getOrgTableType());
        orgTreeList.add(orgTreeVO);
    }

    private void buildUserResult(List<AccountLogicVO> accountLogicVOList, List<OrgTreeVO> orgTreeList, int j) {
        OrgTreeVO orgTreeVO = new OrgTreeVO();
        orgTreeVO.setAsyn(0);
        orgTreeVO.setDisabled(SystemInfo.FALSE_TREE);
        //判断主从账号组织ID
        orgTreeVO.setId(accountLogicVOList.get(j).getAccountId());
        orgTreeVO.setPid(accountLogicVOList.get(j).getAccountOrgId());
        orgTreeVO.setIsParent(SystemInfo.FALSE_TREE);
        orgTreeVO.setOrderby("999999");
        orgTreeVO.setType(5);
        orgTreeVO.setVal(accountLogicVOList.get(j).getAccountCurUserName());
        orgTreeList.add(orgTreeVO);
    }


    public ProcessResult selectCaseTransferBelongToOrgTree() {
        List<OrgBeanVO> list = new ArrayList<>();
        List<AccountLogicVO> list1 = new ArrayList<>();
        //获取用户信息
        UserInfo userInfo = RequestHolder.getUserInfo();
        if (userInfo == null) {
            return new ProcessResult(ProcessResult.ERROR, SystemInfo.GET_USER_ERROR);
        }
        String orgLevel = userInfo.getOrgLevel();
        String deptId = userInfo.getDeptId();
        String orgId = userInfo.getOrgId();
        String unitCode = userInfo.getUnitCode();
        String enterpType = RequestHolder.getenterpType(unitCode);
        String superRole = null;/*勿动*/
        //根据用户信息判断当前登录人权限级别
        switch (orgLevel) {
            case SystemInfo.ORG_LEVEL_ONE:// 总部
                list = orgBeanVOMappper.selectBelongUser(enterpType, orgLevel, superRole);
                break;
            case SystemInfo.ORG_LEVEL_TWO:// 省份
                String provinceCode = RequestHolder.getProvinceCode(deptId);
                list = orgBeanVOMappper.selectBelongUser(provinceCode, orgLevel, superRole);
                break;
            case SystemInfo.ORG_LEVEL_THREE:// 地市
                list = orgBeanVOMappper.selectBelongUser(orgId, orgLevel, superRole);
                break;
            default:
                logger.error(INFO);
        }
        List<AccountLogicVO> accountLogicVOList = buildListInfos(list1, list);
        List<OrgTreeVO> orgTreeVOS = buildResposeInfo(accountLogicVOList);
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", orgTreeVOS);
    }

    private List<OrgBeanVO> buildUpOrgInfo(List<OrgBeanVO> list, String orgLevel, String deptId, String orgId, String enterpType, String superRole) {
        if (StringUtils.isNotEmpty(superRole)) {
            OrgBeanVO orgBeanVO = new OrgBeanVO();
            orgBeanVO.setOrgParentCode(SystemInfo.UNIT_CODE_MAX);
            list.add(orgBeanVO);
        } else {
            list = buildAreaInfo(list, orgLevel, deptId, orgId, enterpType, superRole);
        }
        return list;
    }

    private List<OrgBeanVO> buildAreaInfo(List<OrgBeanVO> list, String orgLevel, String deptId, String orgId, String enterpType, String superRole) {
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
                logger.error(INFO);
        }
        return list;
    }


    public ProcessResult selectEntrustedOrgTree() {
        UserInfo userInfo = RequestHolder.getUserInfo();
        if (userInfo == null) {
            return new ProcessResult(ProcessResult.ERROR, SystemInfo.GET_USER_ERROR);
        }
        String deptId = userInfo.getDeptId();
        List<AccountLogicVO> result = accountLogicVOMapper.selectCurrOrgTreeByOrgCode(deptId);
        List<OrgTreeVO> orgTreeVOS = buildResposeInfo(result);
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", orgTreeVOS);
    }


    /**
     * 功能描述: 查询法务角色移交人组织树
     *
     * @param
     * @return java.util.List<com.chinatower.product.legalms.modules.license.entity.OrgTreeVO>
     * @auther: guodong
     * @date: 2019/12/11 19:23
     */
    public ProcessResult selectCaseTransferOrgTree() {
        List<OrgBeanVO> list = new ArrayList<>();
        List<AccountLogicVO> list1 = new ArrayList<>();
        List<String> roleList = Arrays.asList(SystemInfo.FAWU_ROLES.split(","));
        //获取用户信息
        UserInfo userInfo = RequestHolder.getUserInfo();
        if (userInfo == null) {
            return new ProcessResult(ProcessResult.ERROR, SystemInfo.GET_USER_ERROR);
        }
        String orgLevel = userInfo.getOrgLevel();
        String deptId = userInfo.getDeptId();
        String orgId = userInfo.getOrgId();
        String unitCode = userInfo.getUnitCode();
        String enterpType = RequestHolder.getenterpType(unitCode);
        String superRole = null;
        //根据用户信息判断当前登录人权限级别
        switch (orgLevel) {
            case SystemInfo.ORG_LEVEL_ONE:// 总部
                list = orgBeanVOMappper.selectCaseReceptInfoByLevel(roleList, enterpType, orgLevel, superRole);
                break;
            case SystemInfo.ORG_LEVEL_TWO:// 省份
                String provinceCode = RequestHolder.getProvinceCode(deptId);
                list = orgBeanVOMappper.selectCaseReceptInfoByLevel(roleList, provinceCode, orgLevel, superRole);
                break;
            case SystemInfo.ORG_LEVEL_THREE:// 地市
                list = orgBeanVOMappper.selectCaseReceptInfoByLevel(roleList, orgId, orgLevel, superRole);
                break;
            default:
                logger.error(INFO);
        }
        List<AccountLogicVO> accountLogicVOList = buildListInfos(list1, list);
        List<OrgTreeVO> orgTreeVOS = buildResposeInfo(accountLogicVOList);
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", orgTreeVOS);
    }

    /**
     * 功能描述:构建人员信息
     *
     * @param list
     * @param OrgBeanVO
     * @return java.util.List<com.chinatower.product.legalms.modules.license.entity.AccountLogicVO>
     * @auther: G D
     * @date: 2019/10/16 16:49s
     */
    private List<AccountLogicVO> buildListInfos(List<AccountLogicVO> list, List<OrgBeanVO> orgBeanVO) {
        for (int i = 0; i < orgBeanVO.size(); i++) {
            OrgBeanVO result = orgBeanVO.get(i);
            AccountLogicVO accountLogicVO = new AccountLogicVO();
            if (StringUtils.isBlank(result.getUserName())) {
                continue;
            } else {
                accountLogicVO.setAccountCurUserName(result.getUserName());
            }
            accountLogicVO.setAccountId(result.getAccountId());
            accountLogicVO.setAccountOrgId(result.getOrgCode());
            accountLogicVO.setOrgPath(result.getOrgPath());
            list.add(accountLogicVO);
        }
        return list;
    }


    private List<AccountLogicVO> buildResultListInfo(List<AccountLogicVO> list, List<OrgBeanVO> orgBeanVO) {
        for (int i = 0; i < orgBeanVO.size(); i++) {
            OrgBeanVO result = orgBeanVO.get(i);
            AccountLogicVO accountLogicVO = new AccountLogicVO();
            if (StringUtils.isBlank(result.getUserName())) {
                continue;
            } else {
                accountLogicVO.setAccountCurUserName(result.getUserName());
            }
            accountLogicVO.setAccountId(result.getAccountId());
            accountLogicVO.setAccountOrgId(result.getOrgCode());
            accountLogicVO.setCompanyCode(result.getCompanyCode());
            accountLogicVO.setCompanyName(result.getCompanyName());
            accountLogicVO.setOrgName(result.getOrgName());
            list.add(accountLogicVO);
        }
        return list;
    }

    /**
     * 功能描述:构建角色对应的组织集合
     *
     * @param list
     * @return java.util.List<com.chinatower.product.legalms.modules.license.entity.OrgTreeVO>
     * @auther: G D
     * @date: 2019/10/16 16:49
     */
    private List<OrgTreeVO> buildResposeInfo(List<AccountLogicVO> list) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        List<OrgTreeVO> orgTreeList = new ArrayList<>();
        //遍历org_path查询所有组织信息
        StringBuilder allOrgList = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (allOrgList.indexOf(list.get(i).getOrgPath()) < 0) {
                allOrgList.append(list.get(i).getOrgPath());
            }
        }
        //ort_path进行分割查询
        List<OrgBeanVO> orgBeanVOS = orgBeanVOMappper.selectOrgTreeByCode(Arrays.asList(allOrgList.toString().split(",")));
        for (int i = 0; i < orgBeanVOS.size(); i++) {
            OrgTreeVO orgTreeVO = new OrgTreeVO();
            orgTreeVO.setAsyn(0);
            orgTreeVO.setDisabled("true");
            orgTreeVO.setId(orgBeanVOS.get(i).getOrgCode());
            orgTreeVO.setPid(orgBeanVOS.get(i).getOrgParentCode());
            //orgTreeVO.setIsParent("true");
            orgTreeVO.setOrderby("999999");
            orgTreeVO.setNocheck("true");
            //如果是省分本部公司 将其type改为公司3   规则：0 中国铁塔集团  1 中国铁塔 2 假节点（河北分公司） 3 公司（总 省 地） 4 部门 5 人
            if (SystemInfo.ORG_LEVEL_TWO.equals(orgBeanVOS.get(i).getOrgLevel()) && orgBeanVOS.get(i).getOrgCode().endsWith("0001")) {
                orgTreeVO.setType(Integer.valueOf("03"));
            } else if (SystemInfo.ORG_LEVEL_ONE.equals(orgBeanVOS.get(i).getOrgLevel()) && orgBeanVOS.get(i).getOrgCode().endsWith("100001")) { //如果是总部 将其type改为公司
                orgTreeVO.setType(Integer.valueOf("03"));
            } else {
                orgTreeVO.setType(Integer.valueOf(orgBeanVOS.get(i).getOrgLevel()));
            }
            orgTreeVO.setVal(orgBeanVOS.get(i).getOrgName());
            orgTreeList.add(orgTreeVO);
        }
        for (int j = 0; j < list.size(); j++) {
            OrgTreeVO orgTreeVO = new OrgTreeVO();
            orgTreeVO.setAsyn(0);
            orgTreeVO.setDisabled("false");
            //判断主从账号组织ID
            orgTreeVO.setId(list.get(j).getAccountId());
            orgTreeVO.setPid(list.get(j).getAccountOrgId());
            orgTreeVO.setOrgId(list.get(j).getAccountUnitId());
            //orgTreeVO.setIsParent("false");
            orgTreeVO.setOrderby("999999");
            orgTreeVO.setNocheck("false");
            orgTreeVO.setType(5);
            orgTreeVO.setVal(list.get(j).getAccountCurUserName());
            orgTreeVO.setPhone(list.get(j).getAccountPhone());
            orgTreeList.add(orgTreeVO);
        }
        return orgTreeList;
    }
}
