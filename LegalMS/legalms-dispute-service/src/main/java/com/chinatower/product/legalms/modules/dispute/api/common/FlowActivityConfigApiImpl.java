package com.chinatower.product.legalms.modules.dispute.api.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinatower.product.legalms.modules.dispute.entity.LegislationVO;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssist;
import com.chinatower.product.legalms.modules.dispute.mapper.assist.TIssueAssistMapper;
import com.chinatower.product.legalms.modules.flow.service.version.FlowVersionService;
import com.chinatower.product.legalms.modules.flow.vo.common.FlowActivityConfigUpdateVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.dispute.entity.issue.TIssueJointly;
import com.chinatower.product.legalms.modules.dispute.entity.lawcase.TCaseAssign;
import com.chinatower.product.legalms.modules.dispute.mapper.issue.TIssueJointlyMapper;
import com.chinatower.product.legalms.modules.dispute.mapper.lawcase.TCaseAssignMapper;
import com.chinatower.product.legalms.modules.dispute.mapper.legislation.LegislationMapper;
import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityConfigVO;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;
import com.chinatower.product.legalms.modules.flow.entity.common.SysQuickButtonVO;
import com.chinatower.product.legalms.modules.flow.service.common.OrgBeanService;
import com.chinatower.product.legalms.modules.flow.vo.common.PageList;
import com.chinatower.product.legalms.modules.flow.vo.flow.SubTFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.TFlowLogVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.chinatower.product.legalms.modules.flow.service.common.*;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags={"活动配置接口"})
public class FlowActivityConfigApiImpl extends BaseController implements FlowActivityConfigApi {

    private static final Logger logger = LoggerFactory.getLogger("TransLog");

    @Autowired
    private FlowActivityConfigService flowActivityConfigService;

    @Autowired
    private OrgBeanService orgBeanService;

    @Autowired
    private TCaseAssignMapper tCaseAssignMapper;

    @Autowired
    private TIssueJointlyMapper tIssueJointlyMapper;

    @Autowired
    LegislationMapper legislationMapper;

    @Autowired
    TIssueAssistMapper tIssueAssistMapper;

    @Autowired
    private FlowVersionService flowVersionService;

