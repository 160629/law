package com.chinatower.product.legalms.modules.dispute.api.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.DisputeConstClass;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.entity.common.AccountLogic;
import com.chinatower.product.legalms.modules.flow.service.common.UserInfoService;

/**
 * @Date: 2020/3/16 14:14
 * @Description:
 */
@RestController
public class UserInfoApiImpl {


    private static final Logger logger = LoggerFactory.getLogger(DisputeConstClass.LOGGER_NAME);


    @Autowired
    private UserInfoService userInfoService;


    /*
     * 功能描述:根据用户查询详细信息
     * @auther: guodong
     * @param accountId
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/3/16 14:19
     */
    public ProcessResult selectUserInfo(String accountId){

        try {
            AccountLogic accountLogic = userInfoService.selectUserInfo(accountId);
            return new ProcessResult(ProcessResult.SUCCESS,"",accountLogic);
        } catch (Exception e) {
            logger.error(DisputeConstClass.OPERATION_EXCEPTION , e);
            return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.FAILURE.USERINFO_FIND_ERROR);
        }

    }

    /**
     * 功能描述:根据角色集合查询用户（引诉诉讼查待阅人）
     * @auther: guodong
     * @param roleList
     * @param userInfo
     * @param orgLevel
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/3/16 14:19
     */
    public  ProcessResult selectUserByRoleCode (List<String>roleList, UserInfo userInfo, String orgLevel){
        try {
            List<AccountLogic> list = userInfoService.selectUserByRoleCode(roleList,userInfo,orgLevel);
            return new ProcessResult(ProcessResult.SUCCESS,"",list);
        } catch (Exception e) {
            logger.error(DisputeConstClass.OPERATION_EXCEPTION , e);
            return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.FAILURE.USERINFO_FIND_ERROR);
        }
    }
    public  ProcessResult selectCopyUserByRoleAndOrgCode (List<String> roleList, List<String> orgList){
        try {
            List<AccountLogic> list = userInfoService.selectCopyUserByRoleAndOrgCode(roleList,orgList);
            return new ProcessResult(ProcessResult.SUCCESS,"",list);
        } catch (Exception e) {
            logger.error(DisputeConstClass.OPERATION_EXCEPTION , e);
            return new ProcessResult(ProcessResult.ERROR,DisputeConstClass.FAILURE.USERINFO_FIND_ERROR);
        }
    }

}
