package com.chinatower.product.legalms.modules.filecommon.service.filecommon.impl;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.filecommon.mapper.filecommon.FileMainMapper;
import com.chinatower.product.legalms.modules.filecommon.mapper.filecommon.FileShareMapper;
import com.chinatower.product.legalms.modules.filecommon.service.filecommon.FilecommonService;
import com.chinatower.product.legalms.modules.filecommon.vo.filecommon.FileShareVO;
import com.chinatower.product.legalms.modules.filecommon.vo.filecommon.Filelist;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilecommonServiceImpl implements FilecommonService {
    private static final Logger logger = LoggerFactory.getLogger(FilecommonServiceImpl.class);
    @Autowired
    FileMainMapper fileMainMapper;
    @Autowired
    FileShareMapper fileShareMapper;

    @Override
    /**
     * String addId; 新增的shareid需要和 primaryKey 和 shareType作绑定存到库里去
      String delId; 直接可以把shareid删除
      String shareType;
     */
    public ProcessResult updateFile(List<Filelist> filelist,UserInfo userInfo,String primaryKey) {
        try {
            //所有需要删除的id的字符串
            StringBuilder delIdStr = new StringBuilder();
            //所有新增的share对象list
            List<FileShareVO> parlist = new ArrayList<>();
            if(!filelist.isEmpty()){
                for(int i=0;i<filelist.size();i++){
                    Filelist files = filelist.get(i);
                    if(StringUtils.isNotBlank(files.getDelId())){
                        delIdStr = delIdStr.append(files.getDelId()).append(",");
                    }
                    //直接初始化新增的对象
                    List<FileShareVO> sonlist = dealWithAdd(files.getAddId(),files.getShareType(),primaryKey,userInfo);
                    parlist.addAll(sonlist);
                }
            }
            if(!parlist.isEmpty()){
                fileShareMapper.addshareVos(parlist);
            }
            if(StringUtils.isNotBlank(delIdStr.toString())){
                String[] delIdS = delIdStr.substring(1,(delIdStr.length()-1)).split(",");
                fileShareMapper.delectShareArr(delIdS);
            }
            return new ProcessResult(ProcessResult.SUCCESS,"处理成功");
        }catch (Exception e){
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,"处理失败");
        }
    }

    private List<FileShareVO> dealWithAdd(String addId, String shareType, String primaryKey,UserInfo userInfo) {
        List<FileShareVO> sonlistnew = new ArrayList<>();
        if(StringUtils.isNotBlank(addId)){
            String[] addIdArr = addId.split(",");
            for (int i=0;i<addIdArr.length;i++){
                if(StringUtils.isNotBlank(addIdArr[i])){
                    FileShareVO fileShareVO = new FileShareVO();
                    fileShareVO.setFileShareBusinessKey(primaryKey);
                    String[] tempArr = addIdArr[i].split("#");
                    if(tempArr.length>1){
                        fileShareVO.setFileId(tempArr[0]);
                        fileShareVO.setFileShareId(tempArr[1]);
                    }else{
                        continue;
                    }
                    fileShareVO.setFileShareType(shareType);
                    fileShareVO.setFileShareStatusUpdUserId(userInfo.getLoginAcct());
                    fileShareVO.setFileShareStatusUpdTime(new Date());
                    sonlistnew.add(fileShareVO);
                }
            }
        }
        return sonlistnew;
    }

    @Override
    public Map<String, Object> test() {
        return null;
    }

}
