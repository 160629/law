package com.chinatower.product.legalms.modules.dispute.api.assist;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.*;
import com.chinatower.product.legalms.modules.dispute.entity.assist.TIssueAssist;
import com.chinatower.product.legalms.modules.dispute.service.assist.TIssueAssistService;
import com.chinatower.product.legalms.modules.dispute.service.flow.TFlowService;
import com.chinatower.product.legalms.modules.dispute.vo.assist.FindTIssueAssistListListParamVO;
import com.chinatower.product.legalms.modules.dispute.vo.assist.SelectAllListParamVO;
import com.chinatower.product.legalms.modules.dispute.vo.assist.SelectRelationshipListInCreateParamVO;
import com.chinatower.product.legalms.modules.dispute.vo.assist.SelectRelationshipListParamVO;
import com.chinatower.product.legalms.modules.flow.entity.common.DraftsVO;
import com.chinatower.product.legalms.modules.flow.entity.delegate.TFlowDelegate;
import com.chinatower.product.legalms.modules.flow.entity.flow.TFlowLog;
import com.chinatower.product.legalms.modules.flow.mapper.common.DraftsVOMapper;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowLogVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.OrgParticipantVO;
import com.eos.workflow.data.WFWorkItem;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = {"协助执行流程接口实现类"})
@RestController
public class TIssueAssistApiImpl extends BaseController implements TIssueAssistApi {

    // 日志对象
    private static final Logger logger = LoggerFactory.getLogger(TIssueAssistApiImpl.class);

    // 协助执行Service
    @Autowired
    private TIssueAssistService tIssueAssistService;

    @Autowired
    TFlowService tFlowService;

    @Autowired
    TFlowCommonService commonService;

    // 流程对象
    @Autowired
    FlowUtil flowUtil;

    @Autowired
    DraftsVOMapper draftsVOMapper;

