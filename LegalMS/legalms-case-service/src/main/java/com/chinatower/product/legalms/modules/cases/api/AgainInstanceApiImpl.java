package com.chinatower.product.legalms.modules.cases.api;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.modules.cases.entity.AgainInstanceVO;
import com.chinatower.product.legalms.modules.cases.entity.FileMainVO;
import com.chinatower.product.legalms.modules.cases.service.AgainInstanceService;
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
public class AgainInstanceApiImpl implements AgainInstanceApi {

    private static final Logger logger = LoggerFactory.getLogger(AgainInstanceApiImpl.class);
    @Autowired
    private AgainInstanceService againInstanceService;

    @Autowired
    private FileShareVOService fileShareVOService;

/*    @Override
    @ApiOperation("再审创建接口")
    @ApiImplicitParam(name="filedId",value = "文件ID",required = true)
    public ProcessResult addAgainInstance(@RequestBody AgainInstanceVO againInstanceVO,
                                          @RequestParam(value="filedId",required = false) String filedId) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(againInstanceVO)));
        try {
            long timestamp = System.currentTimeMillis();
            String nonce = String.valueOf(new Random().nextInt(10));
            String id = CaseInfo.ID_HEAD_AGAININSTANCE+timestamp+nonce;
            againInstanceVO.setZaishenId(id);
            againInstanceService.addAgainInstance(againInstanceVO);
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
    @ApiOperation("再审详情接口")
    public ProcessResult selectAgainInstance(@RequestBody AgainInstanceVO againInstance) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(againInstance)));
        try {
            AgainInstanceVO againInstanceVO = againInstanceService.selectAgainInstance(againInstance.getCaseId());
            List<FileMainVO> fileMainVOS = fileShareVOService.selectFileById(againInstance.getCaseId(),againInstance.getShareType());
            Map<String,Object> map = new HashMap<>();
            map.put("againInstanceVO",againInstanceVO);
            map.put("fileMainVOS",fileMainVOS);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS+map));
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.SELECT_AGAIN_SUCCESS,map);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SELECT_AGAIN_FAILED);
        }
    }

/*
    @Override
    @ApiOperation("再审修改接口")
    public ProcessResult updateAgainInstance(@RequestBody AgainInstanceVO againInstanceVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(againInstanceVO)));
        try {
            againInstanceService.updateAgainInstance(againInstanceVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
            return new ProcessResult(ProcessResult.SUCCESS,"");
        }catch (Exception e){
            log.info(CaseInfo.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }
*/

    @Override
    @ApiOperation("再审创建或修改接口")
    public ProcessResult addOrUpdateAgainInstance(@RequestBody AgainInstanceVO againInstanceVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(againInstanceVO)));
        try {
            int count = againInstanceService.selectAllAgainInstanceByCount(againInstanceVO.getCaseId());
            if (count == 0) {
                againInstanceService.addAgainInstance(againInstanceVO);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.ADD_AGAIN_SUCCESS);
            } else {
                againInstanceService.updateAgainInstance(againInstanceVO);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.UPDATE_AGAIN_SUCCESS);
            }
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.ADD_OR_UPDATE_AGAIN_FAILED);
        }
    }

    @Override
    public ProcessResult deleteAgainInstance(@RequestParam("caseId") String caseId) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseId)));
        try {
            int a = againInstanceService.deleteAgainInstance(caseId);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS) + ":" + a);
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.DELETE_AGAIN_SUCCESS);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.DELETE_AGAIN_FAILED);
        }
    }
}
