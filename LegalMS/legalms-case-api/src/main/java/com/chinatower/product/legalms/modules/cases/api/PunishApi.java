package com.chinatower.product.legalms.modules.cases.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.cases.entity.PunishVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface PunishApi {
    /*
     * 增加行政复议
     */
/*    @PostMapping("/case/addPunish")
    public ProcessResult addPunish(PunishVO punishVO,String filedId);*/

    /*
     * 查询行政复议详情
     */
    @PostMapping("/case/selectPunish")
    public ProcessResult selectPunish(PunishVO punish);

    /*
     * 修改行政复议
     */
/*
    @PutMapping("/case/updatePunish")
    public ProcessResult updatePunish(PunishVO punishVO);
*/

    /*
     * 增加或修改行政复议
     */
    @PostMapping("/case/addOrUpdatePunish")
    public ProcessResult addOrUpdatePunish(PunishVO punishVO);

    /*
     * 删除行政复议
     */
    @PostMapping("/case/deletePunish")
    public ProcessResult deletePunish(String caseId);
}
