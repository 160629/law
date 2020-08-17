package com.chinatower.product.legalms.modules.dispute.api.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.entity.common.IdeasVO;
import com.chinatower.product.legalms.modules.flow.service.common.IdeasService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags={"意见反馈接口"})
public class IdeaAPIImpl extends BaseController implements IdeaApi{

    private static final Logger logger = LoggerFactory.getLogger(IdeaAPIImpl.class);

    @Autowired
    private IdeasService ideasService;

    @ApiOperation(value = "增加意见信息")
    public ProcessResult addIdeas(@RequestBody IdeasVO ideasVO) {
        try {
            UserInfo info = RequestHolder.getUserInfo();
            return ideasService.addIdeas(ideasVO,info);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    @ApiOperation(value = "删除意见信息")
    public ProcessResult deleteIdeas(@RequestBody IdeasVO ideasVO) {
        try {
            return ideasService.deleteIdeas(ideasVO);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }
    @ApiOperation(value = "查询意见信息")
    public ProcessResult selectIdeas(@RequestBody IdeasVO ideasVO) {
        try {
            UserInfo info = RequestHolder.getUserInfo();// 获取当前登录人信息
            List<IdeasVO> list= ideasService.selectIdeas(ideasVO,info.getLoginAcct());
            return new ProcessResult(ProcessResult.SUCCESS, "", list);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }
    @ApiOperation(value = "更新意见信息")
    public ProcessResult updateIdeas(IdeasVO ideasVO) {
        try {
            return ideasService.updateIdeas(ideasVO);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }
}
