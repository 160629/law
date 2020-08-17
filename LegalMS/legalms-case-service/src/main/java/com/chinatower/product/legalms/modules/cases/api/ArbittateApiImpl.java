package com.chinatower.product.legalms.modules.cases.api;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.modules.cases.entity.ArbittateVO;
import com.chinatower.product.legalms.modules.cases.entity.FileMainVO;
import com.chinatower.product.legalms.modules.cases.service.ArbittateService;
import com.chinatower.product.legalms.modules.cases.service.FileShareVOService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = {"案件案宗接口"})
public class ArbittateApiImpl implements ArbittateApi {

    private static final Logger logger = LoggerFactory.getLogger(ArbittateApiImpl.class);
    @Autowired
    private ArbittateService arbittateService;

    @Autowired
    private FileShareVOService fileShareVOService;

/*    @Override
    @ApiOperation("仲裁创建接口")
    @ApiImplicitParam(name="filedId",value = "文件ID",required = true)
    public ProcessResult addArbittate(@RequestBody ArbittateVO arbittateVO,
                                      @RequestParam(value="filedId",required = false) String filedId) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(arbittateVO)));
        try {
            long timestamp = System.currentTimeMillis();
            String nonce = String.valueOf(new Random().nextInt(10));
            String id = CaseInfo.ID_HEAD_ARBITTATE+timestamp+nonce;
            arbittateVO.setArbitrateId(id);
            arbittateService.addArbittate(arbittateVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
            if(StringUtils.isNotBlank(filedId)) {
                String shareid = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong();
                FileShareVO fileShareVO = new FileShareVO();
                fileShareVO.setFileShareBusinessKey(id);
                fileShareVO.setFileId(filedId);
                fileShareVO.setFileShareId(shareid);
                fileShareVOService.insertFileShare(fileShareVO);
            }
            return new ProcessResult(ProcessResult.SUCCESS,"");
        }catch (Exception e){
            log.info(CaseInfo.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }*/

    @Override
    @ApiOperation("仲裁详情接口")
    public ProcessResult selectArbittate(@RequestBody ArbittateVO arbittate) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(arbittate)));
        try {
            ArbittateVO arbittateVO = arbittateService.selectArbittate(arbittate.getCaseId());
            List<FileMainVO> fileMainVOS = fileShareVOService.selectFileById(arbittate.getCaseId(),arbittate.getShareType());
            Map<String,Object> map = new HashMap<>();
            map.put("arbittateVO",arbittateVO);
            map.put("fileMainVOS",fileMainVOS);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS+map));
            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SELECT_ARBITTATE_SUCCESS,map);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SELECT_ARBITTATE_FAILED);
        }
    }

/*    @Override
    @ApiOperation("仲裁修改接口")
    public ProcessResult updateArbittate(@RequestBody ArbittateVO arbittateVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(arbittateVO)));
        try {
             arbittateService.updateArbittate(arbittateVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS));
            return new ProcessResult(ProcessResult.SUCCESS,"");
        }catch (Exception e){
            log.info(CaseInfo.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }*/

    @Override
    @ApiOperation("仲裁创建或修改接口")
    public ProcessResult addOrUpdateArbittate(@RequestBody ArbittateVO arbittateVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(arbittateVO)));
        try {
            int count = arbittateService.selectAllArbittateVOByCount(arbittateVO.getCaseId());
            if (count == 0) {
                arbittateService.addArbittate(arbittateVO);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.ADD_ARBITTATE_SUCCESS);
            } else {
                arbittateService.updateArbittate(arbittateVO);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.UPDATE_ARBITTATE_SUCCESS);
            }
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.ADD_OR_UPDATE_ARBITTATE_FAILED);
        }
    }

    @Override
    public ProcessResult deleteArbittate(@RequestParam("caseId") String caseId) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseId)));
        try {
            int a = arbittateService.deleteArbittate(caseId);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS + ":" + a));
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.DELETE_ARBITTATE_SUCCESS);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.DELETE_ARBITTATE_FAILED);
        }
    }
}
