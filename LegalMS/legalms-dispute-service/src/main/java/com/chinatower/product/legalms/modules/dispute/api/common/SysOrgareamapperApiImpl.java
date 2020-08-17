package com.chinatower.product.legalms.modules.dispute.api.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.flow.entity.common.SysOrgareamapperVO;
import com.chinatower.product.legalms.modules.flow.service.common.SysOrgareamapperService;

@RestController
//@Api(tags = {"我方争议主体"})
public class SysOrgareamapperApiImpl implements SysOrgareamapperAPI{

    @Autowired
    SysOrgareamapperService service;

    @Override
/*    @ApiOperation("我方争议主体查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgCode", value = "关联组织机构代码", required = true),
            @ApiImplicitParam(name = "areaCode", value = "地区编码 主键", required = true),
            @ApiImplicitParam(name = "areaName", value = "地区名称", required = true)
    })*/
    public ProcessResult selectSysOrgareamapperByorgCode(@RequestParam(value = "orgCode") String orgCode,
                                                         @RequestParam(value = "areaCode",required = false) String areaCode,
                                                         @RequestParam(value = "areaName",required = false) String areaName) {
        SysOrgareamapperVO sysOrgareamapperVO =  service.selectSysOrgareamapperByorgCode(orgCode,areaCode,areaName);
        return new ProcessResult(ProcessResult.SUCCESS,"",sysOrgareamapperVO );
    }
}
