package com.chinatower.product.legalms.modules.license.mapper;

import com.chinatower.product.legalms.modules.license.entity.supplier.SupplierBankVO;
import com.chinatower.product.legalms.modules.license.entity.supplier.SupplierBaseVO;
import com.chinatower.product.legalms.modules.license.entity.supplier.SupplierCertificateVO;
import com.chinatower.product.legalms.modules.license.entity.supplier.SupplierContactVO;
import com.chinatower.product.legalms.modules.license.entity.supplier.response.SupplierBase;

import java.util.List;

/**
 * @Date: 2020/7/2 16:37
 * @Description:
 */
public interface SupplierMapper {


    public SupplierBaseVO selectSupplierInfoByCode(String supplierCode);

    public List<SupplierBaseVO> selectAllSupplierInfo(SupplierBase supplierBase);

    public void updateSupplierBaseInfo(SupplierBaseVO supplierBaseVO);

    public void updateContactInfo(SupplierContactVO contactVO);

    public void updateBankInfo(SupplierBankVO bankVO);

    public void updateCertificateInfo(SupplierCertificateVO certificateVO);

    public void insertSupplierBaseInfo(SupplierBaseVO supplierBaseVO);

    public void insertContactInfo(SupplierContactVO contactVO);

    public void insertBankInfo(SupplierBankVO bankVO);

    public void insertCertificateInfo(SupplierCertificateVO certificateVO);

    List<SupplierBaseVO> selectSupplierInfoList(SupplierBase supplierBase);
}
