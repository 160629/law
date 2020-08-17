package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.vo.dict.SysDictDataVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.websocket.server.PathParam;

public interface SysDictDataApi {
    @GetMapping("/v1/sysdictdata/selectdictById")
    public ProcessResult selectDictById(@PathParam("dictId") Integer dictId);
    @GetMapping("/v1/sysdictdata/selectdictByType")
    public ProcessResult selectDictByType(@PathParam("dictType") String dictType);

    //数据字典详情列表
    @PostMapping("/v1/sysdictdata/selectSysDictData")
    public ProcessResult selectSysDictData(SysDictDataVO sysDictDataVO);

    //数据字典详情添加
    @PostMapping("/v1/sysdictdata/addSysDictData")
    public ProcessResult addSysDictData(SysDictDataVO sysDictDataVO);

    //数据字典详情修改
    @PostMapping("/v1/sysdictdata/updateSysDictData")
    public ProcessResult updateSysDictData(SysDictDataVO sysDictDataVO);

    //数据字典详情删除
    @PostMapping("/v1/sysdictdata/deleteSysDictData")
    public ProcessResult deleteSysDictData(SysDictDataVO sysDictDataVO);

    @PostMapping("/v1/sysdictdata/selectAllSysDictData")
    public ProcessResult selectAllSysDictData();

    @PostMapping("/v1/sysdictdata/selectDictDataByType")
    public ProcessResult selectDictDataByType(@RequestBody SysDictDataVO sysDictDataVO);

}
