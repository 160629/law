package com.chinatower.product.legalms.common;
/**
 * 记录业务交易日志
 * @author hp
 *
 */
public class InterfaceLogUtil {

	private InterfaceLogUtil() {
		//
	}

	public static String reqTransLog(String body) {
		return new StringBuffer("REQ").append("#\n").append(body).toString();

	}

	public static String rspTransLog(String body) {
		return new StringBuffer("RSP").append("#\n").append(body).toString();
	}

}
