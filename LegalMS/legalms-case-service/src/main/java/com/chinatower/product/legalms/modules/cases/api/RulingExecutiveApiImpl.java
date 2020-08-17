package com.chinatower.product.legalms.modules.cases.api;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.modules.cases.entity.RulingExecutiveVO;
import com.chinatower.product.legalms.modules.cases.service.RulingExecutiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = {"案件案宗接口"})
public class RulingExecutiveApiImpl implements RulingExecutiveApi {

    private static final Logger logger = LoggerFactory.getLogger(RulingExecutiveApiImpl.class);
    @Autowired
    private RulingExecutiveService rulingExecutiveService;

/*    @Override
    @ApiOperation("裁决执行创建接口")
    public ProcessResult addRulingExecutive(@RequestBody RulingExecutiveVO rulingExecutiveVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(rulingExecutiveVO)));
        try {
            long timestamp = System.currentTimeMillis();
            String nonce = String.valueOf(new Random().nextInt(10));
            String id = CaseInfo.ID_RULING_EXECUTIVE+timestamp+nonce;
            rulingExecutiveVO.setRulingId(id);
            rulingExecutiveService.addRulingExecutive(rulingExecutiveVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
            return new ProcessResult(ProcessResult.SUCCESS,"");
        }catch (Exception e){
            log.info(CaseInfo.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }*/

    @Override
    @ApiOperation("裁决执行详情接口")
    @ApiImplicitParam(name="caseId",value = "案宗ID",required = true)
    public ProcessResult selectRulingExecutive(@RequestBody RulingExecutiveVO rulingExecutive) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(rulingExecutive)));
        try {
            RulingExecutiveVO rulingExecutiveVO = rulingExecutiveService.selectRulingExecutive(rulingExecutive.getCaseId());
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS+rulingExecutiveVO));
            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SELECT_RULINGEXECUTIVE_SUCCESS,rulingExecutiveVO);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SELECT_RULINGEXECUTIVE_FAILED);
        }
    }

/*    @Override
    @ApiOperation("裁决执行修改接口")
    public ProcessResult updateRulingExecutive(@RequestBody RulingExecutiveVO rulingExecutiveVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM + JSON.toJSONString(rulingExecutiveVO)));
        try {
            rulingExecutiveService.updateRulingExecutive(rulingExecutiveVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
            return new ProcessResult(ProcessResult.SUCCESS, "");
        } catch (Exception e) {
            log.info(CaseInfo.OPERATION_EXCEPTION + e);
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }*/

    @Override
    @ApiOperation("裁决执行创建或修改接口")
    public ProcessResult addOrUpdateRulingExecutive(@RequestBody RulingExecutiveVO rulingExecutiveVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(rulingExecutiveVO)));
        try {
            int count = rulingExecutiveService.selectAllRulingExecutiveByCount(rulingExecutiveVO.getCaseId());
            if (count == 0) {
                rulingExecutiveService.addRulingExecutive(rulingExecutiveVO);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.ADD_RULINGEXECUTIVE_SUCCESS);
            } else {
                rulingExecutiveService.updateRulingExecutive(rulingExecutiveVO);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.UPDATE_RULINGEXECUTIVE_SUCCESS);
            }
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.ADD_OR_UPDATE_RULINGEXECUTIVE_FAILED);
        }
    }
}
