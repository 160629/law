package com.chinatower.product.legalms.utils;

import com.chinatower.framework.common_service.response.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 *
 * @author duanzj
 */
public class DateUtils {

    private DateUtils() {
        //
    }

    public  static final String DATE_PATTERN = "yyyy-MM-dd";
    public  static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public  static final String DATE_STR_PATTERN = "yyyyMMddHHmmss";
    public  static final String DATE_HHTIME_PATTERN = "yyyy-MM-dd HH:mm";
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static Date strToDate(String str) {
        return strToDate(str, DATE_PATTERN);
    }

    public static Date strToDate(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            logger.error(ProcessResult.ERROR, e);
        }
        return date;
    }

    public static String dateToStr(Date date,String pattern) {
        return format(date, pattern);
    }

    public static String format(Date date, String pattern) {
        try{
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }catch (Exception e){
            logger.error(ProcessResult.ERROR, e);
            return null;
        }
    }

    public static String dateStrToStr(String dateStr) {
        try{
            Date date=strToDate(dateStr,DATE_HHTIME_PATTERN);
            return dateToStr(date,DATE_STR_PATTERN);
        }catch (Exception e){
            logger.error(ProcessResult.ERROR, e);
            return null;
        }
    }



}
