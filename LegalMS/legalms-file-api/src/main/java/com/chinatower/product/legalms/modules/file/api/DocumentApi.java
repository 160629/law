package com.chinatower.product.legalms.modules.file.api;

import com.chinatower.product.legalms.modules.filecommon.vo.filecommon.FileShareVO;
import com.chinatower.framework.common_service.response.ProcessResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface DocumentApi {

    @GetMapping("/file/selectFile")
    public ProcessResult selectFile(@RequestParam("fileShareBusinessKey")String fileShareBusinessKey,
                                    @RequestParam(value = "shareType",required = false)String shareType,
                                    @RequestParam(value = "processInstId",required = false)  String processInstId);

    /*@PostMapping("/file/uploadFile")
    public ProcessResult uploadFile(@RequestParam("visibilityLevel") String visibilityLevel,
                                    @RequestParam("file") MultipartFile  file,
                                    @RequestParam("userId") String userId,
                                    @RequestParam("fileSharebusinessKey") String fileSharebusinessKey,
                                    @RequestParam("fileType") int fileType,
                                    @RequestParam("fileShareType") String fileShareType,
                                    @RequestParam("userId") String fileStatus);*/

    /**
     * 附件下载
     */
    @GetMapping(value = "/file/dowloadFile")
    public ProcessResult dowloadFile(@RequestParam("fileId") String fileId,
                                     @RequestParam("userId") String userId);

    /**
     * 批量下载
     */
    @GetMapping(value = "/file/batchDowloadFile")
    public ProcessResult batchDowloadFile(@RequestParam("fileIds") String fileIds,
                                          @RequestParam("userId") String userId);

    /**
     * 附件删除
     */
    /*@PostMapping(value = "/file/deleteFile")
    public ProcessResult deleteFile(@RequestParam("fileId") String fileId,
                                    @RequestParam("userId") String userId,
                                    @RequestBody FileMainVO fileMainVO);*/

    /**
     * 附件更新
     */
    @PostMapping(value = "/file/updateFile")
    public ProcessResult updateFile(@RequestParam("fileId") String fileId,
                                    @RequestParam("visibilityLevel") String visibilityLevel,
                                    @RequestParam("userId") String userId,
                                    @RequestPart("file") MultipartFile file);

    /**
     * 附件上传
     */
    /*@PostMapping("/v1/file/uploadFileMain")
    public ProcessResult uploadFileMain(@RequestParam("visibilityLevel") String visibilityLevel,
                                    @RequestParam("file") MultipartFile  file,
                                    @RequestParam("userId") String userId
                                   );*/

    /**
     * 附件上传
     */
    @PostMapping("/v1/file/uploadFileMain")
    public ProcessResult uploadFileMain( @RequestParam("file") MultipartFile[]  file);

    /**
     * 附件上传 附带绑定业务主键
     * @param file
     * @param primaryKey
     * @param shareType
     * @return
     */
    @PostMapping("/v1/file/uploadFileShare")
    public ProcessResult uploadFileShare(@RequestParam("file")  MultipartFile[] file,
                                         @RequestParam("primaryKey") String primaryKey,
                                         @RequestParam("shareType")  String shareType,
                                         @RequestParam(value = "processInstId",required = false)  String processInstId);

    /**
     * 附件统一删除接口
     */
    @PostMapping("/v1/file/deleteFileCaseMain")
    public ProcessResult deleteFileCaseMain(@RequestBody FileShareVO fileShareVO );

    @RequestMapping(value = "/v1/file/filedown")
    public void filedown(HttpServletResponse response, HttpServletRequest request, String path , String fileName )throws IOException;
}