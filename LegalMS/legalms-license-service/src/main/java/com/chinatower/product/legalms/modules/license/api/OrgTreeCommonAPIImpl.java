package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.entity.floworgtree.CommenPermission;
import com.chinatower.product.legalms.modules.license.service.OrgTreeCommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2019/11/27 14:33
 * @Description:流程组织树控制器
 */
@RestController
@Api(tags = {"公共组织树查询"})
public class OrgTreeCommonAPIImpl extends BaseController implements OrgTreeCommonAPI {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);


    @Autowired
    private OrgTreeCommonService orgTreeCommonService;

    @ApiOperation(value = "查询组织树")
    @Override
    public ProcessResult selectOrgTree(@RequestBody CommenPermission commenPermission) {

        try {
            return orgTreeCommonService.orgTreeCommonService(commenPermission);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FIND_ORGTREE_ERROR);
        }
    }
}
