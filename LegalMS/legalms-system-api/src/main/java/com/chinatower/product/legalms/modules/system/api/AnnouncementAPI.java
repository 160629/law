package com.chinatower.product.legalms.modules.system.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.system.entity.AnnouncementVO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public interface AnnouncementAPI {

    /**
     * 公告列表展示
     */
    @PostMapping("/announcement/findannouncement")
    public ProcessResult findAnnouncement(AnnouncementVO announcement);

    /**
     * 公告发布
     */
    @PostMapping("/announcement/addAnnouncement")
    public ProcessResult addAnnouncement(AnnouncementVO announcement);

    /**
     * 公告修改
     */
    @PostMapping("/announcement/updateannouncement")
    public ProcessResult updateAnnouncement(AnnouncementVO announcement);


    /**
     * 公告删除
     */
    @DeleteMapping("/announcement/deleteannouncement")
    public ProcessResult deleteAnnouncement(AnnouncementVO announcement);

    /**
     * 首页公告展示
     */
    @GetMapping("/announcement/selannouncement")
    public ProcessResult selAnnouncement();

    /**
     * 查看更多 和首页查看公告的权限一致
     * @return
     */
    @PostMapping("/announcement/selannouncementMore")
    public ProcessResult selannouncementMore(AnnouncementVO announcement);

    /**
     * 公告详情查询
     */
    @GetMapping("/announcement/selannouncementById")
    public ProcessResult selAnnouncementById(String id);
}