    @Override
    @ApiOperation("查询所有流程")
    public ProcessResult selParamActivityConfig(@RequestBody PageList param) {
        try {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            List<FlowActivityConfigVO> list = flowActivityConfigService.selParamActivityConfig(param);
            PageInfo<FlowActivityConfigVO> pageInfo = new PageInfo<>(list);
            return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
        } catch (Exception e) {
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }

    /*
    * 查询当前活动的下一步活动列表
    * 当前流程模板id flowId 必填
    * 当前活动id     beginId 必填
    * 重要程度 importantLevel  默认 简单支撑决策,normal
    */
    @Override
    @ApiOperation(value = "查询当前活动的下一步活动列表 ")
    public ProcessResult selActivityConfig(@RequestBody FlowActivityConfigVO flowActivityConfigVO) {
        try {
            //logger.info("输入参数："+flowActivityConfigVO.toString());
            // 交办标志 传入交办部门的code 查询到这个部门属于省分还是地市
            flowActivityConfigVO = getBusinessObject(flowActivityConfigVO);

            List<FlowActivityConfigVO> list = flowActivityConfigService.selActivityConfig(flowActivityConfigVO);

            //根据日志记录来过滤退回的线
            if("1".equals(flowActivityConfigVO.getIsReturn()) && !list.isEmpty()){
                list = dealWithReturnLine(list,flowActivityConfigVO);
            }

            return new ProcessResult(ProcessResult.SUCCESS,"",list);
        } catch (Exception e) {
            logger.info(DisputeConstClass.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    private List<FlowActivityConfigVO> dealWithReturnLine(List<FlowActivityConfigVO> list,FlowActivityConfigVO flowActivityConfigVO){
        //用来存储经办过的环节id及其处理时间 modifyTime
        List<Map<String,Object>> mapList = new ArrayList<>();
        //确保logs对象有值
        if(flowActivityConfigVO.getMian()!=null && !flowActivityConfigVO.getMian().getLogs().isEmpty()){
            dealWithReturnLineMore(list,flowActivityConfigVO,mapList);
        }
        return getList(list,mapList);
    }

    private List<FlowActivityConfigVO> getList(List<FlowActivityConfigVO> list,List<Map<String,Object>> mapList){
        List<FlowActivityConfigVO> listnew = new ArrayList<>();
        //如果查询出来只有一根线就不用判断 存不存在拟稿的线了
        if(list.size()>1){
            // 如果存在退回拟稿的线 就只能是两根线
            // 如果不存在拟稿的线 就只能是一根线
            // 获取退回起草的那条线
            FlowActivityConfigVO vo = existQcFlag(list);
            //找到最新的那条线
            FlowActivityConfigVO voNew = getNewVo(list, mapList);
            if(voNew!=null){
                // 如果这个起草的线和最新的线不同的话 才需要添加起草的线
                if(vo!=null && !vo.getEndId().equals(voNew.getEndId())){
                    listnew.add(vo);
                }
                listnew.add(voNew);
            }else{
                return list;
            }
        }else{
            return list;
        }
        return listnew;
    }

    /**
     * 获取最新的那条线
     * @param list
     * @param mapList
     * @return
     */
    private FlowActivityConfigVO getNewVo(List<FlowActivityConfigVO> list,List<Map<String,Object>> mapList){
        String newestActId = getNewestActId(mapList);
        if(StringUtils.isNotBlank(newestActId)){
            return getNewVoFromList(newestActId, list);
        }
        return null;
    }

    /**
     * 根据最新的环节id找到对应的线
     * @param newestActId
     * @param list
     * @return
     */
    private FlowActivityConfigVO getNewVoFromList(String newestActId, List<FlowActivityConfigVO> list) {
        for(int i=0;i<list.size();i++){
            if(newestActId.equals(list.get(i).getEndId())){
                return list.get(i);
            }
        }
        return null;
    }

    /**
     * 获取到时间最新的那个环节id
     * @param mapList
     * @return
     */
    private String getNewestActId(List<Map<String, Object>> mapList) {
        if(!mapList.isEmpty()){
            String actId = (String)mapList.get(0).get(DisputeConstClass.ACTID);
            Date time =  (Date)mapList.get(0).get(DisputeConstClass.TIME);
            for(int i = 0;i<mapList.size();i++){
                Date timeNew = (Date)mapList.get(i).get(DisputeConstClass.TIME);
                if(timeNew.compareTo(time)>0){
                    actId = (String)mapList.get(i).get(DisputeConstClass.ACTID);
                    time =  (Date)mapList.get(i).get(DisputeConstClass.TIME);
                }
            }
            return actId;
        }
        return null;
    }

    /**
     * 是否存在退回拟稿的线
     * @return
     */
    private FlowActivityConfigVO existQcFlag(List<FlowActivityConfigVO> list){
        List<Map<String, String>> dictMaps = legislationMapper.dictMaps("sys_qchj_value");
        for(int i= list.size()-1;i>=0;i--){
            FlowActivityConfigVO vo = list.get(i);
            if(vo!=null){
                for (Map<String, String> map : dictMaps) {
                    String flowId = map.get("dict_cabel");
                    String actId = map.get("dict_value");
                    if(flowId.equals(vo.getFlowId()) && actId.equals(vo.getEndId())){
                        return vo;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 根据日志记录来去掉未经办过的线
     * @param list
     * @param flowActivityConfigVO
     */
    private void dealWithReturnLineMore(List<FlowActivityConfigVO> list,FlowActivityConfigVO flowActivityConfigVO,List<Map<String,Object>> mapList){
        List<TFlowLogVO> logs = flowActivityConfigVO.getMian().getLogs();
        for(int j=list.size()-1;j>=0;j--){
            String endId = list.get(j).getEndId();
            boolean flag = getFlag(endId, logs, mapList);
            if(flag){
                list.remove(j);
            }
        }
    }

    /**
     * 先判断主流程日志是否经办过
     * @param endId
     * @param logs
     * @return
     */
    private boolean getFlag(String endId,List<TFlowLogVO> logs,List<Map<String,Object>> mapList){
        for(int i=logs.size()-1;i>=0;i--){
            TFlowLogVO vo = logs.get(i);
            if(vo!=null){
                //送出活动环节id 才是已经处理过的环节id
                String activityDefId = vo.getActivityDefId();
                if(StringUtils.isNotBlank(endId) && endId.equals(activityDefId)){
                    Map<String,Object> map = new HashMap<>();
                    map.put(DisputeConstClass.ACTID,activityDefId);
                    map.put(DisputeConstClass.TIME,vo.getModifyTime());
                    mapList.add(map);
                    return false;
                }
                //子流程的日志还要判断
                List<SubTFlowLogVO> subLogs =vo.getSubLogs();
                if(!getSubLogFlag(endId,subLogs,mapList)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *再判断子流程日志是否经办过
     * @param endId
     * @param subLogs
     * @return
     */
    private boolean getSubLogFlag(String endId,List<SubTFlowLogVO> subLogs,List<Map<String,Object>> mapList){
        if(subLogs!=null && !subLogs.isEmpty()){
            for(int j=subLogs.size()-1;j>=0;j--){
                if(subLogs.get(j)!=null){
                    String subactivityDefId = subLogs.get(j).getActivityDefId();
                    if(StringUtils.isNotBlank(endId) && endId.equals(subactivityDefId)){
                        Map<String,Object> map = new HashMap<>();
                        map.put(DisputeConstClass.ACTID,subactivityDefId);
                        map.put(DisputeConstClass.TIME,subLogs.get(j).getModifyTime());
                        mapList.add(map);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param flowActivityConfigVO
     * @return
     */
    private FlowActivityConfigVO getBusinessObject(FlowActivityConfigVO flowActivityConfigVO) {
        String flowId = flowActivityConfigVO.getFlowId();
        String beginId = flowActivityConfigVO.getBeginId();
        // 交办流程 传入交办部门的code 查询到这个部门属于省分还是地市
        flowActivityConfigVO = getLawcase(flowId,beginId,flowActivityConfigVO);

        // 协办流程 根据协办部门所在层级不同 所走的流程环节也有不同
        flowActivityConfigVO = getJointly(flowId, beginId, flowActivityConfigVO);

        // 法律文书流程
        flowActivityConfigVO = getLegislation(flowId, beginId, flowActivityConfigVO);

        // 协助执行流程
        flowActivityConfigVO = getAssist(flowId, beginId, flowActivityConfigVO);

        return flowActivityConfigVO;
    }

    /**
     * com.tower.issue.assist.head
     * com.tower.issue.assist.state
     * com.tower.issue.assist.city
     * @param flowId
     * @param beginId
     * @param flowActivityConfigVO
     * @return
     */
    private FlowActivityConfigVO getAssist(String flowId, String beginId, FlowActivityConfigVO flowActivityConfigVO) {
        //协助执行处理线逻辑
        if(("com.tower.issue.assist.head".equals(flowId) && "fwbmfzrsp".equals(beginId))
                || ("com.tower.issue.assist.state".equals(flowId) && "fwbmfzrsp".equals(beginId))
                || ("com.tower.issue.assist.city".equals(flowId) &&"zhbfzrsp".equals(beginId))
                || ("com.tower.issue.assist.city".equals(flowId) &&"sffwbmfzrsp".equals(beginId))){
            TIssueAssist tIssueAssist = tIssueAssistMapper.selectByPrimaryKey(flowActivityConfigVO.getBusinessId());
            if(tIssueAssist!=null  && StringUtils.isNotBlank(tIssueAssist.getJointlyUnitId())){
                //因为执行单位只能是同级的 所以取第一个执行单位就行了
                return getFlowActivityConfigVO(flowActivityConfigVO,tIssueAssist.getJointlyUnitId().split(",")[0]);
            }
        }
        return flowActivityConfigVO;
    }

    private FlowActivityConfigVO getLegislation(String flowId, String beginId, FlowActivityConfigVO flowActivityConfigVO) {
        //法律文书处理线逻辑
        if(("com.tower.issue.legislation.head".equals(flowId) && "fwbmfzrsp".equals(beginId))
                || ("com.tower.issue.legislation.head".equals(flowId)  && "sffwbmfzrsp".equals(beginId))
                || ("com.tower.issue.legislation.state".equals(flowId) && "fwbmfzrsp".equals(beginId))
                || ("com.tower.issue.legislation.state".equals(flowId) && "fwryzs".equals(beginId))
                || ("com.tower.issue.legislation.city".equals(flowId) &&"zhbfzrsp".equals(beginId))
                || ("com.tower.issue.legislation.city".equals(flowId) &&"sffwbmfzrsp".equals(beginId))){
            LegislationVO legislationVO = legislationMapper.selectByPrimaryKey(flowActivityConfigVO.getBusinessId());
            if(legislationVO!=null){
                return getFlowActivityConfigVO(flowActivityConfigVO,legislationVO.getExecuteUnitId());
            }
        }
        return flowActivityConfigVO;
    }


    private FlowActivityConfigVO getJointly(String flowId, String beginId, FlowActivityConfigVO flowActivityConfigVO) {
        //案件协办线逻辑
        if(("com.tower.issue.jointly.head".equals(flowId)             && "fwbmfzrsp".equals(beginId))
                || ("com.tower.issue.jointly.head".equals(flowId)     && "sffwbmfzrsp1".equals(beginId))
                || ("com.tower.issue.jointly.state".equals(flowId) && "fwbmfzrsp".equals(beginId))
                || ("com.tower.issue.jointly.state".equals(flowId) && "fwryzs".equals(beginId))
                || ("com.tower.issue.jointly.city".equals(flowId)     && "zhbfzrsp".equals(beginId))
                || ("com.tower.issue.jointly.city".equals(flowId)     && "sffwbmfzrsp".equals(beginId))
                ){
            TIssueJointly tIssueJointly = tIssueJointlyMapper.selectByPrimaryKey(flowActivityConfigVO.getBusinessId());
            if(tIssueJointly!=null){
                return getFlowActivityConfigVO(flowActivityConfigVO,tIssueJointly.getJointlyUnitId());
            }
        }
        return flowActivityConfigVO;
    }

    /**
     *
     * @param flowId
     * @param beginId
     * @param flowActivityConfigVO
     * @return
     */
    private FlowActivityConfigVO getLawcase(String flowId, String beginId, FlowActivityConfigVO flowActivityConfigVO) {
        if("com.tower.issue.lawcase.head".equals(flowId) && "sffwbmfzrsp".equals(beginId)){
            TCaseAssign tCaseAssign = tCaseAssignMapper.selectByPrimaryKey(flowActivityConfigVO.getBusinessId());
            if(tCaseAssign!=null){
                return getFlowActivityConfigVO(flowActivityConfigVO,tCaseAssign.getMainSeedOrgId());
            }
        }
        return flowActivityConfigVO;
    }

    private FlowActivityConfigVO getFlowActivityConfigVO(FlowActivityConfigVO flowActivityConfigVO,String orgCode) {
        OrgBeanVO orgBeanVO = new OrgBeanVO();
        // 取出执行公司id
        orgBeanVO.setOrgCode(orgCode);
        // 获得该公司的层级
        orgBeanVO = (OrgBeanVO) orgBeanService.selectOrgLevelByorgCode(orgBeanVO).getData();
        if(orgBeanVO!=null){
            flowActivityConfigVO.setHandoverLevel(orgBeanVO.getOrgLevel());
        }
        return flowActivityConfigVO;
    }

    @Override
    @ApiOperation(value = "通过ID查询")
    public ProcessResult selActivityId(String flowId,String beginId,String endId,Integer versionId) {
        FlowActivityConfigVO flowActivityConfig = null;
        try {
            flowActivityConfig = flowActivityConfigService.selActivityId(flowId, beginId, endId,versionId);
            return new ProcessResult(ProcessResult.SUCCESS,"",flowActivityConfig);
        }catch (Exception e){
            logger.info(DisputeConstClass.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    /*
     * 查询这个流程有多少个会签活动
     */
    @Override
    @ApiOperation(value = "查询这个流程有多少个会签活动 ")
    public ProcessResult selActivityCount(@RequestBody FlowActivityConfigVO flowActivityConfigVO) {
        int a;
        try {
            a = flowActivityConfigService.selActivityCount(flowActivityConfigVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",a);
        }catch (Exception e) {
            logger.info(DisputeConstClass.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    private SysQuickButtonVO getSysQuickButtonVO(FlowActivityConfigVO flowActivityConfigVO){
        SysQuickButtonVO sysQuickButtonVO =new SysQuickButtonVO();
        sysQuickButtonVO.setModuleName(flowActivityConfigVO.getModuleName());
        //获取用户信息
        UserInfo userInfo = RequestHolder.getUserInfo();
        String level = userInfo.getOrgLevel();
        sysQuickButtonVO.setLevel(level); //暂不获取 调用后台公用方法获取 当前人的层级

        List<String> list = Arrays.asList(flowActivityConfigVO.getRoleCodes().split(","));
        sysQuickButtonVO.setRoleCodes(list);
        sysQuickButtonVO.setIsJiyuehua(userInfo.getIsJiYueHua());
        return sysQuickButtonVO;
    }

    /**
     * 查询当前流程权限
     * 如果 是   拟稿 无需传入 流程模板id和 活动id 只要前台moduleName 和角色id
     * 如果 不是 拟稿 需要传入 流程模板id 和 当前活动id
     */
    @Override
    @ApiOperation(value = "查询当前流程权限 ")
    public ProcessResult selActivityJurisdiction(@RequestBody FlowActivityConfigVO flowActivityConfigVO) {
        if(StringUtils.isBlank(flowActivityConfigVO.getRoleCodes())){
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.REQUEST_ERR_PARAM+"roleCodes");
        }
        try {
            String flowStartAct = "";
            String flowStartActName = "";
            Integer versionId = 1;
            if(flowActivityConfigVO.getFlowId()==null || flowActivityConfigVO.getBeginId()==null){
                // 获取当前登录人 所在 总部 省分 地市 和 角色就已经绑定了能够起草哪个流程
                SysQuickButtonVO sysQuickButtonVO = getSysQuickButtonVO(flowActivityConfigVO);

                // 备选流程模板id 对象集合
                List<SysQuickButtonVO> list = flowActivityConfigService.selectByRoleCode(sysQuickButtonVO);

                if(!list.isEmpty()){
                    String flowId = list.get(0).getFlowId();
                    flowStartAct = list.get(0).getFlowStartAct();
                    flowStartActName = list.get(0).getFlowStartActName();
                    versionId = list.get(0).getVersionId();

                    flowActivityConfigVO.setFlowId(flowId);
                    flowActivityConfigVO.setBeginId(flowStartAct);
                    flowActivityConfigVO.setVersionId(versionId);
                }else{
                    return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.MESSAGE_UNRIGHT);
                }
            }
            Map<String,Object> map = new HashMap<>();
            String permissionJson = flowActivityConfigService.judgeFlow(flowActivityConfigVO.getFlowId(),flowActivityConfigVO.getBeginId(),flowActivityConfigVO.getVersionId());
            if(StringUtils.isBlank(permissionJson)){
                return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.MESSAGE_UNACTRIGHT);
            }
            map.put("permissionJson",permissionJson);
            map.put(DisputeConstClass.FLOW_ID,flowActivityConfigVO.getFlowId());
            map.put(DisputeConstClass.FLOW_START_ACT,flowStartAct);
            map.put("flowStartActName",flowStartActName);
            map.put("versionId",versionId);
            return new ProcessResult(ProcessResult.SUCCESS,"SUCCESS",map);
        }catch (Exception e) {
            logger.info(DisputeConstClass.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }


    @Override
    @ApiOperation(value = "更新流程表")
    public ProcessResult updateActivityConfig(@RequestBody FlowActivityConfigVO flowActivityConfigVO) {
        int i = 0;
        try {
            // 如果开始环节名称和开始环节id 结束环节名称和结束环节id 有变化 在这个模板这套数据 中 都需要更新
            dealWithUpdate(flowActivityConfigVO);
            i = flowActivityConfigService.updateActivityConfig(flowActivityConfigVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",i);
        }catch (Exception e){
            logger.info(DisputeConstClass.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    private void dealWithUpdate(FlowActivityConfigVO flowActivityConfigVO) {
        FlowActivityConfigVO vo = new FlowActivityConfigVO();
        vo.setId(flowActivityConfigVO.getId());
        List<FlowActivityConfigVO> list = flowActivityConfigService.selActivityConfig(vo);
        List<FlowActivityConfigUpdateVO> configUpdateVOs = new ArrayList<>();
        if(!list.isEmpty()){
            FlowActivityConfigVO voOrigin = list.get(0);
            if(!flowActivityConfigVO.getBeginId().equals(voOrigin.getBeginId()) || !flowActivityConfigVO.getBeginName().equals(voOrigin.getBeginName())){
                FlowActivityConfigUpdateVO updateVO = new  FlowActivityConfigUpdateVO();
                updateVO.setFlowId(flowActivityConfigVO.getFlowId());
                updateVO.setVersionId(flowActivityConfigVO.getVersionId());
                updateVO.setActId(flowActivityConfigVO.getBeginId());
                updateVO.setActName(flowActivityConfigVO.getBeginName());
                updateVO.setFlowIdOld(voOrigin.getFlowId());
                updateVO.setVersionIdOld(voOrigin.getVersionId());
                updateVO.setActIdOld(voOrigin.getBeginId());
                updateVO.setActNameOld(voOrigin.getBeginName());

                configUpdateVOs.add(updateVO);
            }
            if(!flowActivityConfigVO.getEndId().equals(voOrigin.getEndId()) || !flowActivityConfigVO.getEndName().equals(voOrigin.getEndName())) {
                FlowActivityConfigUpdateVO updateVO = new  FlowActivityConfigUpdateVO();
                updateVO.setFlowId(flowActivityConfigVO.getFlowId());
                updateVO.setVersionId(flowActivityConfigVO.getVersionId());
                updateVO.setActId(flowActivityConfigVO.getEndId());
                updateVO.setActName(flowActivityConfigVO.getEndName());
                updateVO.setFlowIdOld(voOrigin.getFlowId());
                updateVO.setVersionIdOld(voOrigin.getVersionId());
                updateVO.setActIdOld(voOrigin.getEndId());
                updateVO.setActNameOld(voOrigin.getEndName());

                configUpdateVOs.add(updateVO);
            }

            if(!configUpdateVOs.isEmpty()){
                flowActivityConfigService.updateConfig(configUpdateVOs);
            }
        }
    }

    @Override
    public ProcessResult addActivityConfig(@RequestBody FlowActivityConfigVO flowActivityConfigVO) {
        try {
            int i = flowActivityConfigService.addActivityConfig(flowActivityConfigVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",i);
        }catch (Exception e){
            logger.info(DisputeConstClass.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    @Override
    public ProcessResult delActivityConfig(@RequestBody FlowActivityConfigVO flowActivityConfigVO) {
        try {
            int i = flowActivityConfigService.delActivityConfig(flowActivityConfigVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",i);
        }catch (Exception e){
            logger.info(DisputeConstClass.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

}
