package com.chinatower.product.legalms.modules.license.api;

import com.chinatower.framework.common_service.response.ProcessResult;
import com.chinatower.product.legalms.modules.license.commen.ServerResponseOrg;
import com.chinatower.product.legalms.modules.license.entity.supplier.SupplierBeanVO;
import com.chinatower.product.legalms.modules.license.entity.supplier.response.SupplierBase;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Date: 2020/7/2 16:03
 * @Description:
 */
public interface SupplierInfoAPI {

    /**
     * 新增或修改供应商
     *
     */
    @PostMapping("/v1/supplier/recieveSupplierInfo")
    public ServerResponseOrg recieveSupplierInfo(SupplierBeanVO supplierBeanVO);

    /**
     * 功能描述: 查询供应商详细信息（包括从表）
     * @auther: guodong
     * @param
     * @return
     * @date: 2020/7/15 16:43
     */
    @PostMapping("/v1/supplier/selectAllSupplierInfo")
    public ProcessResult selectAllSupplierInfo(SupplierBase supplierBase);

    /**
     * 功能描述:查询供应商主表信息
     * @auther: guodong
     * @param supplierBase
     * @return com.chinatower.framework.common_service.response.ProcessResult
     * @date: 2020/7/15 16:44
     */
    @PostMapping("/v1/supplier/selectSupplierInfo")
    public ProcessResult selectSupplierInfo(SupplierBase supplierBase);
}
