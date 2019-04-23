package com.feifei.licai.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author cuishaofei
 * @date 2019/4/23
 */
public class DateTimeUtil {

    static Log logger = LogFactory.getLog(DateTimeUtil.class);

    /**
     * 把时间格式化成如：2002-08-03 08:26:16 格式的字符串
     */
    public final static String FMT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 把时间格式化成如：2002-08-03格式的字符串
     */
    public final static String FMT_YYYYMMDD = "yyyy-MM-dd";


    /**
     * 将给定的日期时间按操作系统默认的国家内格格式化成"yyyy-MM-dd HH:mm:ss"格式的日期时间串
     *
     * @param dateTime
     *            日期对象
     * @return 如果为null，返回""
     */
    public static String formatDateTimetoString(Date dateTime) {
        return formatDateTimetoString(dateTime, FMT_YYYYMMDDHHMMSS);
    }

    /**
     * 将给定的日期时间串按操作系统默认的国家内格格式化成"yyyy-MM-dd HH:mm:ss"格式的日期
     *
     * @param dateTime
     *            日期对象
     * @return 如果为null，返回""
     */
    public static Date formatStringtoDateTime(String dateTime) {
        return formatStringtoDateTime(dateTime, FMT_YYYYMMDDHHMMSS);
    }

    /**
     * 根据给出的Date值和格式串采用操作系统的默认所在的国家风格来格式化时间，并返回相应的字符串
     *
     * @param date
     *            日期对象
     * @param formatStr
     *            日期格式
     * @return 如果为null，返回字符串""
     */
    public static String formatDateTimetoString(Date date, String formatStr) {
        String reStr = "";
        if (date == null || formatStr == null || formatStr.trim().length() < 1) {
            return reStr;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(formatStr);
        reStr = sdf.format(date);
        return reStr == null ? "" : reStr;
    }

    /**
     * 根据给出的Date值和格式串采用操作系统的默认所在的国家风格来格式化时间，并返回相应的字符串
     *
     * @param date
     *            日期对象
     * @param formatStr
     *            日期格式
     * @return
     */
    public static Date formatStringtoDateTime(String date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        Date reStr = null;
        sdf.applyPattern(formatStr);
        try {
             reStr = sdf.parse(date);
        }catch (Exception e){
            logger.error("发生异常", e);
        }
        return reStr;
    }

    /**
     * 获取明年的1月1号
     * @return
     */
    public static Date getNextYearFirstDay(){
        String reStr = "";
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 1);
        c.set(Calendar.MONTH,0);
        c.set(Calendar.DATE,1);
        return c.getTime();
    }

}
