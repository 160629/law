package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.service.TActivityParticipatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Date: 2020/8/4 15:48
 * @Description:
 */
@RestController
public class TActivityParticipatorAPIImpl extends BaseController implements TActivityParticipatorAPI {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private TActivityParticipatorService tActivityParticipatorService;


    public ProcessResult selectOrgTree(@RequestBody Map map) {
        try {
            return tActivityParticipatorService.selectOrgTree(map);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.NEXT_USER_ERROR);
        }
    }
}
