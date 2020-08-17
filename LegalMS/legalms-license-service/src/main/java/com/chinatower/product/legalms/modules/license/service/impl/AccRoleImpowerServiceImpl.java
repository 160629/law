package com.chinatower.product.legalms.modules.license.service.impl;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.commen.ResponseEnum;
import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.AccRoleImpowerVO;
import com.chinatower.product.legalms.modules.license.entity.AccountVO;
import com.chinatower.product.legalms.modules.license.entity.RoleVo;
import com.chinatower.product.legalms.modules.license.mapper.AccRoleImpowerVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.AccountVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.RoleVoMapper;
import com.chinatower.product.legalms.modules.license.service.AccRoleImpowerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AccRoleImpowerServiceImpl implements AccRoleImpowerService {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private AccRoleImpowerVOMapper accRoleImpowerVOMapper;
    @Autowired
    private RoleVoMapper roleVoMapper;

    @Autowired
    private AccountVOMapper accountVOMapper;

  /**
   * 功能描述:4A_账号与角色授权变更接口
   * @auther: GD
   * @param accRoleImpowerVO
   * @return com.chinatower.product.legalms.modules.license.commen.ServerResponse
   * @date: 2019/10/10 11:37
   */
    @Override
    public ServerResponse operaAccRoleImpower(@RequestBody AccRoleImpowerVO accRoleImpowerVO) {
        logger.info(InterfaceLogUtil.reqTransLog(SystemInfo.REQUEST_PARAM + JSON.toJSONString(accRoleImpowerVO)));
//        if (isEmpty(accRoleImpowerVO)) return ServerResponse.error(ResponseEnum.ERROR);
//        if (isSpecialSymbols(accRoleImpowerVO)) return ServerResponse.error(ResponseEnum.ERROR);
        try {
            if (accRoleImpowerVO.getFlag().equals(SystemInfo.INSERT_ID)) {
                AccountVO accountVO = accountVOMapper.selectUserInfoByAccountId(accRoleImpowerVO.getUserCode());
                if(accountVO!=null){
                    accRoleImpowerVO.setUserName(StringUtils.defaultIfBlank(accountVO.getUserName(),""));
                }
                List<String> result = buildOrgCodeList(accRoleImpowerVO);
                accRoleImpowerVO.setRoleColeIds(String.join(",", accRoleImpowerVO.getRoleCodes()));
                List<AccRoleImpowerVO> list = accRoleImpowerVOMapper.selectAccRoleImpowerByCode(accRoleImpowerVO);
                buildAddInfo(accRoleImpowerVO, result, list);
                logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.INSERT_SUCCESS));
            } else if (accRoleImpowerVO.getFlag().equals(SystemInfo.DELETE_ID)&& !accRoleImpowerVO.getRoleCodes().isEmpty()) {
                accRoleImpowerVOMapper.deleteAccRoleImpower(accRoleImpowerVO);
                logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.DELETE_SUCCESS));
            }
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return ServerResponse.error(ResponseEnum.ERROR, e.toString());
        }
    }

    private void buildAddInfo(@RequestBody AccRoleImpowerVO accRoleImpowerVO, List<String> result, List<AccRoleImpowerVO> list) {
        if(!list.isEmpty()){
            List<String> collect = list.stream().map(AccRoleImpowerVO::getRoleColeIds).collect(Collectors.toList());
            List<String> newList = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                buildList(result, collect, newList, i);
            }
            if(!newList.isEmpty()){
                accRoleImpowerVO.setCreateTime(new Date());
                accRoleImpowerVO.setRoleCodes(newList);
                accRoleImpowerVOMapper.addAccRoleImpower(accRoleImpowerVO);
            }
        }else {
            accRoleImpowerVO.setCreateTime(new Date());
            accRoleImpowerVOMapper.addAccRoleImpower(accRoleImpowerVO);
        }
    }

//    private boolean isSpecialSymbols(@RequestBody AccRoleImpowerVO accRoleImpowerVO) {
//
//       return RequestHolder.specialSymbols(accRoleImpowerVO.getFlag())||
//                RequestHolder.specialSymbols(accRoleImpowerVO.getServiceId())||
//                RequestHolder.specialSymbols(accRoleImpowerVO.getUserCode())||
//                RequestHolder.specialSymbols(accRoleImpowerVO.getOrgCode())||
//                RequestHolder.specialSymbols(accRoleImpowerVO.getPermissionKey());
//
//    }
//
//    private boolean isEmpty(@RequestBody AccRoleImpowerVO accRoleImpowerVO) {
//        return  StringUtils.isBlank(accRoleImpowerVO.getFlag())||
//                StringUtils.isBlank(accRoleImpowerVO.getServiceId())||
//                StringUtils.isBlank(accRoleImpowerVO.getUserCode())||
//                StringUtils.isBlank(accRoleImpowerVO.getOrgCode())||
//                StringUtils.isBlank(accRoleImpowerVO.getPermissionKey())||
//                accRoleImpowerVO.getRoleCodes().isEmpty();
//
//
//    }

    private void buildList(List<String> list, List<String> collect, List<String> newList, int i) {
        if(!collect.contains(list.get(i))){
            newList.add(list.get(i));
        }
    }


    private List<String> buildOrgCodeList(AccRoleImpowerVO accRoleImpowerVO) {
        List<String> roleCodes = accRoleImpowerVO.getRoleCodes();
        List<String> list = new ArrayList<>();
        //对兼职信息进行批量增加
        if (!roleCodes.isEmpty()) {
            roleCodes.forEach(x -> list.add((x)));
        }
        return list;
    }

    @Override
    public ProcessResult selectAccRoleImpowerByAccountId(AccRoleImpowerVO accRoleImpowerVO) {
        List<AccRoleImpowerVO> result1 =  accRoleImpowerVOMapper.selectAccRoleImpowerByAccountId(accRoleImpowerVO.getAccountId());
        if(result1.isEmpty()){
            return new ProcessResult(ProcessResult.SUCCESS,"该用户无角色信息");
        }
        List<String> collect = result1.stream().map(AccRoleImpowerVO::getRoleColeIds).collect(Collectors.toList());
        List<RoleVo> result =  roleVoMapper.selectYetAllRole(collect).stream().filter(x->x.getRoleCode().contains(SystemInfo.ROLE_CODE_MODEL)).collect(Collectors.toList());
        return new ProcessResult(ProcessResult.SUCCESS,"",result);
    }

}
