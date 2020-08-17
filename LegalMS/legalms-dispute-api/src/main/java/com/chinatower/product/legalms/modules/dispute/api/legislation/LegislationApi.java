package com.chinatower.product.legalms.modules.dispute.api.legislation;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.dispute.entity.LegislationVO;
import com.chinatower.product.legalms.modules.dispute.vo.legislation.PageParam;
import com.chinatower.product.legalms.modules.dispute.vo.querylist.TLegislationListVO;
import com.chinatower.product.legalms.modules.flow.vo.flow.AddFlowVO;

public interface LegislationApi {

    /*法律文书起草*/
    @PostMapping("/legislation/addLegislationInfo")
    ProcessResult addLegislationInfo( AddFlowVO<LegislationVO> vo);

    /*法律文书列表查询*/
    @PostMapping("/legislation/findLegislation")
    ProcessResult findLegislation(PageParam param);

    /*暂存法律文书*/
    @PostMapping("/legislation/tempLegislation")
    ProcessResult tempLegislation(AddFlowVO<LegislationVO> vo);

    /*法律文书详情页面*/
    @GetMapping("/legislation/findOneLegislation")
    ProcessResult findOneLegislation(String id, String actId);

    /*逻辑删除*/
    @PostMapping("/legislation/deleteLegislation")
    ProcessResult deleteLegislation(List<String> ids);

   /*法律文书列表综合查询*/
    @PostMapping("/legislation/findLegislationList")
    ProcessResult findLegislationList(TLegislationListVO param);

    //法律文书审批接口
    @PostMapping("/legislation/addTFlowLog")
    ProcessResult addTFlowLog(HttpServletRequest request);

    @GetMapping("/legislation/getExcelDate")
    void getExcelDate(HttpServletResponse response) throws IOException;
}
