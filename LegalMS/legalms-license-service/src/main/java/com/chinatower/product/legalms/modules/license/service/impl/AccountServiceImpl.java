package com.chinatower.product.legalms.modules.license.service.impl;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.commen.ResponseEnum;
import com.chinatower.product.legalms.modules.license.commen.ServerResponse;
import com.chinatower.product.legalms.modules.license.entity.AccRoleImpowerVO;
import com.chinatower.product.legalms.modules.license.entity.AccountVO;
import com.chinatower.product.legalms.modules.license.entity.AuxiAccountVO;
import com.chinatower.product.legalms.modules.license.mapper.AccRoleImpowerVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.AccountVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.AuxiAccountVOMapper;
import com.chinatower.product.legalms.modules.license.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("ALL")
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private AccountVOMapper accountVOMapper;

    @Autowired
    private AccRoleImpowerVOMapper accRoleImpowerVOMapper;

    @Autowired
    private AuxiAccountVOMapper auxiAccountVOMapper;




    /**
     * 功能描述:4A_账号同步变更接口
     *
     * @param accountVO
     * @return com.chinatower.product.legalms.modules.license.commen.ServerResponse
     * @auther: GD
     * @date: 2019/10/10 11:36
     */
    @Override
    public ServerResponse operaAccount(AccountVO accountVO) {
        logger.info(InterfaceLogUtil.reqTransLog(SystemInfo.REQUEST_PARAM + JSON.toJSONString(accountVO)));
//        if(StringUtils.isBlank(accountVO.getFlag())||
//                StringUtils.isBlank(accountVO.getUserCode())||
//                StringUtils.isBlank(accountVO.getUserName())||
//                StringUtils.isBlank(accountVO.getOrgCode())||
//                StringUtils.isBlank(accountVO.getCreateDate())||
//                StringUtils.isBlank(accountVO.getSex())
//                ){
//            return ServerResponse.error(ResponseEnum.ERROR, ConstClass.FAILURE.ACCOUNT_DEAL_ERROR);
//        }
//        if(RequestHolder.specialSymbols(accountVO.getFlag())||
//                RequestHolder.specialSymbols(accountVO.getUserCode())||
//                RequestHolder.specialSymbols(accountVO.getUserName())||
//                RequestHolder.specialSymbols(accountVO.getOrgCode())||
//                RequestHolder.specialSymbols(accountVO.getCreateDate())||
//                RequestHolder.specialSymbols(accountVO.getSex())
//                ){
//            return ServerResponse.error(ResponseEnum.ERROR, ConstClass.FAILURE.ACCOUNT_DEAL_ERROR);
//        }
//        if(!isLegalDate(accountVO.getCreateDate())||(!isLegalDate(accountVO.getBirthday())&&StringUtils.isNotEmpty(accountVO.getBirthday()))){
//            return ServerResponse.error(ResponseEnum.ERROR, ConstClass.FAILURE.ACCOUNT_DEAL_ERROR);
//        }
        try {
            AccountVO account = accountVOMapper.selectAccountByCode(accountVO);
            if (account == null && !accountVO.getFlag().equals(SystemInfo.DELETE_ID)) {
                if(accountVO.getUserNature()==null){
                    //默认设置成铁塔内部账号 0 铁塔 1 服务商
                    accountVO.setUserNature("0");
                }
                accountVOMapper.addAccount(accountVO);
                logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.INSERT_SUCCESS));
            } else {
                if (accountVO.getFlag().equals(SystemInfo.DELETE_ID)) {
                    AccRoleImpowerVO accRoleImpowerVO = new AccRoleImpowerVO();
                    accRoleImpowerVO.setFlag(accountVO.getFlag());
                    accRoleImpowerVO.setUserCode(accountVO.getUserCode());
                    //删除兼职及主账号角色授权关系
                    accRoleImpowerVOMapper.deleteAccRoleImpower(accRoleImpowerVO);
                    //删除兼职信息
                    AuxiAccountVO auxiAccountVO = new AuxiAccountVO();
                    auxiAccountVO.setFlag(accountVO.getFlag());
                    auxiAccountVO.setUserCode(accountVO.getUserCode());
                    auxiAccountVOMapper.deleteAuxiAccount(auxiAccountVO, null);
                    //删除主账号信息
                    accountVO.setSyncStatus(SystemInfo.DATA_UPDATE);
                    accountVOMapper.deleteAccount(accountVO);
                    logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.DELETE_SUCCESS));
                } else {
                    if(accountVO.getUserNature()==null){
                        //默认设置成铁塔内部账号 0 铁塔 1 服务商
                        accountVO.setUserNature("0");
                    }
                    accountVO.setSyncStatus(SystemInfo.DATA_UPDATE);
                    accountVOMapper.updateAccount(accountVO);
                    logger.info(InterfaceLogUtil.rspTransLog(SystemInfo.SYSTEM_RESULT + SystemInfo.UPDATE_SUCCESS));
                }
            }
            return ServerResponse.success(ResponseEnum.SUCCESS);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
            return ServerResponse.error(ResponseEnum.ERROR, ConstClass.FAILURE.ACCOUNT_DEAL_ERROR);
        }
    }
}
