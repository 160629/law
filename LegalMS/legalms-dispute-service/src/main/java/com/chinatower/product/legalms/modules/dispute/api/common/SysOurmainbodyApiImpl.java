package com.chinatower.product.legalms.modules.dispute.api.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.modules.flow.entity.common.SysOurmainbodyVO;
import com.chinatower.product.legalms.modules.flow.service.common.SysOurmainbodyService;
import com.chinatower.product.legalms.modules.flow.vo.common.SysOurmainbodyPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = {"我方争议主体"})
public class SysOurmainbodyApiImpl extends BaseController implements SysOurmainbodyApi {

    @Autowired
    private SysOurmainbodyService sysOurmainbodyService;

    @Override
    @ApiOperation("我方争议主体查询接口")
    public ProcessResult selectSysOurmainbody(@RequestBody SysOurmainbodyPage sysOurmainbodyPage) {
        PageHelper.startPage(sysOurmainbodyPage.getPageNum(), sysOurmainbodyPage.getPageSize());
        List<SysOurmainbodyVO> sysOurmainbodyVOS= sysOurmainbodyService.selectSysOurmainbodyAll(sysOurmainbodyPage);
        PageInfo<SysOurmainbodyVO> pageInfo = new PageInfo<>(sysOurmainbodyVOS);
        return new ProcessResult(ProcessResult.SUCCESS,"",pageInfo);
    }

    @Override
    public ProcessResult selectSysOurmainbodyAndConfig(@RequestBody SysOurmainbodyVO sysOurmainbodyVO) {
        List<SysOurmainbodyVO> sysOurmainbodyVOS = sysOurmainbodyService.selectSysOurmainbodyAndConfig(sysOurmainbodyVO.getApproveItemId(),sysOurmainbodyVO.getApproveItemType());
        return new ProcessResult(ProcessResult.SUCCESS,"",sysOurmainbodyVOS);
    }
}
