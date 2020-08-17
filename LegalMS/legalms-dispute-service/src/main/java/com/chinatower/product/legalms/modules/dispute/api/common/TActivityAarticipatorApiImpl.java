package com.chinatower.product.legalms.modules.dispute.api.common;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.modules.dispute.entity.CommonParam;
import com.chinatower.product.legalms.modules.dispute.service.common.TActivityAarticipatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TActivityAarticipatorApiImpl extends BaseController implements TActivityParticipatorApi {

    private static final Logger logger = LoggerFactory.getLogger(DisputeConstClass.LOGGER_NAME);

    @Autowired
    private TActivityAarticipatorService activityAarticipatorService;

    @Override

   /* public ProcessResult selectParticipatorByCode(@RequestParam(value = "flowId")String flowId,
                                                  @RequestParam(value = "beginId")String beginId,
                                                  @RequestParam(value = "endId")String endId,
                                                  @RequestParam(value = "joinlyCode", required = false)String joinlyCode,
                                                  @RequestParam(value = "deptCode", required = false)String deptCode,
                                                  @RequestParam(value = "processInstId", required = false) Long processInstId,
                                                  @RequestParam(value = "loginAcct", required = false) String loginAcct) {*/
    public ProcessResult selectParticipatorByCode(@RequestBody CommonParam commonParam) {

        try {
            return  activityAarticipatorService.selectParticipatorByCode(commonParam);
        } catch (Exception e) {
            logger.error(DisputeConstClass.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.FAILURE.TA_FAILURE_MESS);
        }

    }
}
