package com.chinatower.product.legalms.modules.cases.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.cases.entity.ArbittateVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface ArbittateApi {
    /*
     * 增加仲裁
     */
/*    @PostMapping("/case/addArbittate")
    public ProcessResult addArbittate(ArbittateVO arbittateVO,String filedId);*/

    /*
     * 查询仲裁
     */
    @PostMapping("/case/selectArbittate")
    public ProcessResult selectArbittate(ArbittateVO arbittate);

    /*
     * 修改仲裁
     */
/*    @PutMapping("/case/updateArbittate")
    public ProcessResult updateArbittate(ArbittateVO arbittateVO);*/


    /*
     * 增加或修改仲裁
     */
    @PostMapping("/case/addOrUpdateArbittate")
    public ProcessResult addOrUpdateArbittate(ArbittateVO arbittateVO);

    /*
     * 删除仲裁
     */
    @PostMapping("/case/deleteArbittate")
    public ProcessResult deleteArbittate(String caseId);
}
