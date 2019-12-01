package cosyfish.pro.common.util;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
    /**
     * SQL格式字符串
     */
    public final static String SQL_FORMART_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh24:mi:ss";
    /**
     * 格式字符串: "yyyy-MM-dd HH:mm:ss"
     */
    public final static String FORMART_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 格式字符串: "yyyy-MM-dd HH:mm:ss.S"
     */
    public final static String FORMART_YYYY_MM_DD_HH_MM_SS_MS = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 格式字符串: yyyy-MM-dd HH:mm
     */
    public final static String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /**
     * 格式字符串: yyyy-MM-dd
     */
    public final static String FORMART_YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 格式字符串: yyyyMMdd
     */
    public final static String FORMART_YYYYMMDD = "yyyyMMdd";
    /**
     * 格式字符串: dd/MM/yyyy
     */
    public final static String FORMART_DDMMYYYY = "dd/MM/yyyy";
    /**
     * 格式字符串: yyyyMMddHHmm
     */
    public final static String FORMART_YYYYMMDDHHMM = "yyyyMMddHHmm";
    /**
     * 格式字符串: yyyyMMddHHmmss
     */
    public final static String FORMART_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /**
     * 格式字符串: EEE MMM d HH:mm:ss Z yyyy
     */
    public final static String FORMART_EEE_MMM_DM_HH_MM_SS_Z_YYY = "EEE MMM d HH:mm:ss Z yyyy";
    /**
     * 格式字符: 时间部分格式"HH:mm:ss"
     */
    public final static String FORMART_HH_MM_SS = "HH:mm:ss";
    /**
     * 格式字符: 完整日期格式"yyyy-MM-dd HH:mm:ss"
     */
    public final static String DefaultDateTimeFormatPattern = FORMART_YYYY_MM_DD_HH_MM_SS;
    /**
     * 格式字符: 日期部分格式"yyyy-MM-dd"
     */
    public final static String DefaultDateFormatPattern = FORMART_YYYY_MM_DD;
    /**
     * 格式字符: 时间部分格式"HH:mm:ss"
     */
    public final static String DefaultTimeFormatPattern = FORMART_HH_MM_SS;
    /* - 日期格式化类 - */
    /**
     * 日期格式类: "yyyy-MM-dd HH:mm:ss"
     */
    public final static DateFormat DefaultDateTimeFormat = getDateFormart(FORMART_YYYY_MM_DD_HH_MM_SS);
    /**
     * 日期格式类: "yyyy-MM-dd HH:mm:ss.S"
     */
    public final static DateFormat DefaultDateTimeMSFormat = getDateFormart(FORMART_YYYY_MM_DD_HH_MM_SS_MS);
    /**
     * 日期格式类: "yyyyMMddHHmmss"
     */
    public final static DateFormat DefaultLogDateTimeFormat = getDateFormart(FORMART_YYYYMMDDHHMMSS);
    /**
     * 日期格式类: "yyyy-MM-dd"
     */
    public final static DateFormat DefaultDateFormat = getDateFormart(FORMART_YYYY_MM_DD);
    /**
     * 日期格式类: "HH:mm:ss"
     */
    public final static DateFormat DefaultTimeFormat = getDateFormart(FORMART_HH_MM_SS);
    /**
     * 日期格式类: "yyyyMMdd"
     */
    public final static DateFormat DefaultLogDateFormat = getDateFormart(FORMART_YYYYMMDD);

    public static DateFormat getDateFormart() {
        return getDateFormart(FORMART_YYYY_MM_DD_HH_MM_SS);
    }

    public static DateFormat getDateFormartYMD() {
        return getDateFormart(FORMART_YYYY_MM_DD);
    }

    /**
     * 将String(格式: yyyy-MM-dd)转Date
     *
     * @param dateStr 日期字符串
     * @return 日期对象
     */
    public static Date toDateFormYMD(String dateStr) {
        DateFormat df = getDateFormartYMD();
        Date da = null;
        try {
            da = df.parse(dateStr);
        } catch (ParseException e) {
            LOGGER.error("日期转换错误", e);
        }
        return da;
    }

    /**
     * 将String(格式: yyyy-MM-dd HH:mm:ss)转Date
     *
     * @param dateStr 日期字符串
     * @return 日期对象
     */
    public static Date toDate(String dateStr) {
        DateFormat df = getDateFormart();
        Date da = null;
        try {
            da = df.parse(dateStr);
        } catch (ParseException e) {
            LOGGER.error("日期转换错误", e);
        }
        return da;
    }

    /**
     * 将String转Date
     *
     * @param dateStr 日期字符串
     * @param format 指定日期格式字符串
     * @return 日期对象
     */
    public static Date toDate(String dateStr, String format) {
        DateFormat df = getDateFormart(format);
        Date da = null;
        try {
            da = df.parse(dateStr);
        } catch (ParseException e) {
            LOGGER.error("日期转换错误", e);
        }
        return da;
    }

    /**
     * 获得日期格式化对象
     *
     * @param format 指定日期格式字符串
     * @return 日期格式化对象
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
     * 将日期转换为String(格式: yyyy-MM-dd HH:mm:ss)
     */
    public static String toString(Date date) {
        return toString(date, FORMART_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 方法概要:将日期转换为String串
     */
    public static String toString(Date date, String sFmt) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(sFmt);
            String sRet = formatter.format(date).toString();
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
     * 增加指定月年数
     *
     * @return 日期对象
     */
    public static Date addYears(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + years);// 让日期加1
        date = calendar.getTime();
        return date;
    }

    /**
     * 增加指定月数
     *
     * @return 日期对象
     */
    public static Date addMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + months);// 让日期加1
        date = calendar.getTime();
        return date;
    }

    /**
     * 增加指定天数
     *
     * @return 日期对象
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);// 让日期加1
        date = calendar.getTime();
        return date;
    }

    /**
     * 增加指定小时数
     *
     * @return 日期对象
     */
    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hours);// 让日期加1
        date = calendar.getTime();
        return date;
    }

    /**
     * 增加指定分钟数
     *
     * @return 日期对象
     */
    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);// 让日期加1
        date = calendar.getTime();
        return date;
    }

    public static Date getDayWithFormat(Date date, String format) {
        Date today = DateUtils.toDate(DateUtils.toString(date, format), format);
        return today;
    }

    /**
     * 获取当前日期的0点
     *
     * @return 日期对象
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
     * 获取当前日期对应前一天的0点
     *
     * @return 日期对象
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
     * 获取当前日期对应下一天的0点
     *
     * @return 日期对象
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
     * 获取指定日期对应的0点
     *
     * @return 日期对象
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
     * 获取指定日期对应下一个月的第一天的0点
     *
     * @return 日期对象
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
     * 获取指定日期对应当月的第一天的0点
     *
     * @return 日期对象
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
     * 获取指定日期对应当月的最后一天的0点
     *
     * @return 日期对象
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
     * 获取指定日期对应下一周的周一的0点
     *
     * @return 日期对象
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
     * 获取指定日期对应本周的周一的0点
     *
     * @return 日期对象
     */
    public static Date getThisWeekFirstDateZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(getDateZeroTime(date));
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    /**
     * 获得2天后0点的日期对象
     *
     * @return 日期对象
     */
    public static Date getTheDateAfterTomorrowZero() {
        Calendar expiredCal = Calendar.getInstance();
        expiredCal.set(Calendar.HOUR_OF_DAY, 0);
        expiredCal.set(Calendar.SECOND, 0);
        expiredCal.set(Calendar.MINUTE, 0); // 确保在统计期间，不会过期
        expiredCal.set(Calendar.MILLISECOND, 0);
        expiredCal.add(Calendar.DATE, 2);

        return expiredCal.getTime();
    }

    /**
     * 获得指定日期的往后一天的0点的日期对象
     *
     * @param date 日期
     * @return 日期对象
     */
    public static Date getNextDateZero(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateZeroTime(date));
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 获得日期格式化字符串
     *
     * @param type 格式化类型<br>
     * 1 - yyyy-MM-dd HH:mm:ss(默认)<br>
     * 2 - yyyy-MM-dd<br>
     * 3 - HH:mm:ss<br>
     * 4 - yyyyMMddHHmmss<br>
     * 5 - yyyyMMdd<br>
     * 6 - yyyy-MM-dd HH:mm:ss.S<br>
     */
    public static String format(int type, Date date) {
        String dateStr = "";
        if (date != null) {
            if (type == 1) {
                dateStr = DefaultDateTimeFormat.format(date);
            } else if (type == 2) {
                dateStr = DefaultDateFormat.format(date);
            } else if (type == 3) {
                dateStr = DefaultTimeFormat.format(date);
            } else if (type == 4) {
                dateStr = DefaultLogDateTimeFormat.format(date);
            } else if (type == 5) {
                dateStr = DefaultLogDateFormat.format(date);
            } else if (type == 6) {
                dateStr = DefaultDateTimeMSFormat.format(date);
            } else {
                dateStr = DefaultDateTimeFormat.format(date);
            }
        }
        return dateStr;
    }

    /**
     * 获得Date日期部分
     *
     * @return 日期部分(格式 : yyyy - MM - dd)
     */
    public static String getDate(Date date) {
        if (date == null) {
            return null;
        } else {
            return DefaultDateFormat.format(date);
        }
    }

    /**
     * 获得Date时间部分
     *
     * @return 时间部分(格式 : HH : mm : ss)
     */
    public static String getTime(Date date) {
        if (date == null) {
            return null;
        } else {
            return DefaultTimeFormat.format(date);
        }
    }

    /**
     * 将日期和时间字符拼装并返回对应日期
     *
     * @param dateStr 日期部分(格式: yyyy-MM-dd)
     * @param timeStr 时间部分(格式: HH:mm:ss)
     * @return 日期
     */
    public static Date toDateTime(String dateStr, String timeStr) {
        try {
            return DefaultDateTimeFormat.parse(dateStr + " " + timeStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 计算两个日期的差值<br>
     * 默认第一个日期大于第二个日期
     */
    public static String calDateDiffVal(Date date1, Date date2) {
        long durationMillis = org.apache.commons.lang3.time.DateUtils.getFragmentInMilliseconds(date1, Calendar.YEAR);
        long durationMillis2 = org.apache.commons.lang3.time.DateUtils.getFragmentInMilliseconds(date2, Calendar.YEAR);
        return DurationFormatUtils.formatPeriod(durationMillis, durationMillis2, DefaultDateTimeFormatPattern);
    }

    public static Integer calDateDiffDay(Date date1, Date date2) {
        Integer diffDay = 0;
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        diffDay = calendar1.get(Calendar.DAY_OF_YEAR) - calendar2.get(Calendar.DAY_OF_YEAR);
        return diffDay;
    }

    /**
     * 将web请求的日期字符串安静的转换成日期(不抛异常,异常则返回为空)<br>
     * 主要针对格式: <br>
     * yyyy-MM-dd HH:mm:ss.S <br>
     * yyyy-MM-dd HH:mm:ss<br>
     * yyyy-MM-dd HH:mm <br>
     * yyyy-MM-dd HH:mm<br>
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parseWebDateQuietly(String dateStr) {
        Date _date = null;
        try {
            _date = org.apache.commons.lang3.time.DateUtils.parseDate(dateStr, FORMART_YYYY_MM_DD_HH_MM_SS_MS, FORMART_YYYY_MM_DD_HH_MM_SS,
                    FORMAT_YYYY_MM_DD_HH_MM, FORMART_YYYY_MM_DD);
        } catch (Exception e) {
            _date = null;
        }
        return _date;
    }
}
