package com.chinatower.product.legalms.modules.dispute.api.common;

import com.chinatower.product.legalms.modules.flow.vo.flow.BusinessLogConfigVO;
import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.common.BusinessLogVO;

import java.util.List;

public interface BusinessLogApi {
    @PostMapping("/businessLog/selectBusinessLog")
    ProcessResult selectBusinessLog(BusinessLogVO businessLogVO);

    @PostMapping("/businessLog/selectAll")
    ProcessResult selectAll();

    @PostMapping("/businessLog/addConfig")
    ProcessResult addConfig(BusinessLogConfigVO businessLogConfigVO);

    @PostMapping("/businessLog/delConfig")
    ProcessResult delConfig(List<BusinessLogConfigVO> businessLogConfigVOS);

    @PostMapping("/businessLog/updateConfig")
    ProcessResult updateConfig(List<BusinessLogConfigVO> businessLogConfigVOS);
}
