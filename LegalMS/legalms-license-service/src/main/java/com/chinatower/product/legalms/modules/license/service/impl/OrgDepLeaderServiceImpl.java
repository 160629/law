package com.chinatower.product.legalms.modules.license.service.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.license.entity.AccountLogicVO;
import com.chinatower.product.legalms.modules.license.entity.OrgBeanVO;
import com.chinatower.product.legalms.modules.license.entity.OrgTreeVO;
import com.chinatower.product.legalms.modules.license.entity.depleader.OrgDepLeader;
import com.chinatower.product.legalms.modules.license.entity.depleader.OrgDepTreeVO;
import com.chinatower.product.legalms.modules.license.mapper.AccountLogicVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.OrgBeanVOMappper;
import com.chinatower.product.legalms.modules.license.mapper.OrgDepLeaderMapper;
import com.chinatower.product.legalms.modules.license.service.AccountLogicService;
import com.chinatower.product.legalms.modules.license.service.CommonOrgTreeService;
import com.chinatower.product.legalms.modules.license.service.OrgDepLeaderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2019/11/18 14:18
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
@Transactional(rollbackFor = Exception.class)
public class OrgDepLeaderServiceImpl implements OrgDepLeaderService {

    @Autowired
    private OrgDepLeaderMapper orgDepLeaderMapper;

    @Autowired
    private OrgBeanVOMappper orgBeanVOMappper;

    @Autowired
    private AccountLogicVOMapper accountLogicVOMapper;

    @Autowired
    private AccountLogicService accountLogicService;


    @Autowired
    private CommonOrgTreeService commonOrgTreeService;


    public static final String ORDERBY = "999999";

    public static final String DEPTNAME = "公司领导";


    @Override
    public ProcessResult selectOrgDepLeader(OrgDepLeader orgDepLeader) {
        UserInfo userInfo = RequestHolder.getUserInfo();
        String orgCode = orgDepLeader.getOrgCode();
        //递归查询该节点下所有orgCode
        if (StringUtils.isNotBlank(orgCode)) {
            List<OrgBeanVO> orgCodeList = orgBeanVOMappper.selectOrgCodeListByOrgCode(orgCode);
            List<OrgBeanVO> result = orgBeanVOMappper.selectOrgInfoList(orgCodeList.stream().map(OrgBeanVO::getOrgCode).collect(Collectors.toList()));
            orgDepLeader.setOrgCodeList(result.stream().map(OrgBeanVO::getOrgCode).collect(Collectors.toList()));
        }
        PageHelper.startPage(orgDepLeader.getPageNum(), orgDepLeader.getPageSize());
        //用于限制单位类型
        orgDepLeader.setCurrOrgCode(userInfo.getDeptId());
        orgDepLeader.setFilterOrgName(DEPTNAME);
        orgDepLeader.setOrgLevel(userInfo.getOrgLevel());
        //判断是否为超级管理员
        List<Object> superAdmin = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_0")).collect(Collectors.toList());
        if (!superAdmin.isEmpty()) {
            String superRole = superAdmin.get(0).toString();//超级管理员角色
            orgDepLeader.setSuperRole(superRole);
        }
        //判断是否为系统管理员
        List<Object> admin = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_7")).collect(Collectors.toList());
        if (admin.isEmpty() && superAdmin.isEmpty()) {
            return new ProcessResult(ProcessResult.ERROR, "无系统管理员权限");
        }
        orgDepLeader.setEnterpType(RequestHolder.getenterpType(userInfo.getUnitCode()));
        List<OrgDepLeader> list = orgDepLeaderMapper.selectLeaderInfo(orgDepLeader);
        PageInfo<OrgDepLeader> pageInfo = new PageInfo<>(list);
        return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
    }

