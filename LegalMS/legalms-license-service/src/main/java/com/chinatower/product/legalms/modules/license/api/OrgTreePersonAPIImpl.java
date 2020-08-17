package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.entity.floworgtree.CommenPermission;
import com.chinatower.product.legalms.modules.license.service.OrgTreePersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2020/8/4 14:28
 * @Description:
 */
@RestController
public class OrgTreePersonAPIImpl extends BaseController implements OrgTreePersonAPI {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);


    @Autowired
    private OrgTreePersonService orgTreePersonService;

    /**
     * 功能描述:公共组织树人员查询
     *
     * @param
     * @return
     * @auther: guodong
     * @date: 2019/11/20 15:25
     */
    @Override
    public ProcessResult selectOrgTreeAndPerson(@RequestBody CommenPermission commenPermission) {
        try {
            return orgTreePersonService.selectOrgTreeAndPerson(commenPermission);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e.toString());
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FIND_ORGTREE_ERROR);
        }
    }


}
