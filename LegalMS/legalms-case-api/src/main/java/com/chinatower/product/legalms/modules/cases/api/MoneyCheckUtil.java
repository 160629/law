package com.chinatower.product.legalms.modules.cases.api;

import org.apache.commons.lang.StringUtils;

public class MoneyCheckUtil {

    private MoneyCheckUtil(){

    }

    public static String checkMoneyIsEmpty(String moneyField){
        if (StringUtils.isBlank(moneyField)) {
            return "0";
        }
        return moneyField;
    }

}
