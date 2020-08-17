package com.chinatower.product.legalms.modules.flow.service.common;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.common.DraftsVO;

/**
 * @Auther: G D
 * @Date: 2019/10/21 14:54
 * @Description:
 */
public interface DraftsService {

    public ProcessResult selectDrafts(DraftsVO draftsVO);

    public ProcessResult updateDrafts(DraftsVO draftsVO);

    public ProcessResult addDrafts(DraftsVO draftsVO);

    public ProcessResult deleteBatchDrafts(DraftsVO draftsVO);

    public ProcessResult delDraftsByItem(DraftsVO draftsVO);

    public ProcessResult delBatchDraftsByItem(DraftsVO draftsVO);
}
