package com.chinatower.product.legalms.modules.license;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.api.OrgBeanAPI;
import com.chinatower.product.legalms.modules.license.entity.OrgBeanVO;
import com.chinatower.product.legalms.modules.license.mapper.OrgBeanVOMappper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@Api(tags = {"组织机构接口"})
public class OrgBeanService implements OrgBeanAPI {
    private static final Logger logger = LoggerFactory.getLogger("TransLog");

    @Autowired
    private OrgBeanVOMappper orgBeanVOMappper;

    @Override
    @ApiOperation("查询组织接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", required = true)
    })
    public ProcessResult selectOrgbean(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<OrgBeanVO> orgBeanList = orgBeanVOMappper.selectAllorgBean();
        PageInfo<OrgBeanVO> pageInfo = new PageInfo<>(orgBeanList);
        return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);
    }

    @Override
    @ApiOperation("添加组织接口")
    public ProcessResult addOrgbean(@RequestBody OrgBeanVO orgBeanVO) {
        logger.info(InterfaceLogUtil.reqTransLog(SystemInfo.REQUEST_PARAM + JSON.toJSONString(orgBeanVO)));
        int a;
        try {
            a = orgBeanVOMappper.addorgBean(orgBeanVO);
            logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + a));
            return new ProcessResult(ProcessResult.SUCCESS, "", a);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }

    @Override
    @ApiOperation("修改组织接口")
    public ProcessResult updateOrgbean(@RequestBody OrgBeanVO orgBeanVO) {
        logger.info(InterfaceLogUtil.reqTransLog(SystemInfo.REQUEST_PARAM + JSON.toJSONString(orgBeanVO)));
        int a;
        try {
            a = orgBeanVOMappper.updateorgBean(orgBeanVO);
            logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + a));
            return new ProcessResult(ProcessResult.SUCCESS, "", a);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }

    @Override
    @ApiOperation("删除组织接口")
    public ProcessResult deleteOrgbean(@RequestBody OrgBeanVO orgBeanVO) {
        logger.info(InterfaceLogUtil.reqTransLog(SystemInfo.REQUEST_PARAM + JSON.toJSONString(orgBeanVO)));
        int a;
        try {
            a = orgBeanVOMappper.deleteorgBean(orgBeanVO);
            logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + a));
            return new ProcessResult(ProcessResult.SUCCESS, "", a);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR, e.getMessage());
        }
    }

    @Override
    @ApiOperation("查询我方争议主体接口")
    public ProcessResult selectOrgbeanByAreaName() {
        List<OrgBeanVO> orgBeanList = orgBeanVOMappper.selectOrgbeanByAreaName();
        for (int i = 0; i < orgBeanList.size(); i++) {
            if (StringUtils.isBlank(orgBeanList.get(i).getAreaName())) {
                orgBeanList.get(i).setAreaName(orgBeanList.get(i).getOrgName());
            }
        }
        return new ProcessResult(ProcessResult.SUCCESS, "", orgBeanList);
    }

    @Override
    public ProcessResult selectOrgInfoByorgCode(@RequestBody OrgBeanVO orgBeanVO) {
        OrgBeanVO orgBean = orgBeanVOMappper.selectOrgInfoByorgCode(orgBeanVO.getOrgCode(),orgBeanVO.getOrgTableType());
        OrgBeanVO orgBeans = orgBeanVOMappper.selectOrgInfoByorgCode(orgBean.getOrgParentCode(),orgBeanVO.getOrgTableType());
        if(orgBeans!=null){
            orgBean.setOrgParentCodeName(orgBeans.getOrgName());
        }
        orgBean.setOrgLevel(RequestHolder.getOrgLevel(orgBean.getOrgLevel()));
        orgBean.setOrgStatus(RequestHolder.getOrgStatus(orgBean.getOrgStatus()));
        orgBean.setOrgTableType(orgBean.getOrgTableType());
        return new ProcessResult(ProcessResult.SUCCESS, "", orgBean);
    }
    @Override
    public ProcessResult selectSubordinateOrgInfoByorgCode(@RequestBody OrgBeanVO orgBeanVO) {
        PageHelper.startPage(orgBeanVO.getPageNum(), orgBeanVO.getPageSize());
        List<OrgBeanVO> list = orgBeanVOMappper.selectNextOrgInfo(orgBeanVO.getOrgCode(),orgBeanVO.getOrgTableType());
        list.forEach(x->
            x.setOrgStatus(RequestHolder.getOrgStatus(x.getOrgStatus()))
        );
        PageInfo<OrgBeanVO> pageInfo = new PageInfo<>(list);
        return new ProcessResult(ProcessResult.SUCCESS, "", pageInfo);

    }

    //将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }
}
