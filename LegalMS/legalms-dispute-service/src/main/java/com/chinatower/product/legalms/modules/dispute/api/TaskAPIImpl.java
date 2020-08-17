package com.chinatower.product.legalms.modules.dispute.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.modules.dispute.service.TaskService;
import com.chinatower.product.legalms.modules.flow.vo.common.TaskVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = { "待办、已办、办结接口" })
public class TaskAPIImpl extends BaseController implements TaskAPI {

    @Autowired
    TaskService taskService;

    @Override
    @ApiOperation("获取待办已办办结列表")
    @ApiImplicitParams({@ApiImplicitParam(name="taskVO",value="表单查询条件")})
    public ProcessResult queryPersonWorkItems(@RequestBody TaskVO taskVO) {
        return taskService.queryPersonWorkItems(taskVO);
    }

//    @Override
//    @ApiOperation("待办统计")
//    @ApiImplicitParams({@ApiImplicitParam(name="userId",value="用户ID")})
//    public ProcessResult queryPersonTaskCount(@RequestParam("userId") String userId) {
//        return taskService.queryPersonTaskCount(userId);
//    }
}
