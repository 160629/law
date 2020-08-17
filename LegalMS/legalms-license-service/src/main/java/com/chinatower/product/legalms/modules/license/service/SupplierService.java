package com.chinatower.product.legalms.modules.license.service;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.commen.ServerResponseOrg;
import com.chinatower.product.legalms.modules.license.entity.supplier.SupplierBeanVO;
import com.chinatower.product.legalms.modules.license.entity.supplier.response.SupplierBase;

/**
 * @Date: 2020/7/2 16:15
 * @Description:
 */
public interface SupplierService {


    public ServerResponseOrg recieveSupplierInfo(SupplierBeanVO supplierBeanVO);

    ProcessResult selectAllSupplierInfo(SupplierBase supplierBase);

    ProcessResult selectSupplierInfo(SupplierBase supplierBase);
}
