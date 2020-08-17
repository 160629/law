package com.chinatower.product.legalms.modules.dispute.api.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.service.common.OrgDepLeaderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags={"组织部门领导接口"})
public class OrgDepLeaderAPIImpl implements OrgDepLeaderAPI {

    private static final Logger logger = LoggerFactory.getLogger("TransLog");

    @Autowired
    private OrgDepLeaderService orgDepLeaderService;

    @ApiOperation(value = "查询组织部门领导")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "创建人组织编码",name = "creatorOrgCode")
    })
    public ProcessResult selectOrgDepLeader(@RequestParam("creatorOrgCode")String creatorOrgCode) {

        try {
           List<AccountLogic> orgDepLeader =  orgDepLeaderService.selectOrgDepLeader(creatorOrgCode);
            return new ProcessResult(ProcessResult.SUCCESS,"",orgDepLeader);
        } catch (Exception e) {
            logger.error(DisputeConstClass.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.FAILURE.ORGLEADER_FIND_ERROR);
        }
    }
}
