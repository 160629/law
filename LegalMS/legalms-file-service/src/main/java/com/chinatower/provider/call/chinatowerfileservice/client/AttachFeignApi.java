package com.chinatower.provider.call.chinatowerfileservice.client;
//      com.chinatower.provider.call.*
//      com.chinatower.provider.call.yyy.client



import com.chinatower.product.legalms.config.MultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 *
 * @description 上传、下载附件管理
 */
@FeignClient(url = "${system.custom.attachUrl}",name = "attach", configuration = MultipartSupportConfig.class)
public interface AttachFeignApi {

    /**
     * 附件上传
     *
     * @param visibilityLevel
     * @param file
     * @param userId
     * @return
     */
    @PostMapping(value = "/ChinatowerFileService/uploadFile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String,Object> uploadFile(@RequestParam("visibilityLevel") String visibilityLevel,
                                         @RequestPart("file") MultipartFile file,
                                         @RequestParam("userId") String userId);

    /**
     * 附件下载
     *
     * @param fileId
     * @param userId
     * @return
     */
    @GetMapping(value = "/ChinatowerFileService/dowloadFile")
    public String dowloadFile(@RequestParam("fileId") String fileId,
                              @RequestParam("userId") String userId);

    /**
     * 批量下载
     *
     * @param fileIds
     * @param userId
     * @return
     */
    @GetMapping(value = "/ChinatowerFileService/batchDowloadFile")
    public String batchDowloadFile(@RequestParam("fileIds") String fileIds,
                                   @RequestParam("userId") String userId);

    /**
     * 附件删除
     *
     * @param fileId
     * @param userId
     * @return
     */
    @PostMapping(value = "/ChinatowerFileService/deleteFile")
    public String deleteFile(@RequestParam("fileId") String fileId,
                             @RequestParam("userId") String userId);

    /**
     * 附件更新
     *
     * @param fileId
     * @param visibilityLevel
     * @param userId
     * @param file
     * @return
     */
    @PostMapping(value = "/ChinatowerFileService/updateFile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  Map<String,Object> updateFile(@RequestParam("fileId") String fileId,
                                          @RequestParam("visibilityLevel") String visibilityLevel,
                                          @RequestParam("userId") String userId,
                                          @RequestPart("file") MultipartFile file);

}
