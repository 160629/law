package com.chinatower.product.legalms.modules.license.service.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.entity.AccountVO;
import com.chinatower.product.legalms.modules.license.entity.AuxiAccountVO;
import com.chinatower.product.legalms.modules.license.entity.OrgBeanVO;
import com.chinatower.product.legalms.modules.license.mapper.AccountVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.AuxiAccountVOMapper;
import com.chinatower.product.legalms.modules.license.mapper.OrgBeanVOMappper;
import com.chinatower.product.legalms.modules.license.mapper.SyncInfoMapper;
import com.chinatower.product.legalms.modules.license.service.SyncInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SyncInfoServiceImpl implements SyncInfoService {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private SyncInfoMapper syncInfoMapper;

    @Autowired
    private AccountVOMapper accountVOMapper;

    @Autowired
    private AuxiAccountVOMapper auxiAccountVOMapper;

    @Autowired
    private OrgBeanVOMappper orgBeanVOMappper;

    /**
     * 功能描述:同步4A数据至业务表
     *
     * @param
     * @return com.chinatower.product.legalms.modules.license.commen.ServerResponse
     * @auther: GD
     * @date: 2019/10/10 11:37
     */
    @Override
    public ProcessResult syncInfo() {
        logger.info("进入用户数据同步操作");
        try {
            String notDeal = SystemInfo.NOT_DEAL;
            String dealEd = SystemInfo.DEAL_ED;
            buildAccountResult(notDeal, dealEd);
            logger.info("用户信息已同步至业务表");
            buildAuxiAccountResult(notDeal, dealEd);
            logger.info("兼职信息已同步至业务表");
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR, e);
        }
        logger.info("数据同步完毕");
        return new ProcessResult(ProcessResult.SUCCESS, "定时任务完毕");
    }

    private void buildAuxiAccountResult(String notDeal, String dealEd) {
        int auxiCount;
        List<AuxiAccountVO> auxiAccountVOlist = null;
        auxiAccountVOlist = auxiAccountVOMapper.selectBySyncStatus(SystemInfo.DATA_UPDATE);
        if (!auxiAccountVOlist.isEmpty()) {
            /*同步用户(更新)兼职信息*/
            auxiAccountVOlist.forEach(x -> syncInfoMapper.updateSyncAuxiAccountInfo(x));
        }
        /*同步用户兼职信息*/
        auxiCount = syncInfoMapper.addSyncAuxiAccountInfo(notDeal);
        if (auxiCount > 0 || !auxiAccountVOlist.isEmpty()) {
            List<AuxiAccountVO> list = auxiAccountVOMapper.selectByUnSyncStatus(dealEd);
            List<String> userCodeList = list.stream().map(AuxiAccountVO::getUserCode).collect(Collectors.toList());
            /*设置同步用户兼职信息为已处理*/
            if (!userCodeList.isEmpty()) {
                auxiAccountVOMapper.updateAuxiAccountBySyncStatus(dealEd, userCodeList);
            }
        }
    }

    private void buildAccountResult(String notDeal, String dealEd) {
        List<AccountVO> accountVOlist = null;
        int userCount;
        int accountCount;
        /*同步用户(更新)详细信息*/
        accountVOlist = accountVOMapper.selectByStatus(SystemInfo.DATA_UPDATE);
        if (!accountVOlist.isEmpty()) {
            accountVOlist.forEach(x -> {
                syncInfoMapper.updateSyncUserInfo(x);
                syncInfoMapper.updateSyncAccountInfo(x);
            });
        }
        /*同步用户详细信息*/
        userCount = syncInfoMapper.addSyncUserInfo(notDeal);
        /*同步用户账号信息*/
        accountCount = syncInfoMapper.addSyncAccountInfo(notDeal);
        if (userCount > 0 || accountCount > 0 || !accountVOlist.isEmpty()) {
            List<AccountVO> list = accountVOMapper.selectByUnStatus(dealEd);
            List<String> userCodeList = list.stream().map(AccountVO::getUserCode).collect(Collectors.toList());
            List<AccountVO> providerList = list.stream().filter(x->StringUtils.isNotBlank(x.getUserNature())).filter(x -> x.getUserNature().equals("1")).collect(Collectors.toList());
            /*设置同步用户信息为已处理*/
            if (!userCodeList.isEmpty()) {
                accountVOMapper.updateAccountBySyncStatus(dealEd, userCodeList);
            }
            //服务商账号没有组织的直接挂在服务商根节点下
            if (!providerList.isEmpty()) {
                for (int i = 0; i < providerList.size(); i++) {
                    int count = orgBeanVOMappper.selectOrgProviderInfoByOrgCode(providerList.get(i).getOrgCode());
                    if (count == 0) {
                        //直接挂在服务商根节点下
                        accountVOMapper.updateOrgCodeByUserCode(providerList.get(i).getUserCode());
                    }
                    //更新服务商服务区域字段信息
                    if (StringUtils.isNotBlank(providerList.get(i).getServiceCode())) {
                        List<OrgBeanVO> orgBeanVOS = orgBeanVOMappper.selectOrgInfoByCode(providerList.get(i).getServiceCode());
                        String unitType = RequestHolder.getOrgType(orgBeanVOS.get(0).getEnterpType());
                        String providerArea = unitType + "-" + orgBeanVOS.get(0).getCompanyName() + "-" + orgBeanVOS.get(0).getOrgName();
                        accountVOMapper.updateServiceCodeByAccoungId(providerList.get(i).getUserCode(), providerArea);
                    }
                }
            }
        }
    }
}

