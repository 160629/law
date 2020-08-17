package com.chinatower.product.legalms.modules.license.service.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.license.entity.AccountLogicVO;
import com.chinatower.product.legalms.modules.license.entity.OrgBeanVO;
import com.chinatower.product.legalms.modules.license.entity.OrgTreeVO;
import com.chinatower.product.legalms.modules.license.entity.floworgtree.CommenPermission;
import com.chinatower.product.legalms.modules.license.mapper.OrgBeanVOMappper;
import com.chinatower.product.legalms.modules.license.service.CommonOrgTreeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2020/7/21 15:01
 * @Description:
 */

@SuppressWarnings("ALL")
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CommonOrgTreeServiceImpl implements CommonOrgTreeService {

    public static final String INFO = "获取人员组织信息失败";

    @Autowired
    private OrgBeanVOMappper orgBeanVOMappper;

    @Override
    public ProcessResult selectUserOrgTreeInfoByCase(CommenPermission commenPermission) {
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
        String superRole = null;
        if (!superAdmin.isEmpty()) {
            superRole = superAdmin.get(0).toString();//超级管理员角色
        }
        if (StringUtils.isEmpty(commenPermission.getOrgCode())) {
            //根据用户信息判断当前登录人权限级别
            list = buildUpOrgInfoByCase(list, orgLevel, deptId, orgId, enterpType, superRole);
        } else if(StringUtils.isNotEmpty(commenPermission.getOrgCode())&&StringUtils.isEmpty(superRole)){
            list = orgBeanVOMappper.selectUnderOrgInfoByCase(commenPermission.getOrgCode(), orgLevel);
        } else if(StringUtils.isNotEmpty(commenPermission.getOrgCode())&&StringUtils.isNotEmpty(superRole)){
            list = orgBeanVOMappper.selectUnderOrgInfo(commenPermission.getOrgCode());
        }
        List<AccountLogicVO> accountLogicVOList = new ArrayList<>();
        if (StringUtils.isBlank(commenPermission.getParam())) {
            //"01" 查t_pub_org
            List<OrgBeanVO> reslut = orgBeanVOMappper.selectUserInfoByOrgCode(commenPermission.getOrgCode(),"0");
            accountLogicVOList = buildListInfos(list1, reslut);
        }
        List<OrgTreeVO> orgTreeVOS = buildResposeInfoByCase(list, accountLogicVOList, commenPermission.getOrgCode());
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", orgTreeVOS);
    }

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
            accountLogicVO.setCompanyCode(result.getCompanyCode());
            accountLogicVO.setCompanyName(result.getCompanyName());
            accountLogicVO.setOrgName(result.getOrgName());
            list.add(accountLogicVO);
        }
        return list;
    }

    @Override
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
            list = buildUpOrgInfo( list, orgLevel, deptId, orgId, enterpType, superRole);
        } else {
            list = orgBeanVOMappper.selectUnderOrgInfo(commenPermission.getOrgCode());
        }
        List<AccountLogicVO> accountLogicVOList = new ArrayList<>();
        if (StringUtils.isBlank(commenPermission.getParam())) {
            List<OrgBeanVO> reslut = orgBeanVOMappper.selectUserInfoByOrgCode(commenPermission.getOrgCode(),"0");
            accountLogicVOList = buildListInfos(list1, reslut);
        }
        List<OrgTreeVO> orgTreeVOS = buildResposeInfo(list, accountLogicVOList, commenPermission.getOrgCode(), commenPermission.getParam());
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", orgTreeVOS);
    }

    private List<OrgBeanVO> buildUpOrgInfoByCase( List<OrgBeanVO> list, String orgLevel, String deptId, String orgId, String enterpType, String superRole) {
        if (StringUtils.isNotEmpty(superRole)) {
            OrgBeanVO orgBeanVO = new OrgBeanVO();
            orgBeanVO.setOrgParentCode(SystemInfo.UNIT_CODE_MAX);
            list.add(orgBeanVO);
        } else {
            list = buildAreaInfoByCase(list, orgLevel, deptId, orgId, enterpType, superRole);
        }
        return list;
    }

    private List<OrgBeanVO> buildUpOrgInfo( List<OrgBeanVO> list, String orgLevel, String deptId, String orgId, String enterpType, String superRole) {
        if (StringUtils.isNotEmpty(superRole)) {
            OrgBeanVO orgBeanVO = new OrgBeanVO();
            orgBeanVO.setOrgParentCode(SystemInfo.UNIT_CODE_MAX);
            list.add(orgBeanVO);
        } else {
            list = buildAreaInfo(list, orgLevel, deptId, orgId, enterpType, superRole);
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
                log.error(INFO);
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
                log.error(INFO);
        }
        return list;
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


    private List<OrgTreeVO> buildResposeInfo(List<OrgBeanVO> list, List<AccountLogicVO> accountLogicVOList, String orgCode, String param) {
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
        if (StringUtils.isNotEmpty(param)&&"1".equals(param)) {
            List<OrgBeanVO> collect = orgBeanVOS.stream().filter(x -> !x.getOrgCode().endsWith("0001")).filter(x -> !x.getOrgCode().endsWith("100001")).collect(Collectors.toList());
            for (int i = 0; i < collect.size(); i++) {
                buildResult(orgTreeList, collect, i, param);
            }
        } else {
            for (int i = 0; i < orgBeanVOS.size(); i++) {
                buildOtherResult(orgTreeList, orgBeanVOS, i);
            }
        }
        for (int j = 0; j < accountLogicVOList.size(); j++) {
            buildUserResult(accountLogicVOList, orgTreeList, j);
        }
        return orgTreeList;
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
        orgTreeList.add(orgTreeVO);
    }

    private void buildResult(List<OrgTreeVO> orgTreeList, List<OrgBeanVO> collect, int i, String param) {
        OrgTreeVO orgTreeVO = new OrgTreeVO();
        orgTreeVO.setAsyn(0);
        orgTreeVO.setDisabled(SystemInfo.TRUE_TREE);
        orgTreeVO.setId(collect.get(i).getOrgCode());
        orgTreeVO.setPid(collect.get(i).getOrgParentCode());
        if (!SystemInfo.ORG_LEVEL_THREE.equals(collect.get(i).getOrgLevel()) && StringUtils.isNotEmpty(param)) {
            orgTreeVO.setIsParent(SystemInfo.TRUE_TREE);
        }
        if (StringUtils.isEmpty(param)) {
            orgTreeVO.setIsParent(SystemInfo.TRUE_TREE);
        }
        orgTreeVO.setOrderby(collect.get(i).getOrgLevel());
        //如果是省分本部公司 将其type改为公司3   规则：0  1  2 假节点（河北分公司） 3 公司（总 省 地） 4 部门 5 人
        if (SystemInfo.ORG_LEVEL_TWO.equals(collect.get(i).getOrgLevel()) && collect.get(i).getOrgCode().endsWith("0001")) {
            orgTreeVO.setType(Integer.valueOf("03"));
        } else if (SystemInfo.ORG_LEVEL_ONE.equals(collect.get(i).getOrgLevel()) && collect.get(i).getOrgCode().endsWith("100001")) { //如果是总部 将其type改为公司
            orgTreeVO.setType(Integer.valueOf("03"));
        } else {
            orgTreeVO.setType(Integer.valueOf(collect.get(i).getOrgLevel()));
        }
        orgTreeVO.setVal(collect.get(i).getOrgName());
        orgTreeList.add(orgTreeVO);
    }
}
