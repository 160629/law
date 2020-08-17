package com.chinatower.product.feignlocal;


import com.chinatower.encrypt.utils.Util;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class FeignInterceptorLocal implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger("TransLog");

    @Value("${file.privateKey}")
    private String privateKey;
    /**
     * 系统标识
     */
    private String sysCode = "CT00026";



    /**
     * 获取签名信息
     * @param timestamp
     * @param nonce
     * @return
     */
    private String getSign(long timestamp,String nonce){


        String sign = null ;
        try {
            sign = Util.creterSM2Signature(nonce, timestamp+"", sysCode, privateKey);
        } catch (Exception e) {
            logger.error("获取签名出错："+ e);
        }
        return sign;
    }

    /**
     * header add
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        long timestamp = System.currentTimeMillis();
        String nonce = String.valueOf(new Random().nextInt(10));
        String sign = getSign(timestamp, nonce);

        requestTemplate.header("signature",sign);
        requestTemplate.header("timestamp",timestamp+"");
        requestTemplate.header("nonce",nonce);
        requestTemplate.header("sysCode",sysCode);
        logger.info("请求头中参数是 signature "+sign);
        logger.info("请求头中参数是 timestamp "+timestamp);
        logger.info("请求头中参数是 nonce "+nonce);
        logger.info("请求头中参数是 sysCode "+sysCode);

        logger.info("20190906signature："+ sign);
        logger.info("20190906timestamp："+ timestamp);
        logger.info("20190906nonce："+ nonce);
        logger.info("20190906sysCode："+ sysCode);
    }

    /*public static void main(String[] args) {

        String nonce = String.valueOf(new Random().nextInt(10));
        String sysCode = "CT00026";
        String timestamp = System.currentTimeMillis()+"";
//        String privateKey = "04d357a05be321931cc2b4898f08731832fc85e7c23defda99269a69b5bf7579";

        String privateKey = "21277454384770a143e75240509dc597699135165c12b040306b1424d430572e";

        String sign = null ;
        try {
            sign = Util.creterSM2Signature(nonce, timestamp+"", sysCode, privateKey);
        } catch (Exception e) {
            logger.error("获取签名出错："+ e);
        }

        logger.info("signature："+ sign);
        logger.info("timestamp："+ timestamp);
        logger.info("nonce："+ nonce);
        logger.info("sysCode："+ sysCode);
    }*/
}
