package com.chinatower.product.legalms.modules.license.service.impl;

import com.alibaba.fastjson.JSON;
import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.InterfaceLogUtil;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.exception.MyOwnRuntimeException;
import com.chinatower.product.legalms.modules.license.commen.ResponseOrg;
import com.chinatower.product.legalms.modules.license.commen.ServerResponseOrg;
import com.chinatower.product.legalms.modules.license.entity.supplier.*;
import com.chinatower.product.legalms.modules.license.entity.supplier.response.SupplierBase;
import com.chinatower.product.legalms.modules.license.mapper.SupplierMapper;
import com.chinatower.product.legalms.modules.license.service.SupplierService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Date: 2020/7/2 16:34
 * @Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
@SuppressWarnings("All")
public class SupplierServiceImpl implements SupplierService {

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public ServerResponseOrg recieveSupplierInfo(SupplierBeanVO supplierBeanVO) {
        logger.info(InterfaceLogUtil.reqTransLog(SystemInfo.REQUEST_PARAM + JSON.toJSONString(supplierBeanVO)));
        String flag = supplierBeanVO.getFlag();
        List<SupplierBaseVO> listJson = supplierBeanVO.getListJson();
        for (int i = 0; i < listJson.size(); i++) {
            SupplierBaseVO supplierBaseVO = listJson.get(i);
            String supplierCode = supplierBaseVO.getSupplierCode();
            if(StringUtils.isBlank(supplierCode)){
             return ServerResponseOrg.error(ResponseOrg.SUP_ERROR,"校验为空");
            }
            SupplierBaseVO supplierBase =  supplierMapper.selectSupplierInfoByCode(supplierCode);
           if(supplierBase !=null){
               if(SystemInfo.INSERT_ID.equals(flag)||SystemInfo.UPDATE_ID.equals(flag)){
                   buildUpdateInfo(supplierBaseVO, supplierBase,listJson.size());

               }
           }else {
               if(SystemInfo.INSERT_ID.equals(flag)||SystemInfo.UPDATE_ID.equals(flag)){
                   buildInsertInfo(supplierBaseVO, supplierCode,listJson.size());

               }
           }
        }
        return ServerResponseOrg.success(ResponseOrg.SUP_SUCCESS);
    }

    @Override
    public ProcessResult selectAllSupplierInfo(SupplierBase supplierBase) {
        List<SupplierBaseVO> list = supplierMapper.selectAllSupplierInfo(supplierBase);
        PageInfo<SupplierBaseVO> pageInfo = new PageInfo<>(list);
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", pageInfo);
    }

    @Override
    public ProcessResult selectSupplierInfo(SupplierBase supplierBase) {
        List<SupplierBaseVO> list = supplierMapper.selectSupplierInfoList(supplierBase);
        PageInfo<SupplierBaseVO> pageInfo = new PageInfo<>(list);
        return new ProcessResult(ProcessResult.SUCCESS, "查询成功", pageInfo);
    }

