package com.chinatower.product.legalms.modules.cases.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.cases.entity.AgainInstanceVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface AgainInstanceApi {
    /*
     * 增加再审
     */
/*
    @PostMapping("/case/addAgainInstance")
    public ProcessResult addAgainInstance(AgainInstanceVO againInstanceVO,String filedId);
*/

    /*
     * 查询再审详情
     */
    @PostMapping("/case/selectAgainInstance")
    public ProcessResult selectAgainInstance(AgainInstanceVO againInstance);

/*    *//*
     * 修改再审
     *//*
    @PutMapping("/case/updateAgainInstance")
    public ProcessResult updateAgainInstance(AgainInstanceVO againInstanceVO);*/

    /*
     * 增加或修改再审
     */
    @PostMapping("/case/addOrUpdateAgainInstance")
    public ProcessResult addOrUpdateAgainInstance(AgainInstanceVO againInstanceVO);

    /*
     * 删除再审
     */
    @PostMapping("/case/deleteAgainInstance")
    public ProcessResult deleteAgainInstance(String caseId);
}
