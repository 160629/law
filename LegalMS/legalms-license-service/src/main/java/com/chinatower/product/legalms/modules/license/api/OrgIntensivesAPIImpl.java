package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.entity.jiyuehua.OrgIntensives;
import com.chinatower.product.legalms.modules.license.service.OrgIntensivesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2020/4/9 15:01
 * @Description:
 */
@RestController
public class OrgIntensivesAPIImpl extends BaseController implements OrgIntensivesAPI  {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);


    @Autowired
    private OrgIntensivesService orgIntensivesService;


    public ProcessResult selectOrgIntensives(@RequestBody OrgIntensives orgIntensives) {
        try {
            return orgIntensivesService.selectOrgIntensives(orgIntensives);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.ORG_INTENSIVES_ERROR);
        }
    }

    @Override
    public ProcessResult updateOrgIntensives(@RequestBody OrgIntensives orgIntensives) {
        try {
            return orgIntensivesService.updateOrgIntensives(orgIntensives);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.ORG_INTENSIVES_ERROR);
        }
    }


}
