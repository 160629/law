package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.entity.depleader.OrgDepLeader;
import com.chinatower.product.legalms.modules.license.service.OrgDepLeaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Date: 2019/11/18 11:46
 * @Description:
 */
@RestController
@Api(tags = {"分管领导接口"})
public class OrgDepLeaderAPIImpl extends BaseController implements OrgDepLeaderAPI {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private OrgDepLeaderService orgDepLeaderService;


    /**
     * 功能描述:查询分管领导
     *
     * @param orgDepLeader
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2019/11/18 14:15
     */
    @ApiOperation(value = "查询分管领导")
    @Override
    public ProcessResult selectOrgDepLeader(@RequestBody OrgDepLeader orgDepLeader) {
        try {
            return orgDepLeaderService.selectOrgDepLeader(orgDepLeader);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FIND_DEPLEADER_ERROR);
        }
    }

    /**
     * 功能描述:删除分管领导
     *
     * @param orgDepLeader
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2019/11/18 14:15
     */
    @Override
    @ApiOperation(value = "删除分管领导")
    public ProcessResult deleteOrgDepLeader(@RequestBody OrgDepLeader orgDepLeader) {
        try {
            return orgDepLeaderService.deleteOrgDepLeader(orgDepLeader);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, e.toString());
        }
    }

    /**
     * 功能描述:更新分管领导
     *
     * @param orgDepLeader
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2019/11/18 14:15
     */
    @Override
    @ApiOperation(value = "更新分管领导")
    public ProcessResult updateOrgDepLeader(@RequestBody OrgDepLeader orgDepLeader) {
        try {
            return orgDepLeaderService.updateLeaderInfo(orgDepLeader);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, e.toString());
        }
    }

    /**
     * 功能描述: 组织树查询 （pid）
     *
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2019/11/20 15:25
     */
    @Override
    @ApiOperation(value = "查询组织树")
    @ApiIgnore
    public ProcessResult selectOrgTree() {
        try {
            return orgDepLeaderService.selectOrgTree();
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FIND_ORGTREE_ERROR);
        }
    }

    /**
     * 功能描述:分管领导人员树查询
     *
     * @param orgDepLeader
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @auther: guodong
     * @date: 2019/11/20 15:25
     */
    @Override
    @ApiOperation(value = "查询领导人")
    public ProcessResult selectLeaderTree(@RequestBody OrgDepLeader orgDepLeader) {

        try {
            return orgDepLeaderService.selectLeaderTree(orgDepLeader);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return new ProcessResult(ProcessResult.ERROR, e.toString());
        }
    }

    @Override
    public ProcessResult initLeaderData() {
        return orgDepLeaderService.initLeaderData();
    }
}
