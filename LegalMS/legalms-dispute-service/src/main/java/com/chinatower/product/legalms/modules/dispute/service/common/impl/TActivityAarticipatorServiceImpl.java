package com.chinatower.product.legalms.modules.dispute.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.common.FlowUtil;
import com.chinatower.product.legalms.common.StringUtil;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.CommonParam;
import com.chinatower.product.legalms.modules.dispute.service.common.TActivityAarticipatorService;
import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityConfigVO;
import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityPowerVO;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;
import com.chinatower.product.legalms.modules.flow.mapper.common.FlowActivityConfigMapper;
import com.chinatower.product.legalms.modules.flow.mapper.common.OrgBeanMapper;
import com.chinatower.product.legalms.modules.flow.mapper.common.OrgDepLeaderVOMapper;
import com.chinatower.product.legalms.modules.flow.service.common.FlowActivityPowerService;
import com.chinatower.product.legalms.modules.flow.vo.common.MyOwnRuntimeException;
import com.chinatower.product.legalms.modules.flow.vo.common.PageList;
import com.chinatower.provider.call.license.LicenseServiceClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Service
@Transactional(rollbackFor = Exception.class)
public class TActivityAarticipatorServiceImpl implements TActivityAarticipatorService {

    private static final Logger logger = LoggerFactory.getLogger(TActivityAarticipatorServiceImpl.class);

    @Autowired
    private LicenseServiceClient licenseServiceClient;

    @Autowired
    private FlowActivityConfigMapper flowActivityConfigMapper;

    @Autowired
    private FlowActivityPowerService flowActivityPowerService;

    @Autowired
    private OrgDepLeaderVOMapper orgDepLeaderVOMapper;

    @Autowired
    private OrgBeanMapper orgBeanMapper;
    @Autowired
    FlowUtil flowUtil;


    /**
     * 功能描述:根据活动ID查询当前参与者信息
     *
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: G D
     * @date: 2019/10/11 17:04
     */
    @Override
    public ProcessResult selectParticipatorByCode(CommonParam commonParam) {
        logger.info("审批参与者请求的参数=>{}", JSON.toJSONString(commonParam));
        ProcessResult list = null;
        List<FlowActivityConfigVO> result = flowActivityConfigMapper.selParamActivityConfig(new PageList(commonParam.getFlowId(), commonParam.getBeginId(), commonParam.getEndId(), commonParam.getVersionId()));
        if (result.isEmpty()) {
            logger.info("请完善该流程活动环节节点数据,返回为空(t_flow_activity_config)");
            return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
        }
        List<FlowActivityPowerVO> flowActivityPowerVOs = flowActivityPowerService.selActivityPower(new FlowActivityPowerVO(commonParam.getFlowId(), commonParam.getEndId(), commonParam.getVersionId()));
        if (flowActivityPowerVOs.isEmpty() || flowActivityPowerVOs.get(0) == null) {
            logger.info("请完善该流程活动环节节点数据,返回为空(t_flow_activity_power)");
            return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
        }
        String flag = result.get(0).getCondition();
        String isCountersign = result.get(0).getIsCountersign();//是否送会签 1 送 0 不送
        String roleCode = flowActivityPowerVOs.get(0).getRoleIds();
        //根据condition判断角色和组织各种情况
        list = buildResultInfo(commonParam, list, flag, roleCode, isCountersign);
        if ("WARN".equals(list.getResultStat()) && list.getMess().contains("请联系4A管理员配置法务系统的")) {
            return new ProcessResult(ProcessResult.WARN, result.get(0).getEndName() + list.getMess());
        }
        return list;
    }

