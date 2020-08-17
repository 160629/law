package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.AuxiAccountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AuxiAccountVOMapper {

    public List<AuxiAccountVO> selectAuxiAccountByCode(@Param("userCode")String userCode,@Param("idlist") List<String> list);

    public List<AuxiAccountVO> selectBySyncStatus(String str);

    public void deleteAuxiAccount(@Param("auxiAccountVO") AuxiAccountVO auxiAccountVO, @Param("idlist") List<String> list);

    public void addAuxiAccount(@Param("auxiAccountVO") AuxiAccountVO auxiAccountVO, @Param("idlist") List<String> list);

    public void updateAuxiAccountBySyncStatus(@Param("status")String status, @Param("userCodeList") List<String> userCodeList);

    void updateAuxiAccountByCode(@Param("auxiAccountVO") AuxiAccountVO auxiAccountVO,@Param("idlist")List<String> idlist);

    List<AuxiAccountVO> selectByUnSyncStatus(String dealEd);
}
