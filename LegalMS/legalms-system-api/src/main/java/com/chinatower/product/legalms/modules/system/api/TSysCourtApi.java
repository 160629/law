package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.system.entity.TSysCourtPage;
import com.chinatower.product.legalms.modules.system.entity.TSysCourtVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
/**
 * @author shiyuqi
 * @date
 */
public interface TSysCourtApi {
    @PostMapping("/v1/tSysCourt/selectTSysCourt")
    public ProcessResult selectTSysCourt(TSysCourtPage tSysCourtPage);

    @PostMapping("/v1/tSysCourt/selectTSysCourtName")
    public ProcessResult selectTSysCourtName(TSysCourtPage tSysCourtPage);

    @PostMapping("/v1/tSysCourt/deleteTSysCourt")
    public ProcessResult deleteTSysCourt(TSysCourtVO tSysCourtVO);

    @PostMapping("/v1/tSysCourt/addTSysCourt")
    public ProcessResult addTSysCourt(TSysCourtVO tSysCourtVO);

    @PostMapping("/v1/tSysCourt/updateTSysCourt")
    public ProcessResult updateTSysCourt(TSysCourtVO tSysCourtVO);

    @GetMapping("/v1/tSysCourt/selectTSysCourtOne")
    public ProcessResult selectTSysCourtOne(String id);

    @PostMapping("/v1/tSysCourt/selectTSysCourtTree")
    public ProcessResult selectTSysCourtTree(TSysCourtVO tSysCourtVO);

}