    @Override
//    @SmsAround
    public ProcessResult addTIssueAssist(@RequestBody AddFlowVO<TIssueAssist> vo) {
        logger.info("协助执行流程起草参数：" + vo.toString());
        UserInfo info = RequestHolder.getUserInfo();
        TIssueAssist model = vo.getModel();
        if(null==info|| null==model) {
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.PARAM_EMPTY);
        }
        if (StringUtils.isBlank(model.getCode())) {
            String code = tIssueAssistService.selectCode(info.getUnitCode(),CoreConstClass.XZ, info.getOrgCode());
            model.setCode(code);
            vo.setAddFlag(1);
        }
        StringUtil.copyProperties(model,info);
        flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, false);
        int i = tIssueAssistService.addTIssueAssist(vo, flowUtil, info);
        List<OrgParticipantVO> orgs = vo.getOrgs();
        Long flowId = flowUtil.getFlowId();
        try {
            TFlowLog log = new TFlowLog();
            flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, true);
            flowUtil.setFlowId(flowId);
            i += tIssueAssistService.addFlow(vo,  flowUtil, info,log);
            Long activityInstID = flowUtil.getActivityInstID();
            flowUtil.clientCommit(true);
            tFlowService.setIsUnView(vo.getModel().getSignDept(), info, flowId);
            flowUtil.init(info.getLoginAcct(), info.getLoginName(), CoreConstClass.TENANLID, null, false);
            List<WFWorkItem> wfWorkItems = flowUtil.queryNextWorkItemsByActivityInstID(activityInstID, false);
            for (int j = 0; wfWorkItems != null && j < wfWorkItems.size(); j++) {
                TFlowDelegate delegate = commonService.verifyDelegate(wfWorkItems.get(j).getParticipant());
                if (null != delegate) {
                    flowUtil.init(wfWorkItems.get(j).getParticipant(), wfWorkItems.get(j).getPartiName(), CoreConstClass.TENANLID, null, true);
                    flowUtil.delegateWorkItem(delegate.getToerId(), delegate.getToerName(),wfWorkItems.get(j).getWorkItemID());
                    flowUtil.clientCommit(true);
                }
            }
            String signDept = vo.getModel().getSignDept();
            if ("1".equals(signDept)) {
                tIssueAssistService.addOrgsUndone(orgs);
            } else {
                tIssueAssistService.addUndone(orgs.get(0).getDepaInterPersonP());
            }

            return new ProcessResult(ProcessResult.SUCCESS, "", i);
        } catch (Exception e) {
            logger.info("业务处理异常,流程业务回滚" , e);
            tFlowService.flowBack(tIssueAssistService.getClass(), vo.getModel().getId(), flowUtil);
            return new ProcessResult(ProcessResult.ERROR,CoreConstClass.FAILURE_MESS);
        }
    }

    @Override
    @SmsAround
    public ProcessResult addTFlowLog(HttpServletRequest request) {
        AddFlowLogVO vo=(AddFlowLogVO) request.getAttribute("vo");
        logger.info("协助执行审批参数:vo={}" , vo.toString());
        try {
            return tIssueAssistService.addTFlowLog(vo);
        } catch (Exception e) {
            logger.info("协助执行审批处理异常" , e);
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }

    /**
     * 起草暂存
     *
     * @param vo 流程起草对象
     * @return
     */
    @Override
    public ProcessResult tempTIssueAssist(@RequestBody AddFlowVO<TIssueAssist> vo) {
        logger.info("起草暂存协助执行流程表单参数：" + vo.toString());
        // 暂存草稿
        int i = tIssueAssistService.tempTIssueAssist(vo);
        // 返回执行结果
        if (2 == i) {
            // 成功，新增或修改（业务表及草稿箱）各一条数据
            logger.info(DisputeConstClass.TISSUE_ASSIST_TEMP_SUCCESS, i);
            return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.TISSUE_ASSIST_TEMP_SUCCESS, i);
        } else {
            // 失败
            logger.error(getReturnMsg("tempTIssueAssist", i), i);
            return new ProcessResult(ProcessResult.ERROR, getReturnMsg("tempTIssueAssist", i), i);
        }
    }


    @Override
    public ProcessResult tempSelective(@RequestBody AddFlowLogVO addFlowLogVO) {
        return tIssueAssistService.tempSelective(addFlowLogVO);
    }

    @Override
    public ProcessResult findOne(String id, String actId, String activityInstId) {
        logger.info("协助执行流程查询详情参数：" + id + "---" + actId + "---" + activityInstId);
        return tIssueAssistService.findOne(id, actId, activityInstId);
    }

    @Override
    public ProcessResult findBaseInfo(String id) {
        logger.info("协助执行流程查询基础信息详情参数：" + id);
        return tIssueAssistService.findBaseInfo(id);
    }
    @Override
    public ProcessResult findBaseInfoAndBindFiles(String id) {
        logger.info("协助执行流程查询基础信息详情并绑定文件信息参数：" + id);
        return tIssueAssistService.findBaseInfoAndBindFiles(id);
    }

    @Override
    public ProcessResult selectAll(@RequestBody SelectAllListParamVO param) {
        logger.info("协助执行流程列表查询参数：" + param.toString());
        PageInfo<TIssueAssist> pageInfo = null;
        try {
            // 列表分页查询
            pageInfo = tIssueAssistService.selectAll(param);
        } catch (Exception e) {
            logger.error("协助执行流程列表查询失败", e);
        }
        if (null != pageInfo) {
            logger.info(DisputeConstClass.TISSUE_ASSIST_SELECT_ALL_SUCCESS, pageInfo);
            return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.TISSUE_ASSIST_SELECT_ALL_SUCCESS, pageInfo);
        }
        logger.error(DisputeConstClass.TISSUE_ASSIST_SELECT_ALL_FAILED);
        return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.TISSUE_ASSIST_SELECT_ALL_FAILED);
    }

    @Override
    public ProcessResult findTIssueAssistList(@RequestBody FindTIssueAssistListListParamVO param) {
        return tIssueAssistService.findTIssueAssistList(param);
    }

    /**
     * 协助执行流程关系列表查询
     * 查询可关联的协助执行单
     * @param param
     * @return
     */
    @Override
    public ProcessResult selectRelationshipList(@RequestBody SelectRelationshipListParamVO param) {
        logger.info("协助执行流程审批中关系列表查询参数：" + param.toString());
        PageInfo<TIssueAssist> pageInfo = null;
        try {
            // 列表分页查询
            pageInfo = tIssueAssistService.selectRelationshipList(param);
        } catch (Exception e) {
            logger.error("协助执行流程审批中关系列表查询失败", e);
        }
        if (null != pageInfo) {
            logger.info(DisputeConstClass.TISSUE_ASSIST_SELECT_RELATIONSHIP_LIST_SUCCESS, pageInfo);
            return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.TISSUE_ASSIST_SELECT_RELATIONSHIP_LIST_SUCCESS, pageInfo);
        }
        logger.error(DisputeConstClass.TISSUE_ASSIST_SELECT_RELATIONSHIP_LIST_FAILED);
        return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.TISSUE_ASSIST_SELECT_RELATIONSHIP_LIST_FAILED);
    }

    @Override
    public ProcessResult selectRelationshipListInCreate(@RequestBody SelectRelationshipListInCreateParamVO param) {
        logger.info("协助执行流程起草时关系列表查询参数：" + param.toString());
        PageInfo<TIssueAssist> pageInfo = null;
        try {
            // 列表分页查询
            pageInfo = tIssueAssistService.selectRelationshipListInCreate(param);
        } catch (Exception e) {
            logger.error("协助执行流程起草时关系列表查询失败", e);
        }
        if (null != pageInfo) {
            logger.info(DisputeConstClass.TISSUE_ASSIST_SELECT_RELATIONSHIP_LIST_IN_CREATE_SUCCESS, pageInfo);
            return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.TISSUE_ASSIST_SELECT_RELATIONSHIP_LIST_IN_CREATE_SUCCESS, pageInfo);
        }
        logger.error(DisputeConstClass.TISSUE_ASSIST_SELECT_RELATIONSHIP_LIST_IN_CREATE_FAILED);
        return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.TISSUE_ASSIST_SELECT_RELATIONSHIP_LIST_IN_CREATE_FAILED);
    }

    @Override
    public ProcessResult deleteTIssueAssist(@RequestBody List<String> ids) {
        logger.info("协助执行流程删除参数：" + ids.toString());
        int i = 0;
        try {
            // 删除草稿箱
            DraftsVO draftsVO = new DraftsVO(DisputeConstClass.T_ISSUE_ASSIST, ids);
            i += draftsVOMapper.deleteBatchDrafts(draftsVO);
            // 删除业务表数据
            i += tIssueAssistService.deleteTIssueAssist(ids);
        } catch (Exception e) {
            logger.error("协助执行流程删除数据失败", e);
        }
        if (i > 0) {
            logger.info(DisputeConstClass.TISSUE_ASSIST_DELETE_T_ISSUE_ASSIST_SUCCESS, i);
            return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.TISSUE_ASSIST_DELETE_T_ISSUE_ASSIST_SUCCESS, i);
        } else {
            logger.error(getReturnMsg("deleteTIssueAssist", i));
            return new ProcessResult(ProcessResult.ERROR, getReturnMsg("deleteTIssueAssist", i));
        }
    }

    @Override
    public ProcessResult updateRelation(@RequestBody Map<String, String> param) {
        logger.info("协助执行修改关联关系参数：" + param.toString());
        return tIssueAssistService.updateRelation(param);
    }

    private String getReturnMsg(String methodName, int returnCode) {
        String errorResons = "。错误原因：";
        switch (methodName) {
            case "tempTIssueAssist":
                if (returnCode == -1) {
                    return DisputeConstClass.TISSUE_ASSIST_TEMP_FAILED + errorResons + DisputeConstClass.PARAM_EMPTY;

                }
                break;
            case "deleteTIssueAssist":
                if (returnCode == -1) {
                    return DisputeConstClass.TISSUE_ASSIST_DELETE_T_ISSUE_ASSIST_FAILED + errorResons + DisputeConstClass.PARAM_EMPTY;
                }
                break;
            default:
                return "";
        }
        return "";
    }
}
