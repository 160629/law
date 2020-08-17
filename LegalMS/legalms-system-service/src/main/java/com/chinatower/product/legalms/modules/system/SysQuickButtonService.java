package com.chinatower.product.legalms.modules.system;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.system.api.SysQuickButtonAPI;
import com.chinatower.product.legalms.modules.system.entity.SysQuickButtonPage;
import com.chinatower.product.legalms.modules.system.entity.SysQuickButtonVO;
import com.chinatower.product.legalms.modules.system.mapper.SysQuickButtonMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@Component
public class SysQuickButtonService extends BaseController implements SysQuickButtonAPI {
    @Autowired
    private SysQuickButtonMapper sysQuickButtonMapper;
    private static final Logger logger = LoggerFactory.getLogger("TransLog");
    @Override
    public ProcessResult sellectQuickButton(@RequestBody SysQuickButtonPage sysQuickButtonPage) {
            try {
                PageHelper.startPage(sysQuickButtonPage.getPageNum(),sysQuickButtonPage.getPageSize());
                List<SysQuickButtonVO> list= sysQuickButtonMapper.sellectQuickButton(sysQuickButtonPage);
                PageInfo<SysQuickButtonVO> pageInfo = new PageInfo<>(list);
                return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
            } catch (Exception e) {
                logger.error(ProcessResult.ERROR,e);
                return new ProcessResult(ProcessResult.ERROR,e.getMessage());
            }
        }

    @Override
    public ProcessResult deleteQuickButton(@RequestBody SysQuickButtonVO sysQuickButtonVO) {
        try {
            int a = sysQuickButtonMapper.deleteQuickButton(sysQuickButtonVO);
            if(a!=0){
                return new ProcessResult(ProcessResult.SUCCESS,"",a);
            }else{
                return new ProcessResult(ProcessResult.ERROR,"刪除失败");
            }
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SYSDICT_DATA_DELETE_ERROR);
        }
    }

    @Override
    public ProcessResult addQuickButton(@RequestBody SysQuickButtonVO sysQuickButtonVO) {
        int a;
        try {
            a = sysQuickButtonMapper.addQuickButton(sysQuickButtonVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",a);
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.SYSDICT_DATA_INSERT_ERROR);
        }
    }


    @Override
    public ProcessResult updateQuickButton(@RequestBody SysQuickButtonVO sysQuickButtonVO) {
       int a;
        try {
            a = sysQuickButtonMapper.updateQuickButton(sysQuickButtonVO);
            return new ProcessResult(ProcessResult.SUCCESS,"",a);
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.SYSDICT_DATA_UPDATE_ERROR);
        }
    }
}
