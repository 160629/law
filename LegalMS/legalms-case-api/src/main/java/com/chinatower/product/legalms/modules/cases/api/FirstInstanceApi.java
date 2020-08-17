package com.chinatower.product.legalms.modules.cases.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.cases.entity.FirstInstanceVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface FirstInstanceApi {
    /*
     * 增加一审
     */
/*    @PostMapping("/case/addFirstInstance")
    public ProcessResult addFirstInstance(FirstInstanceVO firstInstanceVO,String filedId);*/

    /*
     * 一审详情
     */
    @PostMapping("/case/selectFirstInstance")
    public ProcessResult selectFirstInstance(FirstInstanceVO firstInstance);

    /*
     * 修改一审
     */
/*    @PutMapping("/case/updateFirstInstance")
    public ProcessResult updateFirstInstance(FirstInstanceVO firstInstanceVO);*/

    /*
     * 增加或修改一审
     */
    @PostMapping("/case/addOrUpdateFirstInstance")
    public ProcessResult addOrUpdateFirstInstance(FirstInstanceVO firstInstanceVO);

    /*
     * 删除一审
     */
    @PostMapping("/case/deleteFirstInstance")
    public ProcessResult deleteFirstInstance(String caseId);
}
