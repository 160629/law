package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.vo.dict.SysDictTypePage;
import com.chinatower.product.legalms.vo.dict.SysDictTypeVO;
import org.springframework.web.bind.annotation.PostMapping;

public interface SysDictTypeApi {

    //数据字典列表
    @PostMapping("/v1/sysDictType/selectDictType")
    public ProcessResult selectDictType(SysDictTypePage sysDictTypePage);

    //数据字典添加
    @PostMapping("/v1/sysDictType/addDictType")
    public ProcessResult addDictType(SysDictTypeVO sysDictTypeVO);

    //数据字典修改
    @PostMapping("/v1/sysDictType/updateDictType")
    public ProcessResult updateDictType(SysDictTypeVO sysDictTypeVO);

    //数据字典删除
    @PostMapping("/v1/sysDictType/deleteDictType")
    public ProcessResult deleteDictType(int dictId);

/*    @PostMapping("/v1/sysDictType/sysDictTypeExcelExpress")
    public ProcessResult sysDictTypeExcelExpress(HttpServletResponse response);*/
}
