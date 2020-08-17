package com.chinatower.product.legalms.modules.dispute.api.common;

import org.springframework.web.bind.annotation.PostMapping;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.common.DraftsVO;

/**
 * @Auther: G D
 * @Date: 2019/10/21 14:42
 * @Description:
 */
public interface DraftsAPI {

    /**
     * 功能描述: 查询草稿箱列表
     * @auther: guodong
     * @param draftsVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/1/3 14:55
     */
    @PostMapping("/v1/drafts/selectDrafts")
    public ProcessResult selectDrafts(DraftsVO draftsVO);

    /**
     * 功能描述: 更新草稿箱
     * @auther: guodong
     * @param draftsVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/1/3 14:55
     */
    @PostMapping("/v1/drafts/updateDrafts")
    public ProcessResult updateDrafts(DraftsVO draftsVO);

    /**
     * 功能描述: 增加草稿箱
     * @auther: guodong
     * @param draftsVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/1/3 14:55
     */
    @PostMapping("/v1/drafts/addDrafts")
    public ProcessResult addDrafts(DraftsVO draftsVO);

    /**
     * 功能描述: 单个删除草稿箱
     * @auther: guodong
     * @param draftsVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/1/3 14:55
     */
    @PostMapping("/v1/drafts/delDraftsByItem")
    public ProcessResult delDraftsByItem(DraftsVO draftsVO);

    /**
     * 功能描述: 批量删除草稿箱
     * @auther: guodong
     * @param draftsVO
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/1/3 14:55
     */
    @PostMapping("/v1/drafts/delBatchDraftsByItem")
    public ProcessResult delBatchDraftsByItem(DraftsVO draftsVO);
}
