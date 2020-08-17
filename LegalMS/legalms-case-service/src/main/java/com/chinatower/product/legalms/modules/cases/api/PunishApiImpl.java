package com.chinatower.product.legalms.modules.cases.api;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.modules.cases.entity.FileMainVO;
import com.chinatower.product.legalms.modules.cases.entity.PunishVO;
import com.chinatower.product.legalms.modules.cases.service.FileShareVOService;
import com.chinatower.product.legalms.modules.cases.service.PunishService;
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
public class PunishApiImpl implements PunishApi {

    private static final Logger logger = LoggerFactory.getLogger(PunishApiImpl.class);

    @Autowired
    private PunishService punishService;

    @Autowired
    private FileShareVOService fileShareVOService;

/*    @Override
    @ApiOperation("行政复议创建接口")
    @ApiImplicitParam(name="filedId",value = "文件ID",required = true)
    public ProcessResult addPunish(@RequestBody PunishVO punishVO,
                                   @RequestParam(value="filedId",required = false) String filedId) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(punishVO)));
        try {
            long timestamp = System.currentTimeMillis();
            String nonce = String.valueOf(new Random().nextInt(10));
            String id = CaseInfo.ID_HEAD_PUNISH+timestamp+nonce;
            punishVO.setPunishId(id);
            punishService.addPunish(punishVO);
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
    @ApiOperation("行政复议详情接口")
    public ProcessResult selectPunish(@RequestBody PunishVO punish) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(punish)));
        try {
            PunishVO punishVO = punishService.selectPunish(punish.getCaseId());
            List<FileMainVO> fileMainVOS = fileShareVOService.selectFileById(punish.getCaseId(),punish.getShareType());
            Map<String,Object> map = new HashMap<>();
            map.put("punishVO",punishVO);
            map.put("fileMainVOS",fileMainVOS);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS+map));
            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SELECT_PUNISH_SUCCESS,map);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SELECT_PUNISH_FAILED);
        }
    }

/*    @Override
    @ApiOperation("行政复议修改接口")
    public ProcessResult updatePunish(@RequestBody PunishVO punishVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(punishVO)));
        try {
            punishService.updatePunish(punishVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
            return new ProcessResult(ProcessResult.SUCCESS,"");
        }catch (Exception e){
            log.info(CaseInfo.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }*/

    @Override
    @ApiOperation("行政复议创建或修改接口")
    public ProcessResult addOrUpdatePunish(@RequestBody PunishVO punishVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(punishVO)));
        try {
            int count = punishService.selectAllPunishByCount(punishVO.getCaseId());
            if (count == 0) {
                punishService.addPunish(punishVO);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.ADD_PUNISH_SUCCESS);
            } else {
                punishService.updatePunish(punishVO);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.UPDATE_PUNISH_SUCCESS);
            }

        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.ADD_OR_UPDATE_PUNISH_FAILED);
        }
    }

    @Override
    public ProcessResult deletePunish(@RequestParam("caseId") String caseId) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseId)));
        try {
            int a = punishService.deletePunish(caseId);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS) + ":" + a);
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.DELETE_PUNISH_SUCCESS);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.DELETE_PUNISH_FAILED);
        }
    }
}
