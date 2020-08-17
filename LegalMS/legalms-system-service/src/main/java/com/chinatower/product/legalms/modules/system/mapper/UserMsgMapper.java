package com.chinatower.product.legalms.modules.system.mapper;

import com.chinatower.product.legalms.modules.system.entity.AccRoleImpowerVO;
import com.chinatower.product.legalms.modules.system.entity.UserMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date: 2019/11/6 11:00
 * @Description:
 */
public interface UserMsgMapper {

    UserMsg selectUserLevel(String orgCode);

    int deleteByPrimaryKey(String userId);

    int insert(UserMsg record);

    int insertSelective(UserMsg record);

    UserMsg selectUserInfo(@Param("orgCode") String orgCode,
                           @Param("userId")String userId,
                           @Param("accountId")String accountId);

    int updateByPrimaryKeySelective(UserMsg record);

    int updateByPrimaryKey(UserMsg record);

    List<AccRoleImpowerVO> selectAccRoleImpowerByAccountId(String userCode);
}