    private ServerResponseOrg buildInsertInfo(SupplierBaseVO supplierBaseVO, String supplierCode,int size) {
        supplierBaseVO.setSupBaseId(supplierCode);
        //插入供应商主表信息
        supplierMapper.insertSupplierBaseInfo(supplierBaseVO);
        logger.info("插入供应商主表成功:{}",size);
        List<SupplierContactVO> contactInfo = supplierBaseVO.getSupplierContactInfo();
        List<SupplierBankVO> bankInfo = supplierBaseVO.getSupplierBankInfo();
        List<SupplierCertificateVO> certificateInfo = supplierBaseVO.getSupplierCertificateInfo();
        if(!contactInfo.isEmpty()){
            for (int j = 0; j < contactInfo.size(); j++) {
                SupplierContactVO contactVO = contactInfo.get(j);
                String contactChildId = contactVO.getContactChildId();
                if(StringUtils.isBlank(contactChildId)){
                    throw new MyOwnRuntimeException("校验为空");
                    //return ServerResponseOrg.error(ResponseOrg.SUP_ERROR,"校验为空");
                }
                contactVO.setSupBaseId(supplierCode);
                supplierMapper.insertContactInfo(contactVO);
            }
        }
        logger.info("插入联系人从表成功:{}",contactInfo.size());
        if(!bankInfo.isEmpty()){
            for (int j = 0; j < bankInfo.size(); j++) {
                SupplierBankVO bankVO = bankInfo.get(j);
                String bankChildId = bankVO.getBankChildId();
                if(StringUtils.isBlank(bankChildId)){
                    throw new MyOwnRuntimeException("校验为空");
                    //return ServerResponseOrg.error(ResponseOrg.SUP_ERROR,"校验为空");
                }
                bankVO.setSupBaseId(supplierCode);
                supplierMapper.insertBankInfo(bankVO);

            }
        }
        logger.info("插入银行从表成功:{}",bankInfo.size());
        if(!certificateInfo.isEmpty()){
            for (int j = 0; j < certificateInfo.size(); j++) {
                SupplierCertificateVO certificateVO = certificateInfo.get(j);
                certificateVO.setSupBaseId(supplierCode);
                String childId = certificateVO.getCertificateChildId();
                if(StringUtils.isBlank(childId)){
                    throw new MyOwnRuntimeException("校验为空");
                    //return ServerResponseOrg.error(ResponseOrg.SUP_ERROR,"校验为空");
                }
                supplierMapper.insertCertificateInfo(certificateVO);
            }
        }
        logger.info("插入证书从表成功：{}",certificateInfo.size());
        return null;
    }

    private ServerResponseOrg buildUpdateInfo(SupplierBaseVO supplierBaseVO, SupplierBaseVO supplierBase,int size) {
        supplierBaseVO.setSupBaseId(supplierBase.getSupBaseId());
        //更新供应商主表信息
        supplierMapper.updateSupplierBaseInfo(supplierBaseVO);
        logger.info("更新供应商主表成功:{}",size);
        List<SupplierContactVO> contactInfo = supplierBaseVO.getSupplierContactInfo();
        List<SupplierBankVO> bankInfo = supplierBaseVO.getSupplierBankInfo();
        List<SupplierCertificateVO> certificateInfo = supplierBaseVO.getSupplierCertificateInfo();
        if(!contactInfo.isEmpty()){
            for (int j = 0; j < contactInfo.size(); j++) {
                SupplierContactVO contactVO = contactInfo.get(j);
                contactVO.setSupBaseId(supplierBase.getSupBaseId());
                String contactChildId = contactVO.getContactChildId();
                if(StringUtils.isBlank(contactChildId)){
                    throw new MyOwnRuntimeException("校验为空");
                    //return ServerResponseOrg.error(ResponseOrg.SUP_ERROR,"校验为空");

                }
                supplierMapper.updateContactInfo(contactVO);
            }
        }
        logger.info("更新联系人表成功:{}",size);
        if(!bankInfo.isEmpty()){
            for (int j = 0; j < bankInfo.size(); j++) {
                SupplierBankVO bankVO = bankInfo.get(j);
                String bankChildId = bankVO.getBankChildId();
                if(StringUtils.isBlank(bankChildId)){
                    throw new MyOwnRuntimeException("校验为空");
                   // return ServerResponseOrg.error(ResponseOrg.SUP_ERROR,"校验为空");
                }
                bankVO.setSupBaseId(supplierBase.getSupBaseId());
                supplierMapper.updateBankInfo(bankVO);

            }
        }
        logger.info("更新银行从表成功:{}",size);
        if(!certificateInfo.isEmpty()){
            for (int j = 0; j < certificateInfo.size(); j++) {
                SupplierCertificateVO certificateVO = certificateInfo.get(j);
                String childId = certificateVO.getCertificateChildId();
                if(StringUtils.isBlank(childId)){
                    throw new MyOwnRuntimeException("校验为空");
                    //return ServerResponseOrg.error(ResponseOrg.SUP_ERROR,"校验为空");
                }
                certificateVO.setSupBaseId(supplierBase.getSupBaseId());
                supplierMapper.updateCertificateInfo(certificateVO);
            }
        }
        logger.info("更新证书从表成功:{}",size);
        return null;
    }

}
