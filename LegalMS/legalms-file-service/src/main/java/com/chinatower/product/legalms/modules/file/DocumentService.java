package com.chinatower.product.legalms.modules.file;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.FileInfo;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.file.api.DocumentApi;
import com.chinatower.product.legalms.modules.filecommon.mapper.filecommon.FileMainMapper;
import com.chinatower.product.legalms.modules.filecommon.mapper.filecommon.FileShareMapper;
import com.chinatower.product.legalms.modules.filecommon.vo.filecommon.*;
import com.chinatower.provider.call.chinatowerfileservice.client.AttachFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Slf4j
@RestController
public class DocumentService extends BaseController implements DocumentApi {

    @Bean(name = "remoteRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    @Qualifier(value = "remoteRestTemplate")
    private RestTemplate restTemplate;

    @Value("${file.uploadFileAddress}")
    private String uploadFileAddress;

    @Value("${file.loadFileAddress}")
    private String loadFileAddress;

    @Value("${file.loadFileMultAddress}")
    private String loadFileMultAddress;

    @Value("${file.deleteFileAddress}")
    private String deleteFileAddress;

    @Value("${file.updateFileAddress}")
    private String updateFileAddress;

    @Value("${file.privateKey}")
    private String privateKey;
    /**
     * 内网地址
     */
    @Value("${file.intranetIp}")
    private String intranetIp;
    /**
     * 外网地址
     */
    @Value("${file.extranetIp}")
    private String extranetIp;
    @Autowired
    private FileMainMapper fileMainVoMapper;

    @Autowired
    private FileShareMapper fileShareVoMapper;

    @Autowired
    private AttachFeignApi attachFeignApi;

    private static final Logger logger = LoggerFactory.getLogger("TransLog");


    @Override
    /**
     * 附件查询
     *
     */
    public ProcessResult selectFile(String fileShareBusinessKey,String shareType,String processInstId) {
        List<Map> map=  fileMainVoMapper.selectAllfile(fileShareBusinessKey,shareType,processInstId);
        logger.info(ConstClass.SUCCESS.SUCCESS_MESS,map);
        return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.SUCCESS_MESS,map);
    }


    @Override
    public ProcessResult dowloadFile(String fileId, String userId) {
        try{
            String filedown = attachFeignApi.dowloadFile(userId,fileId);
            logger.info(ConstClass.FAILURE.OPERATION_EXCEPTION + filedown);

        }catch(Exception e){
            logger.info(ConstClass.FAILURE.FILE_GET_ERROR , e);
        }
        return null;
    }

    @Override
    public ProcessResult batchDowloadFile(String fileIds, String userId) {
        return null;
    }


    /**
     * 附件删除
     */
    /*
    @Override
    public ProcessResult deleteFile(@RequestParam("fileId")String fileId,
                                    @RequestParam("userId") String userId,
                                    @RequestBody FileMainVO fileMainVO) {
        if(StringUtils.isEmpty(fileId)){
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FILEID_NOEXIST);
        }
        try {
            int i = fileMainVoMapper.updatefilemain(fileMainVO);
            if(i>0){
                return new ProcessResult(ProcessResult.SUCCESS,ConstClass.DELETE_SUCCESS);
            }
        } catch (Exception e) {
            logger.info(ConstClass.OPERATION_EXCEPTION +e);
            return new ProcessResult(ProcessResult.BUZ_EXCEPTION,e.toString());

        }
        return null;
    }*/

