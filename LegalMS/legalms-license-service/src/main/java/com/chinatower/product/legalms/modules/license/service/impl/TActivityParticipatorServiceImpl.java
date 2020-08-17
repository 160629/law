package com.chinatower.product.legalms.modules.license.service.impl;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.entity.*;
import com.chinatower.product.legalms.modules.license.mapper.*;
import com.chinatower.product.legalms.modules.license.service.TActivityParticipatorService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Date: 2020/8/4 15:52
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
@Transactional(rollbackFor = Exception.class)
public class TActivityParticipatorServiceImpl implements TActivityParticipatorService {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);


    @Autowired
    private AccountLogicVOMapper accountLogicVOMapper;

    @Autowired
    private AccRoleImpowerVOMapper accRoleImpowerVOMapper;

    @Autowired
    private OrgBeanVOMappper orgBeanVOMappper;

    @Autowired
    private AccountVOMapper accountVOMapper;

    @Autowired
    private RoleVoMapper roleVoMapper;

    @Override
    public ProcessResult selectOrgTree(Map map) {
        logger.info("参与者请求入参={}", map);
        List<AccountLogicVO> list = new ArrayList<>();
        List<OrgTreeVO> orgTree = new ArrayList<>();
        //分部门领导
        if (map.get(SystemInfo.GROUPDEPT) != null) {
            List<AccountLogicVO> accountLogicVOList = JSON.parseArray(map.get(SystemInfo.GROUPDEPT).toString(), AccountLogicVO.class);
            orgTree = buildResposeInfo(accountLogicVOList);
        }
        //各流程涉及回退节点人员查询
        if (SystemInfo.ACTIVITY_FLOWBACK_USERCODE.equals(String.valueOf(map.get(SystemInfo.TYPE_CODE)))) {
            String userCode = map.get(SystemInfo.ACTIVITY_FLOWBACK_USERCODE).toString();
            AccountLogicVO logicVO = accountLogicVOMapper.selectUserInfByAccountId(userCode);
            logger.info("流程涉及回退节点人员={}", logicVO.getAccountId());
            list.add(logicVO);
            orgTree = buildResposeInfo(list);
        }
        //org
        if (SystemInfo.TYPE_CODE_ORG.equals(String.valueOf(map.get(SystemInfo.TYPE_CODE)))) {
            String orgList = map.get(SystemInfo.TYPE_CODE_ORG).toString();
            List<String> org = Arrays.asList(orgList.split(","));
            logger.info("组织org={}", org);
            list = accountLogicVOMapper.selectBySingleCode(org);
            orgTree = buildResposeInfo(list);
        }
        //role
        if (SystemInfo.TYPE_CODE_ROLE.equals(String.valueOf(map.get(SystemInfo.TYPE_CODE)))) {
            String role = map.get(SystemInfo.TYPE_CODE_ROLE).toString();
            List<String> roleList = Arrays.asList(role.split(","));
            logger.info("角色roleList={}", roleList);
            List<AccRoleImpowerVO> accRoleImpowerVO = accRoleImpowerVOMapper.selectBySingleCode(roleList, null, null);
            orgTree = buildResposeInfo(buildListInfo(list, accRoleImpowerVO));
        }
        //unit
        if (SystemInfo.TYPE_CODE_UNIT.equals(String.valueOf(map.get(SystemInfo.TYPE_CODE)))) {
            String unit = map.get(SystemInfo.TYPE_CODE_UNIT).toString();
            List<String> unitList = Arrays.asList(unit.split(","));
            logger.info("公司unitList={}", unitList);
            List<AccRoleImpowerVO> accRoleImpowerVO = accRoleImpowerVOMapper.selectBySingleCode(null, null, unitList);
            orgTree = buildResposeInfo(buildListInfo(list, accRoleImpowerVO));
        }
        //unitrole
        if (SystemInfo.TYPE_CODE_UNITROLE.equals(String.valueOf(map.get(SystemInfo.TYPE_CODE)))) {
            String orgLevel = RequestHolder.getUserInfo().getOrgLevel();
            List<String> unitRoleList = (List<String>) map.get(SystemInfo.TYPE_CODE_UNITROLE);
            String roleCode = unitRoleList.get(0);
            String unitCode = unitRoleList.get(1);
            List<String> roleList = Arrays.asList(roleCode.split(","));
            List<String> unitList = Arrays.asList(unitCode.split(","));
            logger.info("公司unitList={}，角色roleList={}", unitList, roleList);
            List<AccRoleImpowerVO> accRoleImpowerVO = accRoleImpowerVOMapper.selectBySingleCode(roleList, null, unitList);
            //特殊情况，查地市法律角色，如果查不到就查该省和其他地市的律师角色
            if (accRoleImpowerVO.isEmpty() && "CHNTLEGALMS_42".equals(roleCode) && "03".equals(orgLevel)) {
                String deptId = RequestHolder.getUserInfo().getDeptId();
                String provinceCode = RequestHolder.getProvinceCode(deptId);
                List<AccRoleImpowerVO> resultList = accRoleImpowerVOMapper.selectSepLawInfo(roleList, provinceCode);
                List<AccRoleImpowerVO> result = map.get(SystemInfo.TYPE_IS_COUNTERSIGN).equals("1") ? resultList.stream().filter(x -> !x.getOrgName().contains(SystemInfo.DEPT_NAME_LEADER)).collect(Collectors.toList()) : resultList;
                orgTree = buildResposeInfo(buildListInfo(list, result));
            } else if (accRoleImpowerVO.isEmpty()) {
                return buildErrorInfo(roleList);
            } else {
                List<AccRoleImpowerVO> accRoleImpower = map.get(SystemInfo.TYPE_IS_COUNTERSIGN).equals("1") ? accRoleImpowerVO.stream().filter(x -> !x.getOrgName().contains(SystemInfo.DEPT_NAME_LEADER)).collect(Collectors.toList()) : accRoleImpowerVO;
                orgTree = buildResposeInfo(buildListInfo(list, accRoleImpower));
            }
        }
        //orgrole
        if (SystemInfo.TYPE_CODE_ORGROLE.equals(String.valueOf(map.get(SystemInfo.TYPE_CODE)))) {
            List<String> orgrole = (List<String>) map.get(SystemInfo.TYPE_CODE_ORGROLE);
            String roleCode = orgrole.get(0);
            String orgCode = orgrole.get(1);
            List<String> roleList = Arrays.asList(roleCode.split(","));
            List<String> orgList = Arrays.asList(orgCode.split(","));
            logger.info("组织orgList={}，角色roleList={}", orgList, roleList);
            List<AccRoleImpowerVO> accRoleImpowerVO = accRoleImpowerVOMapper.selectBySingleCode(roleList, orgList, null);
            if (accRoleImpowerVO.isEmpty()) {
                return buildErrorInfo(roleList);
            }
            List<AccRoleImpowerVO> accRoleImpower = map.get(SystemInfo.TYPE_IS_COUNTERSIGN).equals("1") ? accRoleImpowerVO.stream().filter(x -> !x.getOrgName().contains(SystemInfo.DEPT_NAME_LEADER)).collect(Collectors.toList()) : accRoleImpowerVO;
            orgTree = buildResposeInfo(buildListInfo(list, accRoleImpower));
        }
        //流程省分查总部人员
        if (SystemInfo.TYPE_CODE_PROVINCETOHEAD.equals(String.valueOf(map.get(SystemInfo.TYPE_CODE)))) {
            List<String> deptCodeAndRoleCode = (List<String>) map.get(SystemInfo.TYPE_CODE_PROVINCETOHEAD);
            List<String> roleList = Arrays.asList(deptCodeAndRoleCode.get(0).split(","));
            String currCode = deptCodeAndRoleCode.get(1);
            List<String> companyCode = Arrays.asList(currCode.split(","));
            logger.info("公司unitList={}，角色roleList={}", companyCode, roleList);
            List<AccRoleImpowerVO> accRoleImpowerVO = accRoleImpowerVOMapper.selectBySingleCode(roleList, null, companyCode);
            if (accRoleImpowerVO.isEmpty()) {
                return buildErrorInfo(roleList);
            }
            orgTree = buildResposeInfo(buildListInfo(list, accRoleImpowerVO));
        }
        //流程地市查省分人员
        if (SystemInfo.TYPE_CODE_CITYTOPROVINCE.equals(String.valueOf(map.get(SystemInfo.TYPE_CODE)))) {
            List<String> deptCodeAndRoleCode = (List<String>) map.get(SystemInfo.TYPE_CODE_CITYTOPROVINCE);
            List<String> roleList = Arrays.asList(deptCodeAndRoleCode.get(0).split(","));
            String currCode = deptCodeAndRoleCode.get(1);
            //OrgBeanVO result = orgBeanVOMappper.selectCityToProvince(currCode).get(1);
            List<String> companyCode = Arrays.asList(currCode.split(","));
            logger.info("公司unitList={}，角色roleList={}", companyCode, roleList);
            List<AccRoleImpowerVO> accRoleImpowerVO = accRoleImpowerVOMapper.selectBySingleCode(roleList, null, companyCode);
            if (accRoleImpowerVO.isEmpty()) {
                return buildErrorInfo(roleList);
            }
            orgTree = buildResposeInfo(buildListInfo(list, accRoleImpowerVO));
        }
        //流程总部查省分人员
        if (SystemInfo.TYPE_CODE_HEADTORPOVINCE.equals(String.valueOf(map.get(SystemInfo.TYPE_CODE)))) {
            List<String> deptCodeAndRoleCode = (List<String>) map.get(SystemInfo.TYPE_CODE_HEADTORPOVINCE);
            List<String> roleList = Arrays.asList(deptCodeAndRoleCode.get(0).split(","));
            String currCode = deptCodeAndRoleCode.get(1);
            List<String> companyCode = Arrays.asList(currCode.split(","));
            logger.info("公司unitList={}，角色roleList={}", companyCode, roleList);
            List<AccRoleImpowerVO> accRoleImpowerVO = accRoleImpowerVOMapper.selectBySingleCode(roleList, null, companyCode);
            if (accRoleImpowerVO.isEmpty()) {
                return buildErrorInfo(roleList);
            }
            orgTree = buildResposeInfo(buildListInfo(list, accRoleImpowerVO));
        }
        //流程省分查地市人员
        if (SystemInfo.TYPE_CODE_PROVINCETOCITY.equals(String.valueOf(map.get(SystemInfo.TYPE_CODE)))) {
            List<String> deptCodeAndRoleCode = (List<String>) map.get(SystemInfo.TYPE_CODE_PROVINCETOCITY);
            List<String> roleList = Arrays.asList(deptCodeAndRoleCode.get(0).split(","));
            String currCode = deptCodeAndRoleCode.get(1);
            List<String> companyCode = Arrays.asList(currCode.split(","));
            logger.info("公司unitList={}，角色roleList={}", companyCode, roleList);
            List<AccRoleImpowerVO> accRoleImpowerVO = accRoleImpowerVOMapper.selectBySingleCode(roleList, null, companyCode);
            if (accRoleImpowerVO.isEmpty()) {
                return buildErrorInfo(roleList);
            }
            orgTree = buildResposeInfo(buildListInfo(list, accRoleImpowerVO));
        }
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", orgTree);
    }

    private ProcessResult buildErrorInfo(List<String> roleList) {
        RoleVo roleVo = roleVoMapper.selectRoleByRoleCode(roleList.get(0));
        return new ProcessResult(ProcessResult.WARN, "环节未配置人员，请联系4A管理员配置法务系统的" + roleVo.getRoleName() + "角色");
    }


    private List<AccountLogicVO> buildListInfo(List<AccountLogicVO> list, List<AccRoleImpowerVO> accRoleImpowerVO) {
        for (int i = 0; i < accRoleImpowerVO.size(); i++) {
            AccRoleImpowerVO result = accRoleImpowerVO.get(i);
            AccountLogicVO accountLogicVO = new AccountLogicVO();
            if (StringUtils.isBlank(result.getUserName())) {
                continue;
            } else {
                accountLogicVO.setAccountCurUserName(result.getUserName());
            }
            accountLogicVO.setAccountUnitId(result.getUnitId());
            accountLogicVO.setAccountPhone(result.getMobile());
            accountLogicVO.setAccountId(result.getAccountId());
            accountLogicVO.setAccountOrgId(result.getOrgCode());
            accountLogicVO.setOrgPath(result.getOrgPath());
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

//        //遍历获取到的对应角色的组织code，进行组装
//        List<String> collect = list.stream().map(AccountLogicVO::getAccountOrgId).collect(Collectors.toList());
//        //获取对应角色的org_path集合
//        List<OrgBeanVO> orgPathList = orgBeanVOMappper.selectOrgPathByCode(collect);

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
            if ("02".equals(orgBeanVOS.get(i).getOrgLevel()) && orgBeanVOS.get(i).getOrgCode().endsWith("0001")) {
                orgTreeVO.setType(Integer.valueOf("03"));
            } else if ("01".equals(orgBeanVOS.get(i).getOrgLevel()) && orgBeanVOS.get(i).getOrgCode().endsWith("100001")) { //如果是总部 将其type改为公司
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
