package com.chinatower.product.legalms.modules.system.mapper;

import com.chinatower.product.legalms.modules.system.entity.AnnouncementVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface AnnouncementVoMapper extends Mapper<AnnouncementVO> {

    List<AnnouncementVO> selectAllAnnouncement(@Param(value = "announcementTypeid") Integer announcementTypeid,
                                               @Param(value = "announcementStatus") Integer announcementStatus,
                                               @Param(value = "announcementName") String announcementName,
                                               @Param(value = "announcementTime") Date announcementTime,
                                               @Param(value = "announcementTime1") Date announcementTime1,
                                               @Param(value = "announcementIssuername")  String announcementIssuername
    );

    List<AnnouncementVO> selectAnnouncementbyfive();

    List<AnnouncementVO>  selectAnnouncementMore(AnnouncementVO announcement);

    int insertAnnouncementById(AnnouncementVO announcement);

    int updateAnnouncementById(AnnouncementVO announcement);

    AnnouncementVO selectannouncementById(String id);
}
