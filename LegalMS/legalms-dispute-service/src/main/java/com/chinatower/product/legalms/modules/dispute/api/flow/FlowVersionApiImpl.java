package com.chinatower.product.legalms.modules.dispute.api.flow;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.modules.flow.entity.version.FlowVersion;
import com.chinatower.product.legalms.modules.flow.service.version.FlowVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class FlowVersionApiImpl implements FlowVersionApi{

    @Autowired
    FlowVersionService flowVersionService;

    private static final Logger logger = LoggerFactory.getLogger(FlowVersionApiImpl.class);

    @Override
    public ProcessResult insertRecord(@RequestBody FlowVersion flowVersion) {
        logger.info("flowVersion-insertRecord : " + flowVersion);
        int i = 0;
        if (flowVersion == null) {
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.FAILURE.PARAMETER_ERROR);
        }
        try {
            i = flowVersionService.insertRecord(flowVersion);
        } catch (Exception e) {
            logger.error(DisputeConstClass.SUCCESS_MESS, e);
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.FAILURE.PARAMETER_ERROR);
        }
        return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.SUCCESS_MESS, i);
    }

    @Override
    public ProcessResult updateRecord(@RequestBody FlowVersion flowVersion) {
        logger.info("flowVersion-updateRecord : " + flowVersion);
        int i = 0;
        if (flowVersion == null) {
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.FAILURE.PARAMETER_ERROR);
        }
        try {
            i = flowVersionService.updateRecord(flowVersion);
        } catch (Exception e) {
            logger.error(DisputeConstClass.SUCCESS_MESS, e);
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.FAILURE.PARAMETER_ERROR);
        }
        return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.SUCCESS_MESS, i);
    }

    @Override
    public ProcessResult selectAllRecords() {
        logger.info("flowVersion-selectAllRecords");
        List<FlowVersion> flowVersions = null;
        try {
            flowVersions = flowVersionService.selectAllRecords();
        } catch (Exception e) {
            logger.error(DisputeConstClass.SUCCESS_MESS, e);
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.FAILURE.PARAMETER_ERROR);
        }
        return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.SUCCESS_MESS, flowVersions);
    }

    @Override
    public ProcessResult selectRecordById(@RequestBody FlowVersion flowVersion) {
        logger.info("flowVersion-selectRecordById : " + flowVersion);
        FlowVersion flowVersion1 = null;
        if (flowVersion == null) {
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.FAILURE.PARAMETER_ERROR);
        }
        try {
            flowVersion1 = flowVersionService.selectRecordById(flowVersion);
        } catch (Exception e) {
            logger.error(DisputeConstClass.SUCCESS_MESS, e);
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.FAILURE.PARAMETER_ERROR);
        }
        return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.SUCCESS_MESS, flowVersion1);
    }

    @Override
    public ProcessResult addFlowVersionToPowerAndConfig(@RequestBody FlowVersion flowVersion) {
        logger.info("flowVersion-insertRecord : " + flowVersion);
        int i = 0;
        if (flowVersion == null) {
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.FAILURE.PARAMETER_ERROR);
        }
        try {
            i = flowVersionService.insertRecord(flowVersion);
            i += flowVersionService.addFlowVersionToPowerAndConfig(flowVersion);
        } catch (Exception e) {
            logger.error(DisputeConstClass.SUCCESS_MESS, e);
            return new ProcessResult(ProcessResult.ERROR, DisputeConstClass.FAILURE.PARAMETER_ERROR);
        }
        return new ProcessResult(ProcessResult.SUCCESS, DisputeConstClass.SUCCESS_MESS, i);
    }
}
