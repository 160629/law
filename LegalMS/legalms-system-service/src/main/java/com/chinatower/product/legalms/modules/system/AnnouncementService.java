package com.chinatower.product.legalms.modules.system;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.system.api.AnnouncementAPI;
import com.chinatower.product.legalms.modules.system.entity.AnnouncementVO;
import com.chinatower.product.legalms.modules.system.mapper.AnnouncementVoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.chinatower.product.legalms.modules.filecommon.service.filecommon.FilecommonService;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class AnnouncementService extends BaseController implements AnnouncementAPI {
    private static final Logger logger = LoggerFactory.getLogger("TransLog");

    @Autowired
    private  AnnouncementVoMapper announcementVOMapper;

    @Autowired
    FilecommonService filecommonService;

    /**
     * 公告信息查询
     */
    @Override
    public ProcessResult findAnnouncement(@RequestBody AnnouncementVO announcement) {
        PageHelper.startPage(announcement.getPageNum(), announcement.getPageSize());

        List<AnnouncementVO> announcementList = announcementVOMapper.selectAllAnnouncement
                (announcement.getAnnouncementTypeid(),
                        announcement.getAnnouncementStatus(),
                        announcement.getAnnouncementName(),
                        announcement.getAnnouncementStarttime(),
                        announcement.getAnnouncementFinishtime(),
                        announcement.getAnnouncementIssuername());
        Date nowDate = new Date();



        for(int i=0;i<announcementList.size();i++){
            AnnouncementVO vo = announcementList.get(i);
            //如果是发布完毕的状态无需再判断
            if(!SystemInfo.ANNOUNCE_DEAD_STATE.equals(vo.getAnnouncementState())){
                Date startDate = vo.getAnnouncementStarttime();
                Date endDate = vo.getAnnouncementFinishtime();
                boolean flag = false;
                if(getStartFlag(startDate, nowDate)){
                    vo.setAnnouncementState(SystemInfo.ANNOUNCE_NO_STATE);
                    flag = true;
                }

                if(getEndFlag(endDate, nowDate)){
                    vo.setAnnouncementState(SystemInfo.ANNOUNCE_DEAD_STATE);
                    flag = true;
                }
                if(flag){
                    continue;
                }
                vo.setAnnouncementState(SystemInfo.ANNOUNCE_ING_STATE);
            }
        }

        PageInfo<AnnouncementVO> pageInfo = new PageInfo<>(announcementList);
        return new ProcessResult(ProcessResult.SUCCESS,"",pageInfo);
    }

    /**
     * 当前时间大于有效期结束时间 为发布完毕状态
     * @param endDate
     * @param nowDate
     * @return
     */
    private boolean getEndFlag(Date endDate, Date nowDate) {
            /*if(endDate!=null && nowDate.compareTo(endDate) > 0){
                return true;
            }*/
        return endDate!=null && nowDate.compareTo(endDate) > 0;
    }

    /**
     * 当前时间小于有效期开始时间 为待发布状态
     * @param startDate
     * @param nowDate
     * @return
     */
    private boolean getStartFlag(Date startDate,Date nowDate) {
//            if(startDate!=null && startDate.compareTo(nowDate) > 0){
//                return true;
//            }
        return startDate!=null && startDate.compareTo(nowDate) > 0;
    }

    public static String getDateStr(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(currentTime);
    }

    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 公告信息增加
     */
    @Override
    public ProcessResult addAnnouncement(@RequestBody AnnouncementVO announcement) {
        logger.info(InterfaceLogUtil.reqTransLog(SystemInfo.REQUEST_PARAM + JSON.toJSONString(announcement)));
        try {
            //获取用户信息
            UserInfo userInfo = RequestHolder.getUserInfo();
            announcement.setAnnouncementIssuerid(userInfo.getLoginAcct());
            announcement.setAnnouncementIssuername(userInfo.getLoginName());

            if(announcement.getAnnouncementStarttime()!=null){
                Date startDate = announcement.getAnnouncementStarttime();
                startDate = strToDateLong(getDateStr(startDate)+" 00:00:00");
                announcement.setAnnouncementStarttime(startDate);
            }

            if(announcement.getAnnouncementFinishtime()!=null){
                Date endDate = announcement.getAnnouncementFinishtime();
                endDate = strToDateLong(getDateStr(endDate)+" 23:59:59");
                announcement.setAnnouncementFinishtime(endDate);
            }

            //判断有没有文件
            if(StringUtils.isNotBlank(announcement.getFiledId())){
                announcement.setAnnouncementFilestate(SystemInfo.FILE_Y);
            }else{
                announcement.setAnnouncementFilestate(SystemInfo.FILE_N);
            }
            announcementVOMapper.insertAnnouncementById(announcement);
            //文件存储方法
//            filecommonService.updateFile(announcement.getFilelist(), RequestHolder.getUserInfo(), announcement.getAnnouncementId());

            return new ProcessResult(ProcessResult.SUCCESS,"");
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    @Override
    /**
     * 取消发布 传个主键和announcementState 状态 2为发布完毕
     */
    public ProcessResult updateAnnouncement(@RequestBody AnnouncementVO announcement) {
        logger.info(InterfaceLogUtil.reqTransLog(SystemInfo.REQUEST_PARAM+JSON.toJSONString(announcement)));
        try {
            //判断有没有文件
            if(StringUtils.isNotBlank(announcement.getFiledId())){
                announcement.setAnnouncementFilestate(SystemInfo.FILE_Y);
            }else{
                announcement.setAnnouncementFilestate(SystemInfo.FILE_N);
            }
            announcementVOMapper.updateAnnouncementById(announcement);
            return new ProcessResult(ProcessResult.SUCCESS,"");
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    @Override
    /**
     *
     */
    public ProcessResult deleteAnnouncement(@RequestBody AnnouncementVO announcement) {
        logger.info(InterfaceLogUtil.reqTransLog(SystemInfo.REQUEST_PARAM+JSON.toJSONString(announcement)));
        try {
            announcementVOMapper.deleteByPrimaryKey(announcement);
            return new ProcessResult(ProcessResult.SUCCESS,"");
        }catch (Exception e){
            logger.error(SystemInfo.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,e.getMessage());
        }
    }

    /**
     * 公告首页查询
     */
    @Override
    public ProcessResult selAnnouncement() {
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            //e.printStackTrace();
            logger.error(SystemInfo.OPERATION_EXCEPTION, e);
        }

        List<AnnouncementVO> announcementList= announcementVOMapper.selectAnnouncementbyfive();
        return new ProcessResult(ProcessResult.SUCCESS,"",announcementList);
    }

    /**
     * 公告首页查看更多
     * @param announcement
     * @return
     */
    @Override
    public ProcessResult selannouncementMore(@RequestBody AnnouncementVO announcement) {
        PageHelper.startPage(announcement.getPageNum(), announcement.getPageSize());
        List<AnnouncementVO> announcementList= announcementVOMapper.selectAnnouncementMore(announcement);
        PageInfo<AnnouncementVO> pageInfo = new PageInfo<>(announcementList);
        return new ProcessResult(ProcessResult.SUCCESS,"",pageInfo);
    }

    /**
     * 公告详情 查文件需要调用file模块的接口
     */
    @Override
    public ProcessResult selAnnouncementById(String id) {
        AnnouncementVO announcementVO =  announcementVOMapper.selectannouncementById(id);
        return new ProcessResult(ProcessResult.SUCCESS,"",announcementVO);
    }

}
