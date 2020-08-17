package com.chinatower.product.legalms.modules.license.service.impl;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.commen.ResponseEnum;
import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.AuxiAccountVO;
import com.chinatower.product.legalms.modules.license.mapper.AuxiAccountVOMapper;
import com.chinatower.product.legalms.modules.license.service.AuxiAccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AuxiAccountServiceImpl implements AuxiAccountService {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private AuxiAccountVOMapper auxiAccountVOMapper;

   /**
    * 功能描述:4A_附属组织同步变更接口
    * @auther: GD
    * @param auxiAccountVO
    * @return com.chinatower.product.legalms.modules.license.commen.ServerResponse
    * @date: 2019/10/10 11:37
    */
    @Override
    public ServerResponse operaAuxiAccount(@RequestBody AuxiAccountVO auxiAccountVO) {
        logger.info(InterfaceLogUtil.reqTransLog(SystemInfo.REQUEST_PARAM + JSON.toJSONString(auxiAccountVO)));
//        if (isEmpty(auxiAccountVO))
//            return ServerResponse.error(ResponseEnum.ERROR, ConstClass.FAILURE.USERAUXI_DEAL_ERROR);
//        if (isSpecialSymbols(auxiAccountVO))
//            return ServerResponse.error(ResponseEnum.ERROR, ConstClass.FAILURE.USERAUXI_DEAL_ERROR);
        Date date = new Date();
        try {
            if (auxiAccountVO.getFlag().equals(SystemInfo.INSERT_ID)) {
                String userCode = auxiAccountVO.getUserCode();
                List<String> list = buildOrgCodeList(auxiAccountVO);
                List<AuxiAccountVO> result = auxiAccountVOMapper.selectAuxiAccountByCode(userCode,list);//查询是否有重复数据
                buildAddInfo(auxiAccountVO, date, list, result);
                logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.INSERT_SUCCESS));
            } else if (auxiAccountVO.getFlag().equals(SystemInfo.DELETE_ID)) {
                List<String> list = buildOrgCodeList(auxiAccountVO);
                auxiAccountVO.setSyncStatus(SystemInfo.DATA_UPDATE);
                auxiAccountVO.setUpdateTime(date);
                if(!list.isEmpty()){
                    auxiAccountVOMapper.deleteAuxiAccount(auxiAccountVO, list);
                }
                logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.DELETE_SUCCESS));
            }
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return ServerResponse.error(ResponseEnum.ERROR,ConstClass.FAILURE.USERAUXI_DEAL_ERROR);
        }
    }

    private void buildAddInfo(@RequestBody AuxiAccountVO auxiAccountVO, Date date, List<String> list, List<AuxiAccountVO> result) {
        if(!result.isEmpty()){
            //buildResult(auxiAccountVO, date, result);
            List<String> collect = result.stream().map(AuxiAccountVO::getPartOrgCode).collect(Collectors.toList());
            List<String> newList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                buildList(list, collect, newList, i);
            }
            if(!newList.isEmpty()){
                auxiAccountVO.setCreateTime(date);
                auxiAccountVOMapper.addAuxiAccount(auxiAccountVO, newList);
            }
        }else{
            auxiAccountVO.setCreateTime(date);
            auxiAccountVOMapper.addAuxiAccount(auxiAccountVO, list);
        }
    }

//    private boolean isSpecialSymbols(@RequestBody AuxiAccountVO auxiAccountVO) {
//      return   RequestHolder.specialSymbols(auxiAccountVO.getFlag())||
//                RequestHolder.specialSymbols(auxiAccountVO.getUserCode())||
//                RequestHolder.specialSymbols(auxiAccountVO.getPartOrgCode())||
//                RequestHolder.specialSymbols(auxiAccountVO.getPermissionKey());
//    }
//
//    private boolean isEmpty(@RequestBody AuxiAccountVO auxiAccountVO) {
//        return  StringUtils.isBlank(auxiAccountVO.getFlag())||
//                StringUtils.isBlank(auxiAccountVO.getUserCode())||
//                StringUtils.isBlank(auxiAccountVO.getPartOrgCode())||
//                StringUtils.isBlank(auxiAccountVO.getPermissionKey());
//
//    }

//    private void buildResult(@RequestBody AuxiAccountVO auxiAccountVO, Date date, List<AuxiAccountVO> result) {
//        for (int i = 0; i < result.size(); i++) {
//            List<String> delList = result.stream().filter(x->"3".equals(x.getFlag())).map(AuxiAccountVO::getPartOrgCode).collect(Collectors.toList());
//            if(!delList.isEmpty()){
//                auxiAccountVO.setSyncStatus("2");
//                auxiAccountVO.setUpdateTime(date);
//                auxiAccountVOMapper.updateAuxiAccountByCode(auxiAccountVO,delList);
//            }
//            List<String> unDelList = result.stream().filter(x->"1".equals(x.getFlag())).map(AuxiAccountVO::getPartOrgCode).collect(Collectors.toList());
//            if(!unDelList.isEmpty()){
//                if("0".equals(result.get(i).getSyncStatus())){
//                    auxiAccountVO.setSyncStatus("0");
//                }
//                if("1".equals(result.get(i).getSyncStatus())){
//                    auxiAccountVO.setSyncStatus("1");
//                }
//                auxiAccountVO.setUpdateTime(date);
//                auxiAccountVOMapper.updateAuxiAccountByCode(auxiAccountVO,unDelList);//更新存在的数据
//            }
//        }
//    }

    private void buildList(List<String> list, List<String> collect, List<String> newList, int i) {
        if(!collect.contains(list.get(i))){
            newList.add(list.get(i));
        }
    }

    private List<String> buildOrgCodeList(@RequestBody AuxiAccountVO auxiAccountVO) {
        String partOrgCode = auxiAccountVO.getPartOrgCode();
        List<String> list = new ArrayList<>();
        //对兼职信息进行批量增加
        if (StringUtils.isNotEmpty(partOrgCode)) {
            Arrays.asList(partOrgCode.split(",")).forEach(x -> list.add((x)));
        }
        return list;
    }
}

