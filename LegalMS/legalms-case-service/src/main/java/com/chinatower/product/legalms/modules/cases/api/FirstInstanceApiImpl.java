package com.chinatower.product.legalms.modules.cases.api;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.CaseInfo;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.modules.cases.entity.FileMainVO;
import com.chinatower.product.legalms.modules.cases.entity.FirstInstanceVO;
import com.chinatower.product.legalms.modules.cases.service.FileShareVOService;
import com.chinatower.product.legalms.modules.cases.service.FirstInstanceService;
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
public class FirstInstanceApiImpl implements FirstInstanceApi {

    private static final Logger logger = LoggerFactory.getLogger(FirstInstanceApiImpl.class);

    @Autowired
    private FirstInstanceService firstInstanceService;

    @Autowired
    private FileShareVOService fileShareVOService;

/*    @Override
    @ApiOperation("一审创建接口")
    @ApiImplicitParam(name="filedId",value = "文件ID",required = true)
    public ProcessResult addFirstInstance(@RequestBody FirstInstanceVO firstInstanceVO,
                                          @RequestParam(value="filedId",required = false) String filedId) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(firstInstanceVO)));
        try {
            long timestamp = System.currentTimeMillis();
            String nonce = String.valueOf(new Random().nextInt(10));
            String id = CaseInfo.ID_HEAD_FIRSTINSTANCE+timestamp+nonce;
            firstInstanceVO.setYishenUuid(id);
            firstInstanceService.addFirstInstance(firstInstanceVO);
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
    @ApiOperation("一审详情接口")
    public ProcessResult selectFirstInstance(@RequestBody FirstInstanceVO firstInstance) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(firstInstance)));
        try {
            FirstInstanceVO firstInstanceVO = firstInstanceService.selectFirstInstance(firstInstance.getCaseId());
            List<FileMainVO> fileMainVOS = fileShareVOService.selectFileById(firstInstance.getCaseId(),firstInstance.getShareType());
            Map<String,Object> map = new HashMap<>();
            map.put("firstInstanceVO",firstInstanceVO);
            map.put("fileMainVOS",fileMainVOS);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.SELECT_SUCCESS+map));
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.SELECT_FIRST_INSTANCE_SUCCESS,map);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SELECT_FIRST_INSTANCE_FAILED);
        }
    }

/*    @Override
    @ApiOperation("一审修改接口")
    public ProcessResult updateFirstInstance(@RequestBody FirstInstanceVO firstInstanceVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(firstInstanceVO)));
        try {
             firstInstanceService.updateFirstInstance(firstInstanceVO);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
            return new ProcessResult(ProcessResult.SUCCESS,"");
        }catch (Exception e){
            log.info(CaseInfo.OPERATION_EXCEPTION+e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }*/

    @Override
    @ApiOperation("一审创建或修改接口")
    public ProcessResult addOrUpdateFirstInstance(@RequestBody FirstInstanceVO firstInstanceVO) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(firstInstanceVO)));
        try {
            int count = firstInstanceService.selectAllFirstInstanceByCount(firstInstanceVO.getCaseId());
            if (count == 0) {
                firstInstanceService.addFirstInstance(firstInstanceVO);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.INSERT_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.ADD_FIRST_INSTANCE_SUCCESS);
            } else {
                firstInstanceService.updateFirstInstance(firstInstanceVO);
                log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS));
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.UPDATE_FIRST_INSTANCE_SUCCESS);
            }
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.ADD_OR_UPDATE_FIRST_INSTANCE_FAILED);
        }
    }

    @Override
    public ProcessResult deleteFirstInstance(@RequestParam("caseId") String caseId) {
        log.info(InterfaceLogUtil.reqTransLog(CaseInfo.REQUEST_PARAM+ JSON.toJSONString(caseId)));
        try {
            int a = firstInstanceService.deleteFirstInstance(caseId);
            log.info(InterfaceLogUtil.rspTransLog(CaseInfo.CASE_RESULT + CaseInfo.UPDATE_SUCCESS) + ":" + a);
            return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.DELETE_FIRST_INSTANCE_SUCCESS);
        }catch (Exception e){
            logger.error(CaseInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.DELETE_FIRST_INSTANCE_FAILED);
        }
    }
}
