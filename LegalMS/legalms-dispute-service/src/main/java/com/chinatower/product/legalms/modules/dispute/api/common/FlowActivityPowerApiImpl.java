package com.chinatower.product.legalms.modules.dispute.api.common;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.modules.flow.entity.common.FlowActivityPowerVO;
import com.chinatower.product.legalms.modules.flow.vo.common.PageLists;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chinatower.product.legalms.modules.flow.service.common.FlowActivityPowerService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags={"流程权限配置"})
public class FlowActivityPowerApiImpl implements FlowActivityPowerApi {
    private static final Logger logger = LoggerFactory.getLogger(DisputeConstClass.LOGGER_NAME);

    @Autowired
    private FlowActivityPowerService flowActivityPowerService;

    @Override
    @ApiOperation(value = "流程权限配置新增")
    public ProcessResult addFlowActivityPower(@RequestBody FlowActivityPowerVO flowActivityPowerVO) {
        int i;
        try {
            i = flowActivityPowerService.addFlowActivityPower(flowActivityPowerVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",i);
        }catch (Exception e){
            logger.info(DisputeConstClass.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    @Override
    @ApiOperation(value = "流程权限配置修改")
    public ProcessResult updateFlowActivityPower(@RequestBody Map<String, FlowActivityPowerVO> flowActivityPowerVOs) {
        int i;
        try {
            i = flowActivityPowerService.updateFlowActivityPower(flowActivityPowerVOs.get("oldFlowActivityPowerVO"), flowActivityPowerVOs.get("newFlowActivityPowerVO"));
            return new ProcessResult(ProcessResult.SUCCESS,"",i);
        }catch (Exception e){
            logger.info(DisputeConstClass.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    @Override
    @ApiOperation(value = "流程权限配置删除")
    public ProcessResult deleteFlowActivityPower(@RequestBody FlowActivityPowerVO flowActivityPowerVO) {
        int i;
        try {
            i = flowActivityPowerService.deleteFlowActivityPower(flowActivityPowerVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",i);
        }catch (Exception e){
            logger.info(DisputeConstClass.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    @Override
    public ProcessResult selFlowActivityPower(@RequestBody PageLists pageLists) {
        try {
            PageHelper.startPage(pageLists.getPageNum(), pageLists.getPageSize());
            List<FlowActivityPowerVO> caseMainList= flowActivityPowerService.selFlowActivityPower(pageLists);
            PageInfo<FlowActivityPowerVO> pageInfo = new PageInfo<>(caseMainList);
            return new ProcessResult(ProcessResult.SUCCESS,"",pageInfo);
        }catch (Exception e){
            logger.info(DisputeConstClass.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    @Override
    public ProcessResult selActivityPower(@RequestBody FlowActivityPowerVO flowActivityPowerVO) {
        List<FlowActivityPowerVO> flowActivityPowerVOS;
        try {
            flowActivityPowerVOS = flowActivityPowerService.selActivityPower(flowActivityPowerVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",flowActivityPowerVOS);
        }catch (Exception e){
            logger.info(DisputeConstClass.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    @Override
    public ProcessResult selFlowActivityPowerFlowId() {
        List<FlowActivityPowerVO> flowActivityPowerVOS;
        try {
            flowActivityPowerVOS = flowActivityPowerService.selFlowActivityPowerFlowId();
            return new ProcessResult(ProcessResult.SUCCESS,"",flowActivityPowerVOS);
        }catch (Exception e){
            logger.info(DisputeConstClass.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }
}
