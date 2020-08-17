package com.chinatower.product.legalms.modules.dispute.api.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.modules.flow.entity.common.SysBodyconfigVO;
import com.chinatower.product.legalms.modules.flow.service.common.SysBodyconfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags={"我方争议主体和业务关联关系接口"})
public class SysBodyconfigApiImpl implements SysBodyconfigApi {

    private static final Logger logger = LoggerFactory.getLogger(DisputeConstClass.LOGGER_NAME);

    @Autowired
    private SysBodyconfigService sysBodyconfigService;

    @Override
    @ApiOperation(value = "查询接口")
    public ProcessResult selectSysBodyconfig(@RequestBody SysBodyconfigVO sysBodyconfigVO) {
        List<SysBodyconfigVO> sysBodyconfigVOS = sysBodyconfigService.selectAllSysBodyconfig(sysBodyconfigVO);
        return new ProcessResult(ProcessResult.SUCCESS,"",sysBodyconfigVOS );
    }

    @Override
    @ApiOperation(value = "添加接口")
    public ProcessResult addSysBodyconfig(@RequestBody List<SysBodyconfigVO> list) {
        int a;
        try {
            a = sysBodyconfigService.addSysBodyconfig(list);
            if(a>0) {
                return new ProcessResult(ProcessResult.SUCCESS, "", a);
            }else {
                return new ProcessResult(ProcessResult.ERROR, "", a);
            }
        } catch (Exception e) {
            logger.info(DisputeConstClass.OPERATION_EXCEPTION + e.toString());
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    @Override
    @ApiOperation(value = "修改接口")
    public ProcessResult updateSysBodyconfig(@RequestBody SysBodyconfigVO sysBodyconfigVO) {
        int a;
        try {
            a = sysBodyconfigService.updateSysBodyconfig(sysBodyconfigVO);
            if(a>0) {
                return new ProcessResult(ProcessResult.SUCCESS, "", a);
            }else{
                return new ProcessResult(ProcessResult.ERROR, "", a);
            }
        } catch (Exception e) {
            logger.info(DisputeConstClass.OPERATION_EXCEPTION + e.toString());
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    @Override
    @ApiOperation(value = "删除接口")
    @ApiImplicitParam(value = "approveItemId",name = "ID")
    public ProcessResult deleteSysBodyconfig(@RequestParam String approveItemId) {
        int a;
        try {
            a = sysBodyconfigService.deleteSysBodyconfig(approveItemId);
            if(a>0) {
                return new ProcessResult(ProcessResult.SUCCESS, "", a);
            }else{
                return new ProcessResult(ProcessResult.ERROR, "", a);
            }
        } catch (Exception e) {
            logger.info(DisputeConstClass.OPERATION_EXCEPTION + e.toString());
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }
}
