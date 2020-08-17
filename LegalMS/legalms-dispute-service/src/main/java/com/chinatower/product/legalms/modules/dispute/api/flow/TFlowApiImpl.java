package com.chinatower.product.legalms.modules.dispute.api.flow;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.vo.unview.AutoView;
import com.eos.workflow.omservice.WFParticipant;

/**
 * @Date: 2019/12/20 09:51
 * @Description:
 */
@RestController
public class TFlowApiImpl implements TFlowApi{

    private static final Logger logger = LoggerFactory.getLogger("TransLog");

	@Autowired
	TFlowCommonService commonService;

    public ProcessResult autoUnView(@RequestParam(value = "transferId") String transferId,
                                    @RequestParam(value = "transferUser") String transferUser,
                                    @RequestParam(value = "receptId") String receptId,
                                    @RequestParam(value = "receptUser") String receptUser,
                                    @RequestParam(value = "caseNum") Integer caseNum,
                                    @RequestParam(value = "cause") String cause){
        try {
            WFParticipant participant = new WFParticipant(transferId,transferUser,"emp");
            List<WFParticipant> list = new ArrayList<>();
            WFParticipant participants = new WFParticipant(receptId,receptUser,"emp");
            list.add(participants);
            int view = commonService.autoUnView("t_case_transfer",participant,list,caseNum,cause);
            return new ProcessResult(ProcessResult.SUCCESS,"移交生成待阅成功",view);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ProcessResult(ProcessResult.SUCCESS,"移交生成待阅异常",e.toString());
        }

    }

	@Override
	public Integer autoUnViewTest(@RequestBody AutoView obj) {
		return 1;
		
	}

}
