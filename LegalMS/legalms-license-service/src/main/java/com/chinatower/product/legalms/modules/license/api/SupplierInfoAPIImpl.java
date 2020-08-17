package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.common.ConstClass;
import com.chinatower.product.legalms.common.SystemInfo;
import com.chinatower.product.legalms.modules.license.commen.ResponseOrg;
import com.chinatower.product.legalms.modules.license.commen.ServerResponseOrg;
import com.chinatower.product.legalms.modules.license.entity.supplier.SupplierBeanVO;
import com.chinatower.product.legalms.modules.license.entity.supplier.response.SupplierBase;
import com.chinatower.product.legalms.modules.license.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Date: 2020/7/2 16:09
 * @Description:
 */
@RestController
public class SupplierInfoAPIImpl implements SupplierInfoAPI{

    private static final Logger logger = LoggerFactory.getLogger(SystemInfo.LOGGER_NAME);



    @Autowired
    private SupplierService supplierService;


    @Override
    public ServerResponseOrg recieveSupplierInfo(@RequestBody SupplierBeanVO supplierBeanVO) {
        try {
            return supplierService.recieveSupplierInfo(supplierBeanVO);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return ServerResponseOrg.error(ResponseOrg.SUP_ERROR,ConstClass.FAILURE.FIND_SUPPLIER_ERROR);
        }
    }

    @Override
    public ProcessResult selectAllSupplierInfo(@RequestBody SupplierBase supplierBase) {

        try {
            return supplierService.selectAllSupplierInfo(supplierBase);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FIND_SUPPLIER_ERROR);
        }
    }

    @Override
    public ProcessResult selectSupplierInfo(@RequestBody SupplierBase supplierBase) {
        try {
            return supplierService.selectSupplierInfo(supplierBase);
        } catch (Exception e) {
            logger.error(ProcessResult.ERROR,e);
            return new ProcessResult(ProcessResult.ERROR,ConstClass.FAILURE.FIND_SUPPLIER_ERROR);
        }
    }
}
