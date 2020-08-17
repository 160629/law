package com.chinatower.product.legalms.modules.dispute.api.flow;

import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.vo.unview.AutoView;

/**
 * @Date: 2019/12/20 10:17
 * @Description:
 */
public interface TFlowApi {

    @PostMapping("/TFlowApi/autoUnView")
    public ProcessResult autoUnView(String transferId, String transferUser,String receptId, String receptUser,Integer caseNum, String cause);
    
    @PostMapping("/TFlowApi/autoUnViewTest")
    public Integer autoUnViewTest(AutoView obj);
}
