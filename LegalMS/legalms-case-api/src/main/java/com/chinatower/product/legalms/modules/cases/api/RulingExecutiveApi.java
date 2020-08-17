package com.chinatower.product.legalms.modules.cases.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.cases.entity.RulingExecutiveVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface RulingExecutiveApi {
    /*
     * 增加裁决执行
     */
/*    @PostMapping("/case/addRulingExecutive")
    public ProcessResult addRulingExecutive(RulingExecutiveVO rulingExecutiveVO);*/

    /*
     * 裁决执行详情
     */
    @PostMapping("/case/selectRulingExecutive")
    public ProcessResult selectRulingExecutive(RulingExecutiveVO rulingExecutiveVO);

    /*
     * 修改裁决执行
     */
/*    @PutMapping("/case/updateRulingExecutive")
    public ProcessResult updateRulingExecutive(RulingExecutiveVO rulingExecutiveVO);*/

    /*
     * 增加或修改裁决执行
     */
    @PostMapping("/case/addOrUpdateRulingExecutive")
    public ProcessResult addOrUpdateRulingExecutive(RulingExecutiveVO rulingExecutiveVO);
}
