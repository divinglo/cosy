package cosyfish.pro.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCoverd {
    public final static String FORMART_YYYY_MM_DD = "yyyy-MM-dd";

    public final static String FORMART_DDMMYYYY = "dd/MM/yyyy";

    public final static String FORMART_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public final static String SQL_FORMART_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh24:mi:ss";

    public final static String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public final static String FORMART_YYYYMMDDHHMM = "yyyyMMddHHmm";

    public final static String FORMART_EEE_MMM_DM_HH_MM_SS_Z_YYY = "EEE MMM d HH:mm:ss Z yyyy";

    public static DateFormat getDateFormart() {
        return getDateFormart(FORMART_YYYY_MM_DD_HH_MM_SS);
    }

    public static DateFormat getDateFormartYMD() {
        return getDateFormart(FORMART_YYYY_MM_DD);
    }

    private static Logger logger = LoggerFactory.getLogger(DateCoverd.class);

    /**
     * 方法概要:将String转Date
     */
    public static Date toDateFormYMD(String date) {
        DateFormat df = getDateFormartYMD();
        Date da = null;
        try {
            da = df.parse(date);
        } catch (ParseException e) {
            logger.error("日期转换错误", e);
        }
        return da;
    }

    public static Date toDate(String date) {
        DateFormat df = getDateFormart();
        Date da = null;
        try {
            da = df.parse(date);
        } catch (ParseException e) {
            logger.error("日期转换错误", e);
        }
        return da;
    }

    /**
     * 方法概要:将String转Date
     */
    public static Date toDate(String date, String format) {
        DateFormat df = getDateFormart(format);
        Date da = null;
        try {
            da = df.parse(date);
        } catch (ParseException e) {
            logger.error("日期转换错误", e);
        }
        return da;
    }

    /**
     * 方法概要:获取格式信息
     */
    public static DateFormat getDateFormart(String format) {
        DateFormat df = null;
        if (format == null || format.trim().equals("")) {
            df = new SimpleDateFormat(FORMART_YYYY_MM_DD_HH_MM_SS);
        } else {
            df = new SimpleDateFormat(format);
        }
        return df;
    }

    /**
     * 方法概要:将日期转换为String串
     */
    public static String toString(Date dt, String sFmt) {
        if (dt == null) {
            return "";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(sFmt);
            String sRet = formatter.format(dt).toString();
            return sRet;
        }
    }

    public static String toString(String date, String sFmt) {
        if (date == null) {
            return "";
        } else {
            Date dt = toDateFormYMD(date);
            SimpleDateFormat formatter = new SimpleDateFormat(sFmt);
            String sRet = formatter.format(dt).toString();
            return sRet;
        }
    }

    /**
     * 方法概要:增加年
     */
    public static Date addYears(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + years);// 让日期加1
        date = calendar.getTime();
        return date;
    }

    /**
     * 方法概要:增加月
     */
    public static Date addMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + months);// 让日期加1
        date = calendar.getTime();
        return date;
    }

    /**
     * 方法概要:增加日期
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);// 让日期加1
        date = calendar.getTime();
        return date;
    }

    /**
     * 方法概要:增加小时
     */
    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hours);// 让日期加1
        date = calendar.getTime();
        return date;
    }

    /**
     * 方法概要:增加分钟
     */
    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);// 让日期加1
        date = calendar.getTime();
        return date;
    }

    /**
     * 方法概要:将日期转换为String串
     */
    public static String toString(Date dt) {
        return toString(dt, FORMART_YYYY_MM_DD_HH_MM_SS);
    }

    public static Date getDayWithFormat(Date date, String format) {
        Date today = DateCoverd.toDate(DateCoverd.toString(date, format), format);
        return today;
    }

    /**
     * @return 当前时间的下一天的0点
     */
    public static Date getTomorrowZeroTime() {
        Calendar expiredCal = Calendar.getInstance();
        expiredCal.set(Calendar.HOUR_OF_DAY, 0);
        expiredCal.set(Calendar.SECOND, 0);
        expiredCal.set(Calendar.MINUTE, 0);
        expiredCal.set(Calendar.MILLISECOND, 0);
        expiredCal.add(Calendar.DATE, 1);

        return expiredCal.getTime();
    }

    /**
     * @return 当前时间的当天的0点
     */
    public static Date getTodayZeroTime() {
        Calendar expiredCal = Calendar.getInstance();
        expiredCal.set(Calendar.HOUR_OF_DAY, 0);
        expiredCal.set(Calendar.SECOND, 0);
        expiredCal.set(Calendar.MINUTE, 0);
        expiredCal.set(Calendar.MILLISECOND, 0);

        return expiredCal.getTime();
    }

    /**
     * @return 当前时间的前一天的0点
     */
    public static Date getYesterdayZeroTime() {
        Calendar expiredCal = Calendar.getInstance();
        expiredCal.set(Calendar.HOUR_OF_DAY, 0);
        expiredCal.set(Calendar.SECOND, 0);
        expiredCal.set(Calendar.MINUTE, 0);
        expiredCal.set(Calendar.MILLISECOND, 0);
        expiredCal.add(Calendar.DATE, -1);

        return expiredCal.getTime();
    }

    /**
     * 获取传入date的0点
     */
    public static Date getDateZeroTime(Date date) {
        Calendar expiredCal = Calendar.getInstance();
        expiredCal.setTime(date);
        expiredCal.set(Calendar.HOUR_OF_DAY, 0);
        expiredCal.set(Calendar.SECOND, 0);
        expiredCal.set(Calendar.MINUTE, 0);
        expiredCal.set(Calendar.MILLISECOND, 0);

        return expiredCal.getTime();
    }

    /**
     * 获取传入date的下一个月的第一天的0点
     */
    public static Date getNextMonthFirstDateZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateZeroTime(date));
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取传入date的当月的第一天的0点
     */
    public static Date getThisMonthFirstDateZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateZeroTime(date));
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取传入date的当月的最后一天的0点
     */
    public static Date getThisMonthLastDateZero(Date date) {
        // 下个月1号的前一天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateZeroTime(date));
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 获取传入date的下一周的周一的0点
     */
    public static Date getNextWeekFirstDateZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(getDateZeroTime(date));
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Saturday
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 获取传入date的本周的周一的0点
     */
    public static Date getThisWeekFirstDateZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(getDateZeroTime(date));
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    public static Date getTheDateAfterTomorrowZero() {
        Calendar expiredCal = Calendar.getInstance();
        expiredCal.set(Calendar.HOUR_OF_DAY, 0);
        expiredCal.set(Calendar.SECOND, 0);
        expiredCal.set(Calendar.MINUTE, 0); // 确保在统计期间，不会过期
        expiredCal.set(Calendar.MILLISECOND, 0);
        expiredCal.add(Calendar.DATE, 2);

        return expiredCal.getTime();
    }

    public static Date getNextDateZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateZeroTime(date));
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
}
