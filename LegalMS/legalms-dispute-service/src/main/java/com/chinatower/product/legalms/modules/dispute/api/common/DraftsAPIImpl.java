package com.chinatower.product.legalms.modules.dispute.api.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.modules.flow.entity.common.DraftsVO;
import com.chinatower.product.legalms.modules.flow.service.common.DraftsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Auther: G D
 * @Date: 2019/10/21 14:53
 * @Description:
 */
@RestController
@Api(tags={"草稿箱接口"})
public class DraftsAPIImpl extends BaseController implements DraftsAPI  {

    private static final Logger logger = LoggerFactory.getLogger(DisputeConstClass.LOGGER_NAME);


    @Autowired
    private DraftsService draftsService;

    /**
     * 功能描述:查询文案信息
     * @auther: G D
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/10/22 11:15
     */
    @ApiOperation(value = "查询文案信息")
    public ProcessResult selectDrafts(@RequestBody DraftsVO draftsVO) {
        try {
            return draftsService.selectDrafts(draftsVO);
        } catch (Exception e) {
            logger.info(DisputeConstClass.OPERATION_EXCEPTION ,e);
            return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.FAILURE.DRAFTS_FIND_ERROR);
        }
    }

    /**
     * 功能描述:更新文案信息
     * @auther: G D
     * @param draftsVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/10/22 11:15
     */
    @ApiOperation(value = "更新文案信息")
    public ProcessResult updateDrafts(@RequestBody DraftsVO draftsVO) {
        try {
            return draftsService.updateDrafts(draftsVO);
        } catch (Exception e) {
            logger.info(DisputeConstClass.OPERATION_EXCEPTION ,e);
            return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.FAILURE.DRAFTS_UPDATE_ERROR);
        }
    }

    /**
     * 功能描述:增加文案信息
     * @auther: G D
     * @param draftsVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/10/22 11:15
     */
    @ApiOperation(value = "增加文案信息")
    public ProcessResult addDrafts(@RequestBody DraftsVO draftsVO) {
        try {
            return draftsService.addDrafts(draftsVO);
        } catch (Exception e) {
            logger.info(DisputeConstClass.OPERATION_EXCEPTION ,e);
            return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.FAILURE.DRAFTS_INSERT_ERROR);
        }
    }

    /**
     * 功能描述: 根据审批事项类型和ID删除
     * @auther: guodong
     * @param draftsVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/11/18 14:41
     */
    @ApiOperation(value = "删除文案信息(approveItemId和approveItemType)")
    @Override
    public ProcessResult delDraftsByItem(@RequestBody DraftsVO draftsVO) {
        try {
            return draftsService.delDraftsByItem(draftsVO);
        } catch (Exception e) {
            logger.info(DisputeConstClass.OPERATION_EXCEPTION ,e);
            return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.FAILURE.DRAFTS_DELETE_ERROR);
        }
    }


    @DeleteMapping("/test11")
    ProcessResult deleteBatchDrafts(@RequestBody DraftsVO draftsVO){
        return draftsService.deleteBatchDrafts(draftsVO);
    }

    /**
     * 功能描述:删除文案信
     * @auther: G D
     * @param draftsVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2019/10/22 11:15
     */
    @ApiOperation(value = "批量删除数据")
    public ProcessResult delBatchDraftsByItem(@RequestBody DraftsVO draftsVO){
        try {
            return draftsService.delBatchDraftsByItem(draftsVO);
        } catch (Exception e) {
            logger.info(DisputeConstClass.OPERATION_EXCEPTION , e);
            return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.FAILURE.DRAFTS_ALLDELETE_ERROR);
        }
    }
}