    @Override
    public ProcessResult updateLeaderInfo(OrgDepLeader orgDepLeader) {
        if (orgDepLeader.getEmpNameStr() == null) {
            //删除指定分管部门领导信息
            deleteOrgDepLeader(orgDepLeader);
        } else {
            //增加分管领导信息
            if (StringUtils.isBlank(orgDepLeader.getOrgCode())) {
                return new ProcessResult(ProcessResult.WARN, "orgCode为空");
            }
            //删除指定分管领导信息
            orgDepLeaderMapper.deleteLeaderByOrgCode(orgDepLeader.getOrgCode());
            List<String> empNameList = Arrays.asList(orgDepLeader.getEmpNameStr().split(","));
            List<String> userCodeList = Arrays.asList(orgDepLeader.getUserCodeStr().split(","));
            for (int i = 0; i < empNameList.size(); i++) {
                orgDepLeader.setAccountId(userCodeList.get(i));
                orgDepLeader.setEmpName(empNameList.get(i));
                orgDepLeaderMapper.addLeaderInfo(orgDepLeader);
            }
        }
        return new ProcessResult(ProcessResult.SUCCESS, "更新成功");
    }

    @Override
    public ProcessResult deleteOrgDepLeader(OrgDepLeader orgDepLeader) {
        //删除指定分管领导信息
        if (StringUtils.isBlank(orgDepLeader.getOrgCode())) {
            return new ProcessResult(ProcessResult.WARN, "orgCode为空");
        }
        int a = orgDepLeaderMapper.deleteLeaderByOrgCode(orgDepLeader.getOrgCode());
        //增加已删除部门信息
        orgDepLeaderMapper.addOrgDepInfo(orgDepLeader);
        return new ProcessResult(ProcessResult.SUCCESS, "删除成功", a);
    }