    @Override
    /**
     * 附件更新
     */
    @Transactional
    public ProcessResult updateFile(@RequestParam("fileId")String fileId,
                                    @RequestParam("visibilityLevel")String visibilityLevel,
                                    @RequestParam("userId")String userId,
                                    @RequestParam("file")MultipartFile file) {
        logger.info("需要修改的文件id  " + fileId);
        if(StringUtils.isEmpty(file)) {
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FILE_NOEXIST);
        }
        Map<String,Object> fileupdate = null;
        FileMainVO fileMainVO = new FileMainVO();
//        FileShareVO fileShareVO = new FileShareVO();
        try {
            fileupdate = attachFeignApi.updateFile(fileId, FileInfo.VISIBILITYLEVEL, userId, file);
            String code =  (String) fileupdate.get("code");
            String msg =  (String) fileupdate.get("msg");
            logger.info(ConstClass.SUCCESS.FILE_RESULT+fileupdate);
            if(FileInfo.FILE_STATE.equals(code)) {
                String filen=file.getOriginalFilename();
                String filename = filen.substring(0,filen.lastIndexOf('.'));
                String fileextension=filen.substring(filen.lastIndexOf('.')+1,filen.length());
                LinkedHashMap linkedHashMap = (LinkedHashMap)fileupdate.get("data");
                String httpurl = (String) linkedHashMap.get("httpUrl");
                fileMainVO.setFileId(fileId);
                fileMainVO.setFileName(getFileName(filename));
                fileMainVO.setFileExtension(fileextension);
                fileMainVO.setFileHttpUrl(httpurl);
                fileMainVO.setFileUploadAppId(userId);
                fileMainVO.setVisibilityLevel(visibilityLevel);
                fileMainVO.setFileLength(strToLong(file));
                int t = fileMainVoMapper.updatefilemain(fileMainVO);
                if(t>0){
                    return new ProcessResult(ProcessResult.SUCCESS,ConstClass.SUCCESS.SUCCESS_MESS,""+msg);
                }else{
                    return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.UPDATE_ERROR_NORESULT);
                }
            }
            else {
                return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.UPDATE_ERROR);
            }
        } catch (Exception e) {
            logger.info(ConstClass.FAILURE.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.FAILURE_MESS);
        }
    }


    @Override
    public ProcessResult uploadFileMain(MultipartFile[] file) {
        UserInfo userInfo = RequestHolder.getUserInfo();
        String userId = userInfo.getLoginAcct();
        if(StringUtils.isEmpty(file) || file.length==0) {
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FILE_NOEXIST);
        }
        Map<String,Object> fileBack = null;
        try{
            List<LinkedHashMap> maps = new ArrayList<>();
            for(int i=0;i<file.length;i++){
                MultipartFile file1 = file[i];
                if(file1.getSize()/FileInfo.UNIT /FileInfo.UNIT<FileInfo.ASTRICT_LENGH) {
                    long time1 = new Date().getTime();
                    fileBack = attachFeignApi.uploadFile(FileInfo.VISIBILITYLEVEL, file1, userId);
                    logger.info(ConstClass.SUCCESS.FILE_RESULT + fileBack);
                    String code = (String) fileBack.get("code");
                    long time2 = new Date().getTime();
                    logger.info(FileInfo.TIME_COST + (time2 - time1) + "毫秒");
                    if (FileInfo.FILE_STATE.equals(code)) {
                        String filen = file1.getOriginalFilename();
                        logger.info(filen);
                        String filename = filen.substring(0, filen.lastIndexOf('.'));
                        String fileextension = filen.substring(filen.lastIndexOf('.') + 1, filen.length());
                        LinkedHashMap linkedHashMap = (LinkedHashMap) fileBack.get("data");
                        String fileId = (String) linkedHashMap.get(FileInfo.FILE_ID);
                        String httpUrl = (String) linkedHashMap.get(FileInfo.HTTP_URL);

                        FileMainVO fileMainVO = new FileMainVO();
                        fileMainVO.setFileId(fileId);
                        fileMainVO.setFileName(getFileName(filename));
                        fileMainVO.setFileExtension(fileextension);
                        fileMainVO.setFileHttpUrl(httpUrl);
                        fileMainVO.setFileUploadAppId(null);
                        fileMainVO.setFileUploadUserId(userId);
                        fileMainVO.setVisibilityLevel(FileInfo.VISIBILITYLEVEL);
                        fileMainVO.setFileLength(strToLong(file1));
                        fileMainVO.setFileUploadTime(new Date());
                        fileMainVO.setFileStatusUpdateTime(new Date());
                        fileMainVoMapper.insertFileMain(fileMainVO);
                        maps.add(linkedHashMap);
                    } else {
                        return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.INSERT_ERROR);
                    }
                }else {
                    return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FILE_ASTRICT);
                }
            }
            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SUCCESS_MESS, maps);
        }catch(Exception e){
            logger.info(ConstClass.FAILURE.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FAILURE_MESS);
        }
    }

    @Override
    public ProcessResult uploadFileShare(MultipartFile[] file, String primaryKey,String shareType,String processInstId) {
        UserInfo userInfo = RequestHolder.getUserInfo();
        String userId = userInfo.getLoginAcct();
        if(StringUtils.isEmpty(file)) {
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FILE_NOEXIST);
        }
        if(StringUtils.isEmpty(primaryKey)){
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.PRIMARY_NOEXIST);
        }
        Map<String,Object> fileBack = null;
        FileMainVO fileMainVO = new FileMainVO();
        List<LinkedHashMap> list = new ArrayList<>();
        try{
            for(int i=0;i<file.length;i++){
                MultipartFile file1 = file[i];
                if(file1.getSize()/FileInfo.UNIT /FileInfo.UNIT<FileInfo.ASTRICT_LENGH) {
                    long time1 = new Date().getTime();
                    fileBack = attachFeignApi.uploadFile(FileInfo.VISIBILITYLEVEL, file1, userId);
                    logger.info(ConstClass.SUCCESS.FILE_RESULT + fileBack);
                    String code = (String) fileBack.get("code");
                    long time2 = new Date().getTime();
                    logger.info(FileInfo.TIME_COST + (time2 - time1) + "毫秒");
                    if (FileInfo.FILE_STATE.equals(code)) {
                        String filen = file1.getOriginalFilename();
                        logger.info(filen);
                        String filename = filen.substring(0, filen.lastIndexOf('.'));
                        String fileextension = filen.substring(filen.lastIndexOf('.') + 1, filen.length());
                        LinkedHashMap linkedHashMap = (LinkedHashMap) fileBack.get("data");
                        String fileId = (String) linkedHashMap.get(FileInfo.FILE_ID);
                        String httpUrl = (String) linkedHashMap.get(FileInfo.HTTP_URL);
                        fileMainVO.setFileId(fileId);
                        fileMainVO.setFileName(getFileName(filename));
                        fileMainVO.setFileExtension(fileextension);
                        fileMainVO.setFileHttpUrl(httpUrl);
                        fileMainVO.setFileUploadAppId(null);
                        fileMainVO.setFileUploadUserId(userId);

                        fileMainVO.setVisibilityLevel(FileInfo.VISIBILITYLEVEL);
                        fileMainVO.setFileLength(strToLong(file1));
                        fileMainVO.setFileUploadTime(new Date());
                        fileMainVO.setFileStatusUpdateTime(new Date());
                        fileMainVoMapper.insertFileMain(fileMainVO);

                        String shareid = UUID.randomUUID().toString().replaceAll("-", "") + new Random().nextLong();
                        FileShareVO fileShareVO = new FileShareVO();
                        fileShareVO.setFileShareBusinessKey(primaryKey);
                        fileShareVO.setFileId(fileId);
                        fileShareVO.setFileShareId(shareid);
                        fileShareVO.setFileShareType(shareType);
                        fileShareVO.setFileShareStatusUpdUserId(userId);
                        fileShareVO.setFileShareStatusUpdTime(new Date());
                        if(!StringUtils.isEmpty(processInstId)){
                            fileShareVO.setProcessInstId(processInstId);
                        }
                        fileShareVoMapper.insertFileShare(fileShareVO);
                        linkedHashMap.put("file_share_id", shareid);
                        list.add(linkedHashMap);
                    } else {
                        return new ProcessResult(ProcessResult.ERROR, ConstClass.FAILURE.INSERT_ERROR);
                    }
                }else {
                    return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FILE_ASTRICT);
                }
            }
            return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SUCCESS_MESS, list);
        }catch(Exception e){
            logger.info(ConstClass.FAILURE.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FAILURE_MESS);
        }
    }

    @Override
    public ProcessResult deleteFileCaseMain(@RequestBody FileShareVO fileShareVO) {
        int a = 0;
        try {
            if(StringUtils.isEmpty(fileShareVO.getFileShareId())){
                return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FILESHAREID_NOEXIST);
            }
            a = fileShareVoMapper.delectFileShare(fileShareVO);
            if(a>0) {
                return new ProcessResult(ProcessResult.SUCCESS, ConstClass.SUCCESS.SUCCESS_MESS, a);
            }else{
                return new ProcessResult(ProcessResult.SUCCESS, ConstClass.FAILURE.DELETE_NODONE,a);
            }
         }catch(Exception e){
            logger.info(ConstClass.FAILURE.OPERATION_EXCEPTION,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FAILURE_MESS);
        }

    }

    /**
     * 外网地址替换成内网地址
     * @param path
     * @return
     */
    private  String getPath(String path){
        String[] addrs=extranetIp.split(",");
        String[] urlIPs=intranetIp.split(",");
        for(int i=0;i<addrs.length;i++){
            if(path.contains(addrs[i])){
                path=path.replace(addrs[i],urlIPs[i]);
            }
        }
        return path;
    }

    private  String getFileName(HttpServletRequest request,String fileName)throws UnsupportedEncodingException{
        String userAgent = request.getHeader("User-Agent");
        // 针对IE或者以IE为内核的浏览器：
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            fileName = java.net.URLEncoder.encode(fileName, FileInfo.UTF_8);
        } else {
            // 非IE浏览器的处理：
            fileName = new String(fileName.getBytes(FileInfo.UTF_8), "ISO-8859-1");
        }
        return fileName;
    }

    @ResponseBody
    @RequestMapping(value = "/v1/file/filedown")
    public void filedown(HttpServletResponse response, HttpServletRequest request, String path , String fileName ) throws IOException {
        if (org.apache.commons.lang.StringUtils.isNotBlank(path) ){
            ServletOutputStream out = null;
            BufferedInputStream bis = null;
            InputStream fis = null;
            HttpURLConnection conn = null;

            logger.info("path:" + path + "extranetIp:" + extranetIp + "intranetIp:" + intranetIp);
            path = getPath(path);
            logger.info("repalacepath:" + path);
            response.reset();
            try {
            fileName = getFileName(request,fileName);
            response.setHeader("Content-disposition",String.format("attachment; filename=\"%s\"", fileName));

            // response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, FileInfo.UTF_8));
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Access-Control-Allow-Origin", "*");
            // response.setContentType("application/OCTET-STREAM");
            response.setContentType("application/msword");
            response.setCharacterEncoding(FileInfo.UTF_8);

            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(4 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 得到输入流
            //InputStream inputStream = conn.getInputStream();
            out = response.getOutputStream();
            //获取文件输入流
            fis = conn.getInputStream();

                request.setCharacterEncoding(FileInfo.UTF_8);
                int buffer = 1024*10;
                byte[] data = new byte[buffer];

                int read;
                bis = new BufferedInputStream(fis,buffer);
                while((read = bis.read(data)) != -1){
                    out.write(data, 0, read);
                }
                fis.close();
                bis.close();
            } catch (Exception e) {
                logger.error("文件下载异常", e);
            }finally{
                if (out != null) {
                    out.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
    }

    //长度大小的转换
    public static String strToLong(MultipartFile file) {
//        String sizes =null;
        Long size = file.getSize();
//        if(size/FileInfo.UNIT/FileInfo.UNIT>1){
//            sizes = size/ConstClass.UNIT/ConstClass.UNIT+"MB";
//        }else{
//            sizes = size/ConstClass.UNIT+"KB";
//        }
        return size+"";
    }

    public static String getFileName(String fileName) {
        int beginIndex = fileName.lastIndexOf('\\');
        if(beginIndex>-1){
            return fileName.substring(beginIndex+1);
        }
        return fileName;
    }

}
