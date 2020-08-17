package com.chinatower.product.legalms.modules.dispute.api.common;

import java.util.List;

import com.chinatower.product.legalms.modules.flow.vo.flow.BusinessLogConfigVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.common.BusinessLogVO;
import com.chinatower.product.legalms.modules.flow.service.common.BusinessLogService;

import io.swagger.annotations.Api;


@RestController
@Api(tags = {"纠纷处理意见"})
public class BusinessLogApiImpl implements BusinessLogApi {
    private static final Logger logger = LoggerFactory.getLogger("TransLog");
    @Autowired
    BusinessLogService businessLogService;

    @Override
    public ProcessResult selectBusinessLog(@RequestBody BusinessLogVO businessLogVO) {
        try {
            List<BusinessLogVO> list = businessLogService.selectBusinessLog(businessLogVO);
            return new ProcessResult(ProcessResult.SUCCESS, "", list);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }

    @Override
    public ProcessResult selectAll() {
        return businessLogService.selectAll();
    }

    @Override
    public ProcessResult addConfig(@RequestBody BusinessLogConfigVO businessLogConfigVO) {
        return businessLogService.addConfig(businessLogConfigVO);
    }

    @Override
    public ProcessResult delConfig(@RequestBody List<BusinessLogConfigVO> businessLogConfigVOS) {
        return businessLogService.delConfig(businessLogConfigVOS);
    }

    @Override
    public ProcessResult updateConfig(@RequestBody List<BusinessLogConfigVO> businessLogConfigVOS) {
        return businessLogService.updateConfig(businessLogConfigVOS);
    }

}
