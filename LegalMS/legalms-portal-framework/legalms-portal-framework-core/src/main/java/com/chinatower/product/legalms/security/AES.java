package com.chinatower.product.legalms.security;


import static com.chinatower.product.legalms.utils.ByteUtil.base64Decode;
import static com.chinatower.product.legalms.utils.ByteUtil.base64Encode;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinatower.framework.common_service.response.ProcessResult;


/**
 * 文件描述: 编码工具类
 * <p>
 * 其他说明: 实现aes加密、解密
 * <p>
 * 日期：2018/12/11 0011
 *
 * @author lw
 */
public class AES {
	private static final Logger log = LoggerFactory.getLogger(AES.class);

    private AES() {
     //无用
    }
    //密钥
    private static final String KEY = "fawu_portal&lega";


    //算法
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";
    
    private static final String UTF_8 = "utf-8";

    private static final String AES_CODE = "AES";

    /**
     * aes解密
     *
     * @param encrypt 内容
     * @return
     * @throws Exception
     */
    public static String decrypt(String encrypt) {
        return decrypt(encrypt, KEY);
    }

    /**
     * aes加密
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String encrypt(String content) {
        return encrypt(content, KEY);
    }

    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    private static byte[] encryptToBytes(String content, String encryptKey){
        byte[] doFinal = null;
        try {
            KeyGenerator.getInstance(AES_CODE).init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), AES_CODE));
            doFinal = cipher.doFinal(content.getBytes(UTF_8));
        } catch (UnsupportedEncodingException|InvalidKeyException|NoSuchAlgorithmException|NoSuchPaddingException|BadPaddingException|IllegalBlockSizeException e) {
            log.error(ProcessResult.ERROR,e);
        }
        return doFinal;
    }

    /**
     * AES加密为base 64 code
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    private static String encrypt(String content, String encryptKey){
        return base64Encode(encryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    private static String decryptByBytes(byte[] encryptBytes, String decryptKey){
        String result = "";
        try {
           KeyGenerator.getInstance(AES_CODE).init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), AES_CODE));
            result = new String(cipher.doFinal(encryptBytes), UTF_8);
        } catch (UnsupportedEncodingException|InvalidKeyException|NoSuchAlgorithmException|BadPaddingException|IllegalBlockSizeException|NoSuchPaddingException e) {
            log.error(ProcessResult.ERROR,e);
        }
        return result;
    }

    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    private static String decrypt(String encryptStr, String decryptKey){
        return StringUtils.isEmpty(encryptStr) ? null : decryptByBytes(base64Decode(encryptStr), decryptKey);
    }
}
