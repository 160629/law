package com.chinatower.product.legalms.modules.cases.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.cases.entity.SecondInstanceVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface SecondInstanceApi {
    /*
     * 增加二审
     */
/*    @PostMapping("/case/addSecondInstance")
    public ProcessResult addSecondInstance(SecondInstanceVO secondInstanceVO,String filedId);*/

    /*
     * 二审详情
     */
    @PostMapping("/case/selectSecondInstance")
    public ProcessResult selectSecondInstance(SecondInstanceVO secondInstance);

    /*
     * 修改二审
     */
/*    @PutMapping("/case/updateSecondInstance")
    public ProcessResult updateSecondInstance(SecondInstanceVO secondInstanceVO);*/

    /*
     * 增加或修改二审
     */
    @PostMapping("/case/addOrUpdateSecondInstance")
    public ProcessResult addOrUpdateSecondInstance(SecondInstanceVO secondInstanceVO);

    /*
     * 删除二审
     */
    @PostMapping("/case/deleteSecondInstance")
    public ProcessResult deleteSecondInstance(String caseId);
}