    @Override
    public ProcessResult selectOrgTree() {
        UserInfo userInfo = RequestHolder.getUserInfo();
        if (StringUtils.isBlank(userInfo.getDeptId())) {
            return new ProcessResult(ProcessResult.SUCCESS, "CurrCode不能为空!");
        }
        List<Object> collect = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_0")).collect(Collectors.toList());
        String superRole = null;
        if (!collect.isEmpty()) {
            superRole = collect.get(0).toString();//超级管理员角色
        }
        List<OrgDepTreeVO> list = orgDepLeaderMapper.selectOrgTree(userInfo.getDeptId(), userInfo.getOrgLevel(), superRole, RequestHolder.getenterpType(userInfo.getUnitCode()));
        List<OrgTreeVO> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            OrgTreeVO orgTreeVO = new OrgTreeVO();
            orgTreeVO.setAsyn(0);
            orgTreeVO.setPid(list.get(i).getOrgParentCode());
            orgTreeVO.setId(list.get(i).getOrgCode());
            orgTreeVO.setVal(list.get(i).getOrgName());
            orgTreeVO.setDisabled("true");
            //orgTreeVO.setIsParent("true");
            orgTreeVO.setOrderby(ORDERBY);
            orgTreeVO.setType(1);
            result.add(orgTreeVO);
        }
        return new ProcessResult(ProcessResult.SUCCESS, "", result);
    }

    @Override
    public ProcessResult selectLeaderTree(OrgDepLeader orgDepLeader) {
        List<OrgTreeVO> orgTree = buildLeaderTree(orgDepLeader);
        return new ProcessResult(ProcessResult.SUCCESS, "", orgTree);
    }
    @Override
    public List<OrgDepLeader> selectDeptCodeByAccountId(String loginAcct) {
        return orgDepLeaderMapper.selectDeptCodeByAccountId(loginAcct);
    }

    @Override
    public ProcessResult initLeaderData() {
        List<OrgDepLeader> list = orgDepLeaderMapper.selectAll();
        for (int i = 0; i < list.size(); i++) {
            OrgDepLeader result = list.get(i);
            orgDepLeaderMapper.initLeaderData(result.getLeadeOrgCode(), result.getAccountId(), result.getOrgCode());
        }
        return new ProcessResult(ProcessResult.SUCCESS, "初始化成功");
    }

    /*构建领导组织树*/
    private List<OrgTreeVO> buildLeaderTree(OrgDepLeader orgDepLeader) {
        List<OrgDepTreeVO> list = orgDepLeaderMapper.selectLeaderTree(orgDepLeader);
        List<OrgDepTreeVO> leaderOrgTree = new ArrayList<>();
        List<OrgDepTreeVO> leaderTree = new ArrayList<>();
        List<OrgTreeVO> orgTree = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            OrgDepTreeVO vo = list.get(i);
            /*判断当前orgPath里包含当前公司companyCode*/
            if (Arrays.asList(vo.getOrgPath().split(",")).contains(vo.getCompanyCode())) {
                /*查询当前部门及父节点(*省分公司/*市分公司+部门)*/
                if (vo.getOrgLevel().equals("01") || vo.getOrgLevel().equals("02") || vo.getOrgLevel().equals("03") || DEPTNAME.equals(vo.getOrgName())) {
                    getOrgInfo(leaderOrgTree, vo);
                }
                //查询当前部门领导
                if (DEPTNAME.equals(vo.getOrgName())) {
                    List<AccountLogicVO> logicVOList = accountLogicVOMapper.selectBySingleCode(Arrays.asList(vo.getOrgCode()));
                    for (int j = 0; j < logicVOList.size(); j++) {
                        getUserInfo(leaderTree, logicVOList, j);
                    }
                }
            }
        }
        buildOrgTreeResult(leaderOrgTree, leaderTree, orgTree);
        return orgTree;
    }

    private void buildOrgTreeResult(List<OrgDepTreeVO> leaderOrgTree, List<OrgDepTreeVO> leaderTree, List<OrgTreeVO> orgTree) {
        //构建领导树
        for (int i = 0; i < leaderOrgTree.size(); i++) {
            buildOrgInfo(leaderOrgTree, orgTree, i);
        }
        if (!leaderTree.isEmpty()) {
            for (int i = 0; i < leaderTree.size(); i++) {
                buildLeaderInfo(leaderTree, orgTree, i);
            }
        }
    }

    private void getUserInfo(List<OrgDepTreeVO> leaderTree, List<AccountLogicVO> logicVOList, int j) {
        OrgDepTreeVO orgDepTreeVO = new OrgDepTreeVO();
        if (StringUtils.isBlank(logicVOList.get(j).getAccountName())) {
            return;
        } else {
            orgDepTreeVO.setUserName(logicVOList.get(j).getAccountName());
        }
        orgDepTreeVO.setUserCode(logicVOList.get(j).getAccountId()); //代表领导AccountCurUserId
        orgDepTreeVO.setOrgParentCode(logicVOList.get(j).getAccountOrgId());
        leaderTree.add(orgDepTreeVO);
    }

    private void getOrgInfo(List<OrgDepTreeVO> leaderOrgTree, OrgDepTreeVO vo) {
        OrgDepTreeVO orgDepTreeVO = new OrgDepTreeVO();
        orgDepTreeVO.setOrgCode(vo.getOrgCode());
        orgDepTreeVO.setOrgName(vo.getOrgName());
        orgDepTreeVO.setOrgParentCode(vo.getOrgParentCode());
        leaderOrgTree.add(orgDepTreeVO);
    }

    private void buildLeaderInfo(List<OrgDepTreeVO> leaderTree, List<OrgTreeVO> orgTree, int i) {
        OrgTreeVO orgTreeVO = new OrgTreeVO();
        orgTreeVO.setAsyn(0);
        orgTreeVO.setDisabled("false");
        //orgTreeVO.setIsParent("false");
        orgTreeVO.setId(leaderTree.get(i).getUserCode());
        orgTreeVO.setPid(leaderTree.get(i).getOrgParentCode());
        orgTreeVO.setOrderby(ORDERBY);
        orgTreeVO.setType(1);
        orgTreeVO.setVal(leaderTree.get(i).getUserName());
        orgTree.add(orgTreeVO);
    }

    private void buildOrgInfo(List<OrgDepTreeVO> leaderOrgTree, List<OrgTreeVO> orgTree, int i) {
        OrgTreeVO orgTreeVO = new OrgTreeVO();
        orgTreeVO.setAsyn(0);
        orgTreeVO.setDisabled("true");
        orgTreeVO.setId(leaderOrgTree.get(i).getOrgCode());
        orgTreeVO.setPid(leaderOrgTree.get(i).getOrgParentCode());
        //orgTreeVO.setIsParent("true");
        orgTreeVO.setOrderby(ORDERBY);
        orgTreeVO.setType(2);
        orgTreeVO.setVal(leaderOrgTree.get(i).getOrgName());
        orgTree.add(orgTreeVO);
    }


}