    /**
     * 功能描述:判断角色和组织各种情况
     *
     * @param commonParam
     * @param list
     * @param flag
     * @param roleCode
     * @param orgCode
     * @param jsonObject
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2019/12/19 19:24
     */
    private ProcessResult buildResultInfo(CommonParam commonParam, ProcessResult list, String flag, String roleCode, String isCountersign) {
        Map<String, Object> map = new HashMap<>();
        if (flag.equals(DisputeConstClass.ACTIVITY_NOT_LIMIT)) {
            return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.SUCCESS_MESS, list);
        }
        if (!flag.equals(DisputeConstClass.ACTIVITY_NOT_LIMIT)) {
            ProcessResult x = buildConditionResult(flag, roleCode, map, commonParam, isCountersign);
            //这个校验对象为null的时候才能往下查 不为null说明校验为通过有报错信息
            if (x != null) return x;
            list = licenseServiceClient.selectOrgTree(map);
        }
        return list;
    }


    private ProcessResult buildPTCGetDrafter(CommonParam commonParam, String roleCode, Map map) {
        if (StringUtil.isEmpty(commonParam.getLoginAcct())) {
            logger.info("请求参数loginAcct值={}", commonParam.getLoginAcct());
            return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
        }
        List<AccountLogic> accountLogic = orgDepLeaderVOMapper.selectOrgCodeByAccountId(commonParam.getLoginAcct());
        if (accountLogic.isEmpty()) {
            logger.info("指定loginAcct账号数据为空={}", accountLogic);
            return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
        }
        String unitId = accountLogic.get(0).getAccountUnitId();
        List<String> unitCodeAndRoleCode = Arrays.asList((roleCode + "#" + unitId).split("#"));
        map.put(DisputeConstClass.TYPE_CODE_PROVINCETOCITY, unitCodeAndRoleCode);
        map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_PROVINCETOCITY);
        return null;
    }

    private ProcessResult buildDepLeaderInfo(Map map) {
        UserInfo userInfo = RequestHolder.getUserInfo();
        String orgLevel = userInfo.getOrgLevel();
        String enterpTye = RequestHolder.getenterpType(userInfo.getUnitCode());
        String deptId = userInfo.getDeptId();
        List<AccountLogic> depLeader = orgDepLeaderVOMapper.selectDepLeader(orgLevel, enterpTye, deptId);
        if (depLeader.isEmpty()) {
            logger.info("请联系系统管理员设置分管领导");
            return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
        }
        map.put(DisputeConstClass.GROUPDEPT, depLeader);
        return null;

    }

    /**
     * 功能描述:condtion 节点限制
     *
     * @param flag
     * @param roleCode
     * @param jsonObject
     * @param joinlyCode
     * @param endId
     * @param companyCode
     * @param processInstId
     * @param loginAcct
     * @param executiveArmId
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2019/12/19 19:33
     */
    private ProcessResult buildConditionResult(String flag, String roleCode,  Map map, CommonParam commonParam, String isCountersign) {
        String joinlyCode = commonParam.getWhereParam().getJointlyOrgId();
        String unitOrgCode = commonParam.getWhereParam().getUnitCode();
        String endId = commonParam.getEndId();
        String companyCode = commonParam.getWhereParam().getMainSeedOrgId();
        Long processInstId = commonParam.getProcessInstId();
        String loginAcct = commonParam.getLoginAcct();
        String executiveArmId = commonParam.getWhereParam().getExecutiveArmId();
        UserInfo userInfo = RequestHolder.getUserInfo();
        //获取当前登录人组织code
        String currCode = userInfo.getDeptId();
        logger.info("condition限制=>{},节点角色编码=>{},当前登录人信息=>{}", flag, roleCode, userInfo);
        if (flag.equals(DisputeConstClass.ACTIVITY_UNIT_LIMIT)) {
            String unitCode = RequestHolder.getUnitCode(currCode);
            //获取当前登录人员的公司ID
            buildUnitLimit(roleCode, map, unitCode, isCountersign);
        }
        //特殊情况！！省份送地市限制取拟稿人公司code
        if (flag.equals(DisputeConstClass.ACTIVITY_PROVINCETOCITYCODE_LIMIT)) {
            buildPTCGetDrafter(commonParam, roleCode, map);
        }
        //特殊情况！前台传入当前流程的创建人loginAcct,判断是不是分管部门领导限制,然后再用创建人部门code 查询 t_orgdep_leader 关联账号表 查询下一步办理人信息,如果查不到返回空
        if (flag.equals(DisputeConstClass.ACTIVITY_GROUPDEPT_LIMIT)) {
            buildDepLeaderInfo(map);
        }

        /*法律文书，协办执行部门和执行单位限制*/
        ProcessResult x1 = buildSpeCondition(flag, roleCode, map, joinlyCode, executiveArmId, unitOrgCode, isCountersign);
        if (x1 != null) return x1;
        /*送申报人确认限制*/
        if (flag.equals(DisputeConstClass.ACTIVITY_FLOWTOSBR_LIMIT) && buildFlowToSBRInfo(roleCode, map, loginAcct)) {
            logger.info("请求参数loginAcct值={}", loginAcct);
            return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
        }
        /*需要送到之前某个环节的人*/
        if (flag.startsWith(DisputeConstClass.ACTIVITY_FLOWTOBEFORE_LIMIT)) {
            String[] arr = flag.split("#");
            ProcessResult x = buildFlowBackInfos(roleCode, map, processInstId, arr, currCode);
            if (x != null) return x;
        }

        //各流程涉及回退节点的参与者查询
        if (flag.equals(DisputeConstClass.ACTIVITY_FLOWBACK_LIMIT)) {
            flowUtil.init(userInfo.getLoginAcct(), userInfo.getLoginName(), DisputeConstClass.TENANLID, null, false);
            if (processInstId == null) {
                logger.info("回退环节流程实例ID为空processInstId={}", processInstId);
                return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
            }
            buildFlowBackInfo(roleCode, map, endId, processInstId, flowUtil, currCode);
        }
        /*跨区域查节点人员*/
        if (buildCrossAreaInfo(flag, roleCode, map, companyCode, currCode))
            return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
        /*同部门限制*/
        if (flag.equals(DisputeConstClass.ACTIVITY_DEPT_LIMIT)) {
            buildDeptLimit(map, currCode, roleCode, isCountersign);
        }
        if (flag.equals(DisputeConstClass.ACTIVITY_FINANCE_UNIT_LIMIT)) {
            List<OrgBeanVO> result = orgBeanMapper.selectOrgInfoByWhere(userInfo.getOrgId(), "财务部");
            if (result.isEmpty()) {
                logger.info("conditon:" + DisputeConstClass.ACTIVITY_FINANCE_UNIT_LIMIT + "公司Code={}查询财务部集合size={}", userInfo.getOrgId(), result.size());
                return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
            }
            //获取当前登录人公司财务部门code
            List<String> orgRole = Arrays.asList((roleCode + "#" + result.get(0).getOrgId()).split("#"));
            map.put(DisputeConstClass.TYPE_IS_COUNTERSIGN, "");
            map.put(DisputeConstClass.TYPE_CODE_ORGROLE, orgRole);
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_ORGROLE);
        }
        return null;
    }

    private ProcessResult buildSpeCondition(String flag, String roleCode, Map map, String joinlyCode, String executiveArmId, String unitOrgCode, String isCountersign) {
        /*特殊情况案件协办部门joinlyCode*/
        if (flag.equals(DisputeConstClass.ACTIVITY_JOINLY_LIMIT)) {
            //案件协办的协办部门
            if (StringUtils.isBlank(joinlyCode)) {
                logger.info("请选择案件协办部门joinlyCode={}", joinlyCode);
                return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
            }
            buildJoinlyLimit(map, joinlyCode, roleCode, isCountersign);
        }
        /*特殊情况法律文书执行部门executiveArmId*/
        if (flag.equals(DisputeConstClass.ACTIVITY_LEGISLATION_LIMIT)) {
            if (StringUtils.isBlank(executiveArmId)) {
                logger.info("请选择法律文书的执行部门executiveArmId={}", executiveArmId);
                return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
            }
            buildExecutivearmLimit(map, executiveArmId, roleCode, isCountersign);
        }
        if (flag.equals(DisputeConstClass.ACTIVITY_EXE_UNIT_LIMIT)) {
            if (StringUtils.isBlank(unitOrgCode)) {
                logger.info("请选择执行单位unitOrgCode={}", unitOrgCode);
                return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
            }
            buildExecutableUnitLimit(map, unitOrgCode, roleCode, isCountersign);
        }
        return null;
    }

    private void buildExecutableUnitLimit(Map map, String unitOrgCode, String roleCode, String isCountersign) {
        if (StringUtils.isBlank(roleCode)) {
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_UNIT);
            map.put(DisputeConstClass.TYPE_CODE_UNIT, unitOrgCode);
        } else {
            List<String> orgRole = Arrays.asList((roleCode + "#" + unitOrgCode).split("#"));
            map.put(DisputeConstClass.TYPE_IS_COUNTERSIGN, isCountersign);
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_UNITROLE);
            map.put(DisputeConstClass.TYPE_CODE_UNITROLE, orgRole);
        }
    }

    private void buildExecutivearmLimit( Map map, String executiveArmId, String roleCode, String isCountersign) {
        if (StringUtils.isBlank(roleCode)) {
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_ORG);
            map.put(DisputeConstClass.TYPE_CODE_ORG, executiveArmId);
        } else {
            List<String> orgRole = Arrays.asList((roleCode + "#" + executiveArmId).split("#"));
            map.put(DisputeConstClass.TYPE_IS_COUNTERSIGN, isCountersign);
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_ORGROLE);
            map.put(DisputeConstClass.TYPE_CODE_ORGROLE, orgRole);
        }
    }

    private void buildDeptLimit(Map map, String currCode, String roleCode, String isCountersign) {
        if (StringUtils.isBlank(roleCode)) {
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_ORG);
            map.put(DisputeConstClass.TYPE_CODE_ORG, currCode);
        } else {
            //获取当前登录人员的组织ID
            List<String> orgRole = Arrays.asList((roleCode + "#" + currCode).split("#"));
            map.put(DisputeConstClass.TYPE_IS_COUNTERSIGN, isCountersign);
            map.put(DisputeConstClass.TYPE_CODE_ORGROLE, orgRole);
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_ORGROLE);
        }
    }

    private void buildJoinlyLimit(Map map, String joinlyCode, String roleCode, String isCountersign) {

        if (StringUtils.isBlank(roleCode)) {
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_ORG);
            map.put(DisputeConstClass.TYPE_CODE_ORG, joinlyCode);
        } else {
            List<String> orgRole = Arrays.asList((roleCode + "#" + joinlyCode).split("#"));
            map.put(DisputeConstClass.TYPE_IS_COUNTERSIGN, isCountersign);
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_ORGROLE);
            map.put(DisputeConstClass.TYPE_CODE_ORGROLE, orgRole);
        }
    }

    private void buildUnitLimit(String roleCode, Map map, String unitCode, String isCountersign) {
        if (StringUtils.isBlank(roleCode)) {
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_UNIT);
            map.put(DisputeConstClass.TYPE_CODE_UNIT, unitCode);
        } else {
            List<String> unitRole = Arrays.asList((roleCode + "#" + unitCode).split("#"));
            map.put(DisputeConstClass.TYPE_IS_COUNTERSIGN, isCountersign);
            map.put(DisputeConstClass.TYPE_CODE_UNITROLE, unitRole);
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_UNITROLE);
        }
    }

    private ProcessResult buildFlowBackInfos(String roleCode, Map map, Long processInstId, String[] arr, String currCode) {
        if (StringUtils.isNotBlank(arr[0]) && DisputeConstClass.ACTIVITY_FLOWTOBEFORE_LIMIT.equals(arr[0])) {
            String actId = arr[1];
            if (StringUtil.isEmpty(actId)) {
                logger.info("condition={},流程节点未配置actid", DisputeConstClass.ACTIVITY_FLOWTOBEFORE_LIMIT);
                return new ProcessResult(ProcessResult.WARN, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
            }
            UserInfo userInfo = RequestHolder.getUserInfo();
            flowUtil.init(userInfo.getLoginAcct(), userInfo.getLoginName(), DisputeConstClass.TENANLID, null, false);
            if (processInstId == null) {
                logger.info("环节流程实例ID为空={}", processInstId);
                return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.FAILURE.TA_FAILURE_MESS);
            }
            buildFlowBackInfo(roleCode, map, actId, processInstId, flowUtil, currCode);
        }
        return null;
    }

    /**
     * 功能描述:组装回退到申报人节点查人
     *
     * @param roleCode
     * @param jsonObject
     * @param loginAcct
     * @return boolean
     * @auther: guodong
     * @date: 2019/12/19 18:22
     */
    private boolean buildFlowToSBRInfo(String roleCode, Map map, String loginAcct) {
        if (StringUtils.isBlank(loginAcct)) {
            return true;
        }
        //判断申报人是否离职（需要角色code和部门code）
        List<AccountLogic> accountLogic = orgDepLeaderVOMapper.selectOrgCodeByAccountId(loginAcct);
        if (accountLogic.isEmpty()) {
            logger.info("查询loginAcct={}账号数据为空", loginAcct);
            throw new MyOwnRuntimeException("查询loginAcct=" + loginAcct + "账号数据为空");
        }
        String accountDeptId = accountLogic.get(0).getAccountDeptId();
        if ("3".equals(accountDeptId)) {
            List<String> orgRole = Arrays.asList((roleCode + "#" + accountDeptId).split("#"));
            map.put(DisputeConstClass.TYPE_IS_COUNTERSIGN, "");
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_ORGROLE);
            map.put(DisputeConstClass.TYPE_CODE_ORGROLE, orgRole);
        } else {
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.ACTIVITY_FLOWBACK_USERCODE);
            map.put(DisputeConstClass.ACTIVITY_FLOWBACK_USERCODE, loginAcct);
        }
        return false;
    }

    /**
     * 功能描述: 回退查询节点人员
     *
     * @param roleCode
     * @param jsonObject
     * @param endId
     * @param processInstId
     * @param flowUtil
     * @return void
     * @auther: guodong
     * @date: 2019/12/19 18:22
     */
    private void buildFlowBackInfo(String roleCode, Map map, String endId, Long processInstId, FlowUtil flowUtil, String currCode) {
        String userCode = flowUtil.getActivityInstParticipantByFlowId(processInstId, endId);
        //判断申报人是否离职（需要角色code和部门code）
        List<AccountLogic> accountLogic = orgDepLeaderVOMapper.selectOrgCodeByAccountId(userCode);
        if (accountLogic.isEmpty()) {
            logger.info("userCode={}账号数据为空", userCode);
            throw new MyOwnRuntimeException("查询loginAcct=" + userCode + "账号数据为空");
        }
        if (StringUtils.isBlank(userCode)) {
            String unitCode = RequestHolder.getUnitCode(currCode);
            //获取当前登录人员的公司ID
            List<String> unitRole = Arrays.asList((roleCode + "#" + unitCode).split("#"));
            map.put(DisputeConstClass.TYPE_IS_COUNTERSIGN, "");
            map.put(DisputeConstClass.TYPE_CODE_UNITROLE, unitRole);
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_UNITROLE);
        } else if ("3".equals(accountLogic.get(0).getAccountStatus())) {
            List<String> orgRole = Arrays.asList((roleCode + "#" + accountLogic.get(0).getAccountDeptId()).split("#"));
            map.put(DisputeConstClass.TYPE_IS_COUNTERSIGN, "");
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_ORGROLE);
            map.put(DisputeConstClass.TYPE_CODE_ORGROLE, orgRole);
        } else {
            map.put(DisputeConstClass.ACTIVITY_FLOWBACK_USERCODE, userCode);
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.ACTIVITY_FLOWBACK_USERCODE);
        }
    }

    /**
     * 功能描述:跨区域信息
     *
     * @param flag
     * @param roleCode
     * @param jsonObject
     * @param companyCode
     * @param currCode
     * @return boolean
     * @auther: guodong
     * @date: 2019/12/19 18:21
     */
    private boolean buildCrossAreaInfo(String flag, String roleCode, Map map, String companyCode, String currCode) {
        /*涉及向下级部门查询*/
        if ((flag.equals(DisputeConstClass.ACTIVITY_HEADTORPOVINCE_LIMIT) || flag.equals(DisputeConstClass.ACTIVITY_PROVINCETOCITY_LIMIT)) && StringUtils.isBlank(companyCode)) {
            logger.info("请指定下级MainSeedOrgId={}", companyCode);
            return true;
        }
        /*涉及跨地域级别带条件（flag）查询*/
        buildCrossArea(flag, roleCode, map, companyCode, currCode);
        return false;
    }


    /**
     * 功能描述:跨区域查询节点人员
     *
     * @param flag
     * @param roleCode
     * @param jsonObject
     * @param companyCode
     * @param currCode
     * @return void
     * @auther: guodong
     * @date: 2019/12/19 18:20
     */
    private void buildCrossArea(String flag, String roleCode, Map map, String companyCode, String currCode) {
        //获取省份code
        String provinceCode = RequestHolder.getProvinceCode(currCode);
        List<String> orgCodeAndRoleCode = null;
        if (flag.equals(DisputeConstClass.ACTIVITY_PROVINCETOHEAD_LIMIT)) {
            //查出总部组织code
            OrgBeanVO result = orgBeanMapper.selectHeadBycurrCode(currCode);
            orgCodeAndRoleCode = Arrays.asList((roleCode + "#" + result.getOrgCode()).split("#"));
        } else if (flag.equals(DisputeConstClass.ACTIVITY_HEADTORPOVINCE_LIMIT)) {
            //特殊！！案件交办总送省，地市退回到总部，需要判断主送单位是否未地市公司，如果是，转成省份Code,
            OrgBeanVO orgBeanVO = orgBeanMapper.selectOrgLevel(companyCode);
            if ("03".equals(orgBeanVO.getOrgLevel())) {
                companyCode = RequestHolder.getProvinceCode(companyCode);
            }
            orgCodeAndRoleCode = Arrays.asList((roleCode + "#" + companyCode).split("#"));
        } else {
            orgCodeAndRoleCode = Arrays.asList((roleCode + "#" + companyCode).split("#"));
        }
        /*流程省分查总部人员*/
        if (flag.equals(DisputeConstClass.ACTIVITY_PROVINCETOHEAD_LIMIT)) {
            map.put(DisputeConstClass.TYPE_CODE_PROVINCETOHEAD, orgCodeAndRoleCode);
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_PROVINCETOHEAD);
        }
        /*流程地市查省分人员*/
        if (flag.equals(DisputeConstClass.ACTIVITY_CITYTOPROVINCE_LIMIT)) {
            if (StringUtils.isBlank(roleCode)) {
                map.put(DisputeConstClass.TYPE_CODE_UNIT, provinceCode);
                map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_UNIT);
            } else {
                //将流程角色和当前登录人code组装
                orgCodeAndRoleCode = Arrays.asList((roleCode + "#" + provinceCode).split("#"));
                map.put(DisputeConstClass.TYPE_CODE_CITYTOPROVINCE, orgCodeAndRoleCode);
                map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_CITYTOPROVINCE);
            }
        }
        /*流程总部查省分人员*/
        if (flag.equals(DisputeConstClass.ACTIVITY_HEADTORPOVINCE_LIMIT)) {
            map.put(DisputeConstClass.TYPE_CODE_HEADTORPOVINCE, orgCodeAndRoleCode);
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_HEADTORPOVINCE);
        }
        /*流程省分查地市人员*/
        if (flag.equals(DisputeConstClass.ACTIVITY_PROVINCETOCITY_LIMIT)) {
            map.put(DisputeConstClass.TYPE_CODE_PROVINCETOCITY, orgCodeAndRoleCode);
            map.put(DisputeConstClass.TYPE_CODE, DisputeConstClass.TYPE_CODE_PROVINCETOCITY);
        }
    }

}
