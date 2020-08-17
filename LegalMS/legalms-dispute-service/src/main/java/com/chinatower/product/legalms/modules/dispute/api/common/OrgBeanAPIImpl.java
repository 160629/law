package com.chinatower.product.legalms.modules.dispute.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.modules.flow.entity.common.OrgBeanVO;
import com.chinatower.product.legalms.modules.flow.service.common.OrgBeanService;

/**
 * @Date: 2019/12/2 20:24
 * @Description:
 */
@RestController

public class OrgBeanAPIImpl implements OrgBeanAPI {

    private static final Logger logger = LoggerFactory.getLogger(DisputeConstClass.LOGGER_NAME);

    @Autowired
    private OrgBeanService orgBeanService;


    @Override
    public ProcessResult selectOrgLevelByorgCode(OrgBeanVO orgBeanVO) {
        try {
            return orgBeanService.selectOrgLevelByorgCode(orgBeanVO);
        } catch (Exception e) {
            logger.info(DisputeConstClass.OPERATION_EXCEPTION , e);
            return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.FAILURE.ORGLEVEL_FIND_ERROR);
        }
    }
}
