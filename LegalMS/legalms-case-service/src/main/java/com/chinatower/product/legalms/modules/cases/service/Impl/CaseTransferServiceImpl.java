package com.chinatower.product.legalms.modules.cases.service.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.modules.cases.entity.*;
import com.chinatower.product.legalms.modules.cases.mapper.CaseTransferVOMapper;
import com.chinatower.product.legalms.modules.cases.service.CaseMainService;
import com.chinatower.product.legalms.modules.cases.service.CaseTransferService;
import com.chinatower.provider.call.flowpath.TFlowServiceClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Date: 2019/12/11 11:52
 * @Description:
 */
@SuppressWarnings("ALL")
@Service
@Transactional(rollbackFor = Exception.class)
public class CaseTransferServiceImpl implements CaseTransferService {

    @Autowired
    private CaseTransferVOMapper caseTransferVOMapper;

    @Autowired
    private CaseMainService caseMainService;

    @Autowired
    private TFlowServiceClient tFlowServiceClient;

    @Override
    public ProcessResult selectCaseTransferInfo(CaseTransfer caseTransfer) {
        com.chinatower.product.legalms.common.UserInfo userInfo = RequestHolder.getUserInfo();
        //判断是否为超级管理员
        List<Object> superAdmin = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_0")).collect(Collectors.toList());
        String superRole = null;
        if(!superAdmin.isEmpty()){
            superRole = superAdmin.get(0).toString();
            caseTransfer.setSuperRole(superRole);
        }
        //判断是否为系统管理员
        List<Object> admin  = userInfo.getRoleCodeList().stream().filter(x -> String.valueOf(x).equals("CHNTLEGALMS_7")).collect(Collectors.toList());
        if(admin.isEmpty()&&superAdmin.isEmpty()){
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.NO_ADMIN_POWER);
        }
//        List<OrgBeanVO> orgBeanVOS = caseTransferVOMapper.selectOrgTreeByWhere(RequestHolder.getUserInfo().getOrgLevel(), RequestHolder.getCurrCode(),superRole);
//        if(orgBeanVOS.isEmpty()){
//            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.FAILURE.SELECT_POWER_FAILED);
//        }
//        List<String> collect = orgBeanVOS.stream().map(OrgBeanVO::getCompanyCode).collect(Collectors.toList());
//        caseTransfer.setCompanyCodeList(collect);
        caseTransfer.setCompanyCodeList(Arrays.asList(userInfo.getOrgId()));
        List<CaseTransfer> list = caseTransferVOMapper.selectCaseTransferInfo(caseTransfer);
        if (caseTransfer.getPageNum() != null && caseTransfer.getPageSize() != null) {
            PageHelper.startPage(caseTransfer.getPageNum(), caseTransfer.getPageSize());
        }
        PageInfo<CaseTransfer> pageInfo = new PageInfo<>(list);
        return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SELECT_CASE_TRANSFER_SUCCESS, pageInfo);
    }

    @Override
    public ProcessResult addCaseTransferInfo(CaseTransfer caseTransfer) {
        if (StringUtils.isBlank(caseTransfer.getAccountId()) || StringUtils.isBlank(caseTransfer.getDeptId()) ||
                StringUtils.isBlank(caseTransfer.getAccountName())) {
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.NO_USERINFO);
        }
        com.chinatower.product.legalms.common.UserInfo pid = RequestHolder.getUserInfo();
        /*获取承接人相关信息*/
        String accountId = caseTransfer.getAccountId();
        String accountName = caseTransfer.getAccountName();
        String orgCode = caseTransfer.getDeptId();
        /*查询承接人信息*/
        UserInfo userInfo = caseTransferVOMapper.selectUserInfo(accountId);
        if (userInfo == null) {
            return new ProcessResult(ProcessResult.WARN, ConstClass.FAILURE.SELECT_USERINFO_FAILED);
        }
        List<String> caseIdList = caseTransfer.getCaseIdList();
        List<Map> mapList = buildUnTransfer(caseTransfer, userInfo, caseIdList);
        /*判断当前集合如果不为空，则返回移交失败卷宗信息*/
        if(!mapList.isEmpty()){
         return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.TRANSFER_FAILED,mapList);
        }
       /* 获取caseId集合,进行批量处理*/
        for (int i = 0; i < caseIdList.size(); i++) {
            /*根据caseid更新casemain主表相关信息*/
            CaseMainVO caseMainVO = new CaseMainVO();
            caseMainVO.setCaseId(caseIdList.get(i));
            caseMainVO.setCreatorAccountId(accountId);
            caseMainVO.setCreatorAccountName(accountName);
            caseMainVO.setCreatorCell(StringUtils.defaultIfBlank(userInfo.getMobile(), "*"));
            caseMainVO.setCreatorDeptId(orgCode);
            caseMainVO.setCreatorDeptName(StringUtils.defaultIfBlank(userInfo.getOrgName(), "*"));
            caseMainVO.setCreatorUnitId(StringUtils.defaultIfBlank(userInfo.getCompanyCode(), "*"));
            caseMainService.updateCaseMain(caseMainVO);
            caseTransfer.setCaseId(StringUtils.defaultIfBlank(caseIdList.get(i), "*"));
            caseTransfer.setCaseReceptUser(StringUtils.defaultIfBlank(accountName, "*"));
            caseTransfer.setReceptUserId(StringUtils.defaultIfBlank(accountId, "*"));
            caseTransfer.setTransferUserId(StringUtils.defaultIfBlank(pid.getLoginAcct(), "*"));
            caseTransfer.setCaseTransferUser(StringUtils.defaultIfBlank(pid.getLoginName(), "*"));
            caseTransfer.setTansferReason(StringUtils.defaultIfBlank(caseTransfer.getTansferReason(), ""));
            caseTransfer.setReceptorUnitId(StringUtils.defaultIfBlank(userInfo.getCompanyCode(), "*"));
            caseTransfer.setTransferUnitId(StringUtils.defaultIfBlank(pid.getOrgId(), "*"));
            /*判断移交记录是否冲突*/
            int count = caseTransferVOMapper.selectInfoBycaseId(caseIdList.get(i));
            if (count > 0) {
                caseTransferVOMapper.updateInfoBycaseId(caseTransfer);
            } else {
                /*移交卷宗增加承接人和移交人相关信息*/
                caseTransferVOMapper.addCaseTransferInfo(caseTransfer);
            }
        }
       /*承接人生成待阅*/
        ProcessResult result = tFlowServiceClient.autoUnView(RequestHolder.getLoginAcct(), RequestHolder.getLoginName(), accountId, accountName, caseIdList.size(), caseTransfer.getTansferReason());
        return new ProcessResult(ProcessResult.SUCCESS, result.getMess(), result.getData()==null ? ConstClass.FAILURE.FAILURE_MESS : result.getData());
    }

    @Override
    public List<Map<String, Object>> getExcelDate() {
        List<Map<String, String>> list = caseTransferVOMapper.getExcelDate();
        Map<String, String> resultMap = getDictMap("sys_case_status");
        List<Map<String, Object>> excels = new ArrayList<>(list.size());
        for (Map<String, String> map : list) {
            Map<String, Object> map1 = new LinkedHashMap<>();
            map1.put("卷宗标题", map.get("case_title"));
            map1.put("卷宗编号", map.get("case_code"));
            String str1 = map.get("case_status");
            String caseStatus=null==str1?"":resultMap.get(str1);
            map1.put("状态", caseStatus);
            map1.put("移出人", map.get("case_transfer_user"));
            map1.put("承接人", map.get("case_recept_user"));
            map1.put("移出时间", map.get("case_transfer_time"));
            excels.add(map1);
        }
        return excels;
    }

    private Map<String, String> getDictMap(String dictType) {
        List<Map<String, String>> dictMaps = caseTransferVOMapper.dictMaps(dictType);
        Map<String, String> hashMap = new HashMap<>(dictMaps.size());
        for (Map<String, String> map : dictMaps) {
            hashMap.put(map.get("dict_value"), map.get("dict_cabel"));
        }
        return hashMap;
    }

    private List<Map> buildUnTransfer(CaseTransfer caseTransfer, UserInfo userInfo, List<String> caseIdList) {
        List<String> caseCodeList = caseTransfer.getCaseCodeList();
        /*移交失败卷宗集合*/
        List<Map> mapList = new ArrayList<>();
        /*判断是否有不符合移交卷宗caseId*/
        List<String> unCaseCodeList = new ArrayList<>();
        List<String> unCaseIdList = new ArrayList<>();
        /*判断不是同一公司的caseId集合,且为在办状态的卷宗*/
        for (int j = 0; j < caseIdList.size(); j++) {
           if(!caseTransfer.getUnitIdList().get(j).equals(userInfo.getCompanyCode())&&"2".equals(caseTransfer.getCaseStatusList().get(j))){
               /*判断当前卷宗是否为在办状态 案件状态(1未提交2在办3已结案)*/
                   unCaseCodeList.add(caseCodeList.get(j));
                   unCaseIdList.add(caseIdList.get(j));
           }
        }
        /*根据caseid进行判断该卷宗是否有在审批中的纠纷处理单、协办单、转办单状态*/
        if(!unCaseIdList.isEmpty()){
            CaseStatusVO caseStatusVO = new CaseStatusVO(unCaseIdList);
            List<CaseStatusVO> jointlyStatus = caseTransferVOMapper.selectJointlyStatusByCaseId(caseStatusVO);
            List<CaseStatusVO> lawSuitStatus = caseTransferVOMapper.selectLawSuitStatusByCaseId(caseStatusVO);
            List<CaseStatusVO> caseLegislationStatus = caseTransferVOMapper.selectCaseLegislationStatusByCaseId(caseStatusVO);
            /*组装数据*/
            for (int i = 0; i < unCaseIdList.size(); i++) {
                Map map = new HashMap();
                String caseId = unCaseIdList.get(i);
                String caseCode = unCaseCodeList.get(i);
                buildResult(jointlyStatus, lawSuitStatus, caseLegislationStatus, map, caseId, caseCode);
                if(!map.isEmpty()){
                    mapList.add(map);
                }
            }
        }
        return mapList;
    }

    private void buildResult(List<CaseStatusVO> jointlyStatus, List<CaseStatusVO> lawSuitStatus, List<CaseStatusVO> caseLegislationStatus, Map map, String caseId, String caseCode) {
        getJointly(jointlyStatus, map, caseId, caseCode);
        for (int j = 0; j < lawSuitStatus.size(); j++) {
            if(caseId.equals(lawSuitStatus.get(j).getCaseId())){
                if(map.containsKey(caseCode)){
                    map.put(caseCode,map.get(caseCode)+"/纠纷处理申报单");
                }else {
                    map.put(caseCode,"存在审批中的纠纷处理申报单");
                }
            }
        }
        for (int j = 0; j < caseLegislationStatus.size(); j++) {
            if(caseId.equals(caseLegislationStatus.get(j).getCaseId())){
                if(map.containsKey(caseCode)){
                    map.put(caseCode,map.get(caseCode)+"/法律文书办理单");
                }else {
                    map.put(caseCode,"存在审批中的法律文书办理单");
                }
            }
        }
    }

    private void getJointly(List<CaseStatusVO> jointlyStatus, Map map, String caseId, String caseCode) {
        for (int j = 0; j < jointlyStatus.size(); j++) {
             if(caseId.equals(jointlyStatus.get(j).getCaseId())){
                 map.put(caseCode,"存在审批中的案件协办单");
             }
        }
    }
}
