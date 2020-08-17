package com.chinatower.product.legalms.modules.cases.api;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.modules.cases.entity.FileMainVO;
import com.chinatower.product.legalms.modules.cases.entity.SecondInstanceVO;
import com.chinatower.product.legalms.modules.cases.service.FileShareVOService;
import com.chinatower.product.legalms.modules.cases.service.SecondInstanceService;
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
public class SecondInstanceApiImpl implements SecondInstanceApi {

    private static final Logger logger = LoggerFactory.getLogger(SecondInstanceApiImpl.class);
    @Autowired
    private SecondInstanceService secondInstanceService;

    @Autowired
    private FileShareVOService fileShareVOService;

/*    @Override
    @ApiOperation("二审创建接口")
    @ApiImplicitParam(name="filedId",value = "文件ID",required = true)
    public ProcessResult addSecondInstance(@RequestBody SecondInstanceVO secondInstanceVO,
                                           @RequestParam(value="filedId",required = false) String filedId) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(secondInstanceVO)));
        try {
            long timestamp = System.currentTimeMillis();
            String nonce = String.valueOf(new Random().nextInt(10));
            String id = CaseInfo.ID_HEAD_SECONDINSTANCE+timestamp+nonce;
            secondInstanceVO.setErshenId(id);
            secondInstanceService.addSecondInstance(secondInstanceVO);
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
    @ApiOperation("二审详情接口")
    public ProcessResult selectSecondInstance(@RequestBody SecondInstanceVO secondInstance) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(secondInstance)));
        try {
            SecondInstanceVO secondInstanceVO = secondInstanceService.selectSecondInstance(secondInstance.getCaseId());
            List<FileMainVO> fileMainVOS = fileShareVOService.selectFileById(secondInstance.getCaseId(),secondInstance.getShareType());
            Map<String,Object> map = new HashMap<>();
            map.put("secondInstanceVO",secondInstanceVO);
            map.put("fileMainVOS",fileMainVOS);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS+map));
            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SELECT_SECOND_INSTANCE_SUCCESS,map);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SELECT_SECOND_INSTANCE_FAILED);
        }
    }

/*    @Override
    @ApiOperation("二审修改接口")
    public ProcessResult updateSecondInstance(@RequestBody SecondInstanceVO secondInstanceVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(secondInstanceVO)));
        try {
            secondInstanceService.updateSecondInstance(secondInstanceVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
            return new ProcessResult(ProcessResult.SUCCESS,"");
        }catch (Exception e){
            log.info(CaseInfo.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }*/

    @Override
    @ApiOperation("二审创建或修改接口")
    public ProcessResult addOrUpdateSecondInstance(@RequestBody SecondInstanceVO secondInstanceVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(secondInstanceVO)));
        try {
            int count = secondInstanceService.selectAllSecondInstanceByCount(secondInstanceVO.getCaseId());
            if (count == 0) {
                secondInstanceService.addSecondInstance(secondInstanceVO);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS,"",ConstClass.SUCCESS.ADD_SECOND_INSTANCE_SUCCESS);
            } else {
                secondInstanceService.updateSecondInstance(secondInstanceVO);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS,"",ConstClass.SUCCESS.UPDATE_SECOND_INSTANCE_SUCCESS);
            }

        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.ADD_OR_UPDATE_SECOND_INSTANCE_FAILED);
        }
    }

    @Override
    public ProcessResult deleteSecondInstance(@RequestParam("caseId") String caseId) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseId)));
        try {
            int a = secondInstanceService.deleteSecondInstance(caseId);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS) + ":" + a);
            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.DELETE_SECOND_INSTANCE_SUCCESS);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.DELETE_SECOND_INSTANCE_FAILED);
        }
    }
}
