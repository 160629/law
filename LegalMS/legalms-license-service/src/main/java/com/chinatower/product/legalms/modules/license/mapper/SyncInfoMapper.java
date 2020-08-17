package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.AccountVO;
import com.chinatower.product.legalms.modules.license.entity.AuxiAccountVO;

public interface SyncInfoMapper {

    public int addSyncUserInfo(String str);

    public int addSyncAccountInfo(String str);

    public int addSyncAuxiAccountInfo(String str);

    public int updateSyncUserInfo(AccountVO accountVO);

    public int updateSyncAccountInfo(AccountVO accountVO);

    public int updateSyncAuxiAccountInfo(AuxiAccountVO auxiAccountVO);


}
