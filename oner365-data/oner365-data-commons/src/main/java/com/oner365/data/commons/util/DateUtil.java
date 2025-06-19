package com.oner365.data.commons.util;

import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：时间处理工具类
 *
 * @author zhaoyong
 *
 * mobile enterprise application platform Version 0.1
 */
public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 毫秒
     */
    public static final int MILLI_SECOND = 1000;

    /**
     * 秒
     */
    public static final int SECOND = 1;

    /**
     * 分的秒数
     */
    public static final int MINUTE_SECONDS = SECOND * 60;

    /**
     * 小时的秒数
     */
    public static final int HOUR_SECONDS = MINUTE_SECONDS * 60;

    /**
     * 天的小时
     */
    public static final int DAY_HOUR = 24;

    /**
     * 天的秒数
     */
    public static final int DAY_SECONDS = HOUR_SECONDS * DAY_HOUR;

    /**
     * 年的秒数
     */
    public static final int YEAR_SECONDS = DAY_SECONDS * 365;

    /**
     * 最大 小时
     */
    public static final int MAX_HOUR = 23;

    /**
     * 最大 分
     */
    public static final int MAX_MINUTE = 59;

    /**
     * 最大 秒
     */
    public static final int MAX_SECOND = 59;

    /**
     * 东8区
     */
    public static final String DATE_TIMEZONE = "GMT+8";

    /**
     * 格式：年－月－日 小时：分钟：秒
     */
    public static final String FULL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式：年－月－日 小时：分钟
     */
    public static final String FULL_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 格式：年－月－日
     */
    public static final String FULL_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 格式：年－月－日 小时:分钟:秒:毫秒
     */
    public static final String FULL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";

    /**
     * UTC时间
     */
    public static final String FULL_UTC_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";

    /**
     * 格式：年－月－日 23:59:59:999
     */
    public static final String FULL_DATE_END_TIME_FORMAT = "yyyy-MM-dd 23:59:59:999";

    /**
     * 格式：小时：分钟：秒
     */
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";

    public static final String DATE_DD = "dd";

    public static final String DATE_HH = "HH";

    public static final String DATE_MM = "mm";

    public static final String DATE_SS = "ss";

    public static final char POS_1 = '1';

    public static final char POS_0 = '0';

    public static final int POS_10 = 10;

    /**
     * 星期名称
     */
    protected static final String[] WEEK_NAME = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

    private DateUtil() {

    }

    /**
     * 格式化日期 字符串型为日期转换为日期型，为时间转换为时间型
     * @param strDate 字符串
     * @return 日期型日期
     */
    public static Date stringToDate(String strDate) {
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(strDate);
        boolean dateFlag = m.matches();
        if (dateFlag) {
            return stringToDate(strDate, FULL_DATE_FORMAT);
        }
        return stringToDate(strDate, FULL_TIME_FORMAT);
    }

    /**
     * 格式化日期 字符串型转换成日期型
     * @param strDate 字符串型日期
     * @param fm 格式化类型
     * @return 日期型日期
     */
    public static Date stringToDate(String strDate, String fm) {
        DateFormat df = new SimpleDateFormat(fm);
        df.setTimeZone(TimeZone.getTimeZone(DATE_TIMEZONE));
        try {
            return df.parse(strDate);
        }
        catch (ParseException e) {
            LOGGER.error("Error stringToDate: ", e);
        }
        return null;
    }

    /**
     * 格式化日期 日期型转换为字符串型
     * @param date 日期型日期
     * @param fm 格式化类型
     * @return 字符串型日期
     */
    public static String dateToString(Date date, String fm) {
        try {
            SimpleDateFormat dateFmt = new SimpleDateFormat(fm);
            dateFmt.setTimeZone(TimeZone.getTimeZone(DATE_TIMEZONE));
            return dateFmt.format(date);
        }
        catch (Exception e) {
            LOGGER.error("Error dateToString: ", e);
        }
        return null;
    }

    /**
     * 获得前days天的这个时候
     * @param days 天数
     * @return Date
     */
    public static Date getDateAgo(int days) {
        return getDateAgo(getDate(), days);
    }

    /**
     * 获得某时间之前days天的时间
     * @param date 日期
     * @param days 天数
     * @return Date
     */
    public static Date getDateAgo(Date date, int days) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        return c.getTime();
    }

    /**
     * 获得days天后的时间
     * @param days 天数
     * @return Date
     */
    public static Date getDateAfter(int days) {
        return getDateAgo(getDate(), -days);
    }

    /**
     * 获得某个时间之后days天的时间
     * @param date 日期
     * @param days 天数
     * @return Date
     */
    public static Date getDateAfter(Date date, int days) {
        return getDateAgo(date, -days);
    }

    /**
     * 获得本周第一天
     * @return Date
     */
    public static Date getFirstDayOfThisWeek() {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_WEEK, 2);
        return truncate(c.getTime(), Calendar.DATE);
    }

    /**
     * 获得本周最后一天
     * @return Date
     */
    public static Date getLastDayOfThisWeek() {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_WEEK, 7);
        c.add(Calendar.DATE, 1);
        return truncate(c.getTime(), Calendar.DATE);
    }

    /**
     * 获得本月第一天
     * @return Date
     */
    public static Date getFirstDayOfThisMonth() {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_MONTH, 1);
        return truncate(c.getTime(), Calendar.DATE);
    }

    /**
     * 获得本月最后一天
     * @return Date
     */
    public static Date getLastDayOfThisMonth() {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DATE, -1);
        c.add(Calendar.MONTH, 1);
        return truncate(c.getTime(), Calendar.DATE);
    }

    /**
     * 获得上周的最后一天
     * @return Date
     */
    public static Date getLastDayOfLastWeek() {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_WEEK, 1);
        return truncate(c.getTime(), Calendar.DATE);
    }

    /**
     * 获得上周的第一天
     * @return Date
     */
    public static Date getFirstDayOfLastWeek() {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_WEEK, 1);
        c.add(Calendar.DATE, -6);
        return truncate(c.getTime(), Calendar.DATE);
    }

    /**
     * 获得上个月的最后一天
     * @return Date
     */
    public static Date getLastDayOfLastMonth() {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DATE, -1);
        return truncate(c.getTime(), Calendar.DATE);
    }

    /**
     * 获得上个月第一天
     * @return Date
     */
    public static Date getFirstDayOfLastMonth() {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return truncate(c.getTime(), Calendar.DATE);
    }

    /**
     * 查看两个日期是否是同一天
     * @param d1 日期
     * @param d2 日期
     * @return boolean
     */
    public static boolean isSameDate(Date d1, Date d2) {
        return org.apache.commons.lang3.time.DateUtils.isSameDay(d1, d2);
    }

    /**
     * 查看d1是否在d2之后
     * @param d1 日期
     * @param d2 日期
     * @return boolean
     */
    public static boolean afterDate(Date d1, Date d2) {
        d1 = org.apache.commons.lang3.time.DateUtils.truncate(d1, Calendar.DATE);
        d2 = org.apache.commons.lang3.time.DateUtils.truncate(d2, Calendar.DATE);
        return d1.after(d2);
    }

    /**
     * 查看d1是否在d2之前
     * @param d1 日期
     * @param d2 日期
     * @return boolean
     */
    public static boolean beforeDate(Date d1, Date d2) {
        d1 = org.apache.commons.lang3.time.DateUtils.truncate(d1, Calendar.DATE);
        d2 = org.apache.commons.lang3.time.DateUtils.truncate(d2, Calendar.DATE);
        return d1.before(d2);
    }

    /**
     * 在两个日期之间
     * @param d 日期
     * @param from from
     * @param to to
     * @return boolean
     */
    public static boolean isBetweenDate(Date d, Date from, Date to) {
        return (afterDate(d, from) && beforeDate(d, to)) || (isSameDate(d, from) || isSameDate(d, to));
    }

    /**
     * 获得某个日期的零值
     * @param date 日期
     * @return Date
     */
    public static Date getDate(Date date) {
        date = (date == null ? getDate() : date);
        return truncate(date, Calendar.DATE);
    }

    /**
     * 截断日期
     * @param d1 日期
     * @param i java.util.Calendar.DATE
     * @return Date
     */
    public static Date truncate(Date d1, int i) {
        return org.apache.commons.lang3.time.DateUtils.truncate(d1, i);
    }

    /**
     * 解析时间字符串
     * @param dateString 日期字符串
     * @param format 格式
     * @return Date
     */
    public static Date parse(String dateString, String format) {
        return parse(dateString, format, Locale.CHINESE, TimeZone.getDefault());
    }

    /**
     * 解析时间字符串
     * @param dateString 日期字符串
     * @param format 格式
     * @param locale 本地化
     * @param timeZone 域
     * @return Date
     */
    public static Date parse(String dateString, String format, Locale locale, TimeZone timeZone) {
        SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,
                locale);
        formatter.applyPattern(format);
        formatter.setTimeZone(timeZone);
        try {
            return formatter.parse(dateString);
        }
        catch (Exception e) {
            LOGGER.error("Error parse:", e);
        }
        return null;
    }

    /**
     * 获得当前时间的格式
     * @param pattern 格式
     * @return String
     */
    public static String getCurrentTime(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone(DATE_TIMEZONE));
        return format.format(getDate());
    }

    /**
     * 获取当前时间
     * @return Date
     */
    public static Date getDate() {
        return localDateTimeToDate(LocalDateTime.now());
    }

    /**
     * 通过时间戳得到当前时间
     * @param currentTimeMillis 时间戳
     * @return Date
     */
    public static Date getDateByCurrentTimeMillis(long currentTimeMillis) {
        return new Timestamp(currentTimeMillis);
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60L;
        long nh = 1000 * 60 * 60L;
        long nm = 1000 * 60L;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒
        long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
    }

    /**
     * 比较两个日期的年差
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 年数
     */
    public static int subYear(String startDate, String endDate) {
        Date beforeDay = stringToDate(startDate, FULL_DATE_FORMAT);
        Date afterDay = stringToDate(endDate, FULL_DATE_FORMAT);
        return getYear(afterDay) - getYear(beforeDay);
    }

    /**
     * 比较两个日期的月差
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 月数
     */
    public static int subMonth(String startDate, String endDate) {
        int result = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(Objects.requireNonNull(stringToDate(startDate, FULL_DATE_FORMAT)));
        c2.setTime(Objects.requireNonNull(stringToDate(endDate, FULL_DATE_FORMAT)));
        while (!c1.after(c2)) {
            result++;
            c1.add(Calendar.MONTH, 1);
        }
        result = result - 1;
        return result;
    }

    /**
     * 比较两个日期的日差
     * @param startDate yyyy-MM-dd 开始日期
     * @param endDate yyyy-MM-dd 结束日期
     * @return 日期天数
     */
    public static long subDay(String startDate, String endDate) {
        Date dt1 = stringToDate(startDate, FULL_DATE_FORMAT);
        Date dt2 = stringToDate(endDate, FULL_DATE_FORMAT);
        if (dt1 != null && dt2 != null) {
            long l = dt2.getTime() - dt1.getTime();
            return l / MINUTE_SECONDS / MINUTE_SECONDS / MILLI_SECOND / DAY_HOUR;
        }
        return 0L;
    }

    /**
     * 计算日期时间差
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日期秒数
     */
    public static long subTime(String startTime, String endTime) {
        Date dt1 = stringToDate(startTime, FULL_TIME_FORMAT);
        Date dt2 = stringToDate(endTime, FULL_TIME_FORMAT);
        if (dt1 != null && dt2 != null) {
            long l = dt2.getTime() - dt1.getTime();
            return l / MILLI_SECOND;
        }
        return 0L;
    }

    /**
     * 计算日期时间差
     * @param millSeconds 秒
     * @param format 格式
     * @return String
     */
    public static String subTimeString(long millSeconds, String format) {
        String result;
        if (millSeconds <= 0) {
            result = StringUtils.EMPTY;
        }
        else {
            long day = millSeconds / (DAY_SECONDS * MILLI_SECOND);
            long hour = millSeconds / (HOUR_SECONDS * MILLI_SECOND) - day * DAY_HOUR;
            long minute = (millSeconds / (MINUTE_SECONDS * MILLI_SECOND)) - day * DAY_HOUR * MINUTE_SECONDS
                    - hour * MINUTE_SECONDS;
            long second = millSeconds / MILLI_SECOND - day * DAY_SECONDS - hour * HOUR_SECONDS
                    - minute * MINUTE_SECONDS;

            result = subTimeString(day, hour, minute, second, format);
        }
        return result;
    }

    /**
     * 计算日期时间差
     * @param day 天
     * @param hour 小时
     * @param minute 分钟
     * @param second 秒
     * @param format 格式
     * @return String
     */
    private static String subTimeString(long day, long hour, long minute, long second, String format) {
        StringBuilder result = new StringBuilder();
        if (day > 0 && format.contains(DATE_DD)) {
            result.append(day).append("天");
        }
        if (hour > 0 && format.contains(DATE_HH)) {
            result.append(hour).append("小时");
        }
        if (minute > 0 && format.contains(DATE_MM)) {
            result.append(minute).append("分钟");
        }
        if (second > 0 && format.contains(DATE_SS)) {
            result.append(second).append("秒");
        }
        return result.toString();
    }

    /**
     * 获得当前距离1970年的秒数
     * @return int
     */
    public static int getCurrentTimeSeconds() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    /**
     * 获得SQL时间
     * @param date 时间
     * @return Date
     */
    public static java.sql.Date getSqlDate(Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    /**
     * Date转换Timestamp
     * @param date 时间
     * @return Timestamp
     */
    public static Timestamp dateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * Timestamp转换Date
     * @param timestamp 时间戳
     * @return Date
     */
    public static Date timestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }

    /**
     * 字符串日期转换成中文格式日期
     * @param date 字符串日期 yyyy-MM-dd
     * @return yyyy年MM月dd日
     */
    public static String dateToCnDate(String date) {
        StringBuilder result = new StringBuilder();
        String[] cnDate = new String[] { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String ten = "十";
        String[] cnDay = new String[] { "年", "月", "日" };
        String[] dateStr = date.split("-");
        for (int i = 0; i < dateStr.length; i++) {

            for (int j = 0; j < dateStr[i].length(); j++) {
                String charStr = dateStr[i];
                String str = String.valueOf(charStr.charAt(j));
                if (charStr.length() == 2) {
                    if (charStr.equals(String.valueOf(10))) {
                        result.append(ten);
                        break;
                    }
                    else {
                        dateToCnDate(result, charStr, j, cnDate, ten, str);
                    }
                }
                else {
                    result.append(cnDate[Integer.parseInt(str)]);
                }
            }

            result.append(cnDay[i]);
        }
        return result.toString();
    }

    private static void dateToCnDate(StringBuilder result, String charStr, int j, String[] cnDate, String ten,
            String str) {
        if (j == 0) {
            if (charStr.charAt(j) == POS_1) {
                result.append(ten);
            }
            else if (charStr.charAt(j) == POS_0) {
                result.append(StringUtils.EMPTY);
            }
            else {
                result.append(cnDate[Integer.parseInt(str)]).append(ten);
            }
        }
        if (j == 1) {
            if (charStr.charAt(j) == POS_0) {
                result.append(StringUtils.EMPTY);
            }
            else {
                result.append(cnDate[Integer.parseInt(str)]);
            }
        }
    }

    /**
     * 在某时间之后的时间
     * @param date 指定时间
     * @param amount 指定数值 推后1单位
     * @param unit 指定推后单位(Calendar.MONTH 月、Calendar.WEEK_OF_YEAR 周、Calendar.DATE 日)
     * @return Date
     */
    public static Date after(Date date, int amount, int unit) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(unit, amount);
        return gc.getTime();
    }

    /**
     * Date -> LocalDateTime
     * @param date 时间
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date != null) {
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
        return null;
    }

    /**
     * Date -> LocalDate
     * @param date 时间
     * @return LocalDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (date != null) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    /**
     * LocalDateTime -> Date
     * @param localDateTime 时间
     * @return Date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    /**
     * LocalDate -> Date
     * @param localDate 时间
     * @return Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        if (localDate != null) {
            return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }
        return null;
    }

    /**
     * 获取某年某月的天数
     * @param year 年
     * @param month 月
     * @return int
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前日期
     * @return int
     */
    public static int getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获得当前月份
     * @return int
     */
    public static int getToMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得当前年份
     * @return int
     */
    public static int getToYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的天
     * @param date 日期
     * @return int
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回日期的年
     * @param date 日期
     * @return int
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的月份，1-12
     * @param date 日期
     * @return int
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取每月的第一周
     * @param year 年
     * @param month 月
     * @return int
     */
    public static int getFirstWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        // 星期天为第一天
        c.setFirstDayOfWeek(Calendar.SATURDAY);
        c.set(year, month - 1, 1);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取每月的最后一周
     * @param year 年
     * @param month 月
     * @return int
     */
    public static int getLastWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        // 星期天为第一天
        c.setFirstDayOfWeek(Calendar.SATURDAY);
        c.set(year, month - 1, getDaysOfMonth(year, month));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据生日获取星座
     * @param birth yyyy-MM-dd
     * @return String
     */
    public static String getAstro(String birth) {
        String birthName = birth;
        if (!isLeapYear(birth)) {
            birthName += "2000";
        }
        if (!isLeapYear(birthName)) {
            return "";
        }
        int month = Integer.parseInt(birthName.substring(birthName.indexOf('-') + 1, birthName.lastIndexOf('-')));
        int day = Integer.parseInt(birthName.substring(birthName.lastIndexOf('-') + 1));
        String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
        int[] arr = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
        int start = month * 2 - (day < arr[month - 1] ? 2 : 0);
        return s.substring(start, start + 2) + "座";
    }

    /**
     * 判断日期是否有效,包括闰年的情况
     * @param date yyyy-MM-dd
     * @return boolean
     */
    public static boolean isLeapYear(String date) {
        String reg = "^((\\d{2}(([02468][048])|([13579][26]))-?((((0?"
                + "[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))"
                + "|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|"
                + "(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12"
                + "35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))"
                + "-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))"
                + "-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[" + "1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern p = Pattern.compile(reg);
        return p.matcher(date).matches();
    }

    /**
     * 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前);
     * @param date 日期 为null时表示当天
     * @param months 相加(相减)的月数
     */
    public static Date nextMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 取得指定日期过 day 天后的日期 (当 day 为负数表示指日期之前);
     * @param date 日期 为null时表示当天
     * @param day 相加(相减)的天数
     */
    public static Date nextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    /**
     * 取得距离今天 day 日的日期
     * @param day 天
     * @param format 格式
     * @return String
     */
    public static String nextDay(int day, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getDate());
        cal.add(Calendar.DAY_OF_YEAR, day);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 取得指定日期过 week 周后的日期 (当 week 为负数表示指定月之前)
     * @param date 日期 为null时表示当天
     */
    public static Date nextWeek(Date date, int week) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.WEEK_OF_MONTH, week);
        return cal.getTime();
    }

    /**
     * 取得指定日期过 minutes 分钟后的日期 (当 分钟 为负数表示指定分钟之前)
     * @param date 天
     * @param minutes 分钟
     * @return Date
     */
    public static Date nextMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    /**
     * 取得指定日期过 hour 小时后的日期 (当 小时 为负数表示指定分钟之前)
     * @param date 日期
     * @param hour 小时
     * @return Date
     */
    public static Date nextHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.HOUR_OF_DAY, hour);
        return cal.getTime();
    }

    /**
     * 根据日期获得下周一的日期
     * @param date 日期
     * @return Date
     */
    public static Date getNextWeekFirstDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_WEEK, (9 - cal.get(Calendar.DAY_OF_WEEK)) % 7);
        return cal.getTime();
    }

    /**
     * 获取当前的日期 yyyy-MM-dd
     */
    public static String getCurrentDate() {
        return dateToString(getDate(), FULL_DATE_FORMAT);
    }

    /**
     * 获取当前的时间 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        return dateToString(getDate(), FULL_TIME_FORMAT);
    }

    /**
     * 获得当前天开始时间 yyyy-MM-dd 00:00:00
     * @param date 当前时间
     * @return Date
     */
    public static Date getDateStartTime(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(stringToDate(date, FULL_DATE_FORMAT)));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获得当前天结束时间 yyyy-MM-dd 23:59:59
     * @param date 当前时间
     * @return Date
     */
    public static Date getDateEndTime(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(stringToDate(date, FULL_DATE_FORMAT)));
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取当前小时开始时间
     * @param date 时间
     * @return Date
     */
    public static Date getHourStartTime(String date) {
        Date d = stringToDate(date, FULL_TIME_FORMAT);
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取当前小时结束时间
     * @param date 时间
     * @return Date
     */
    public static Date getHourEndTime(String date) {
        Date d = stringToDate(date, FULL_TIME_FORMAT);
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取当前10分钟开始时间
     * @param date 时间
     * @return Date
     */
    public static Date getTenMinuteStartTime(String date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(stringToDate(date, FULL_DATE_FORMAT)));
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) / 10 * 10);
        cal.set(Calendar.SECOND, 0);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取当前10分钟结束时间
     * @param date 时间
     * @return Date
     */
    public static Date getTenMinuteEndTime(String date) {
        Date d = stringToDate(date, FULL_TIME_FORMAT);
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, 10);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) / 10 * 10);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.SECOND, -1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取当前周开始时间
     * @param date 时间
     * @return Date
     */
    public static Date getWeekStartTime(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(stringToDate(date, FULL_DATE_FORMAT)));
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取当前周结束时间
     * @param date 时间
     * @return Date
     */
    public static Date getWeekEndTime(String date) {
        Date d = stringToDate(date, FULL_TIME_FORMAT);
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取当前月开始时间
     * @param date 时间
     * @return Date
     */
    public static Date getMonthStartTime(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(stringToDate(date, FULL_DATE_FORMAT)));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DATE, 1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取当前月结束时间
     * @param date 时间
     * @return Date
     */
    public static Date getMonthEndTime(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(stringToDate(date, FULL_DATE_FORMAT)));
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取当前年开始时间
     * @param date 时间
     * @return Date
     */
    public static Date getYearStartTime(String date) {
        Date d = stringToDate(date, "yyyy");
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取当前年结束时间
     * @param date 时间
     * @return Date
     */
    public static Date getYearEndTime(String date) {
        Date d = stringToDate(date, "yyyy");
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.roll(Calendar.DAY_OF_YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取上个10分钟开始时间
     * @param date 时间
     * @return Date
     */
    public static Date getLastTenMinuteStartTime(String date) {
        Date d = stringToDate(date, FULL_TIME_FORMAT);
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, -10);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) / 10 * 10);
        cal.set(Calendar.SECOND, 0);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取上个10分钟结束时间
     * @param date 时间
     * @return Date
     */
    public static Date getLastTenMinuteEndTime(String date) {
        Date d = stringToDate(date, FULL_TIME_FORMAT);
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) / 10 * 10);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.SECOND, -1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取上个小时开始时间
     * @param date 时间
     * @return Date
     */
    public static Date getLastHourStartTime(String date) {
        Date d = stringToDate(date, FULL_TIME_FORMAT);
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取上个小时结束时间
     * @param date 时间
     * @return Date
     */
    public static Date getLastHourEndTime(String date) {
        Date d = stringToDate(date, FULL_TIME_FORMAT);
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 1);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获得昨天开始时间 yyyy-MM-dd 00:00:00
     * @param date 当前时间
     * @return Date
     */
    public static Date getLastDateStartTime(String date) {
        Date d = stringToDate(date, FULL_DATE_FORMAT);
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获得昨天结束时间 yyyy-MM-dd 23:59:59
     * @param date 当前时间
     * @return Date
     */
    public static Date getLastDateEndTime(String date) {
        Date d = stringToDate(date, FULL_DATE_FORMAT);
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取上周开始时间
     * @param date 时间
     * @return Date
     */
    public static Date getLastWeekStartTime(String date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(stringToDate(date, FULL_DATE_FORMAT)));
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 7);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取上周结束时间
     * @param date 时间
     * @return Date
     */
    public static Date getLastWeekEndTime(String date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(stringToDate(date, FULL_DATE_FORMAT)));
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.add(Calendar.SECOND, -1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取上月开始时间
     * @param date 时间
     * @return Date
     */
    public static Date getLastMonthStartTime(String date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(stringToDate(date, FULL_DATE_FORMAT)));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取上月结束时间
     * @param date 时间
     * @return Date
     */
    public static Date getLastMonthEndTime(String date) {
        if (date == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(stringToDate(date, FULL_DATE_FORMAT)));
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        cal.roll(Calendar.DATE, -1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取上一年开始时间
     * @param date 时间
     * @return Date
     */
    public static Date getLastYearStartTime(String date) {
        Date d = stringToDate(date, "yyyy");
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取上一年结束时间
     * @param date 时间
     * @return Date
     */
    public static Date getLastYearEndTime(String date) {
        Date d = stringToDate(date, "yyyy");
        if (d == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
        cal.roll(Calendar.DAY_OF_YEAR, -1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取昨天的日期
     * @return String
     */
    public static String getYesterday() {
        return yesterday(FULL_DATE_FORMAT);
    }

    /**
     * 根据时间类型获取昨天的日期
     * @param format 格式
     * @return String
     */
    public static String yesterday(String format) {
        return dateToString(nextDay(getDate(), -1), format);
    }

    /**
     * 获取明天的日期
     */
    public static String getNextDay() {
        return dateToString(nextDay(getDate(), 1), FULL_DATE_FORMAT);
    }

    /**
     * 取得当前时间距离1900/1/1的天数
     * @return int
     */
    public static int getDayNum() {
        int dayNum;
        GregorianCalendar gd = new GregorianCalendar();
        Date dt = gd.getTime();
        GregorianCalendar gd1 = new GregorianCalendar(1900, 1, 1);
        Date dt1 = gd1.getTime();
        dayNum = (int) ((dt.getTime() - dt1.getTime()) / (DAY_SECONDS * MILLI_SECOND));
        return dayNum;
    }

    /**
     * getDayNum的逆方法(用于处理Excel取出的日期格式数据等)
     * @param day 天数
     * @return Date
     */
    public static Date getDateByNum(int day) {
        GregorianCalendar gd = new GregorianCalendar(1900, 1, 1);
        Date date = gd.getTime();
        date = nextDay(date, day);
        return date;
    }

    /**
     * 针对yyyy-MM-dd HH:mm:ss格式,显示yyyy-mm-dd
     * @param dateStr 时间
     * @return String
     */
    public static String getYmdDateCn(String dateStr) {
        if (dateStr != null) {
            if (dateStr.length() < POS_10) {
                return "";
            }
            return dateStr.substring(0, 4) + dateStr.substring(5, 7) + dateStr.substring(8, 10);
        }
        return "";
    }

    /**
     * 获取日期的星期
     * @param date 日期
     * @return String
     */
    public static String getWeekByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0) {
            week = 0;
        }
        return WEEK_NAME[week];
    }

    /**
     * 获取timestamp的星期
     * @param time 时间
     * @return String
     */
    public static String getWeekByTimestamp(Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getTime());

        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0) {
            week = 0;
        }
        return WEEK_NAME[week];
    }

    /**
     * 获得日期列表
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return List
     */
    public static List<String> getDialectDate(String startDate, String endDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(Objects.requireNonNull(stringToDate(startDate, FULL_DATE_FORMAT)));
        c2.setTime(Objects.requireNonNull(stringToDate(endDate, FULL_DATE_FORMAT)));
        List<String> resultList = new ArrayList<>();
        while (!c1.after(c2)) {
            resultList.add(dateToString(c1.getTime(), FULL_DATE_FORMAT));
            c1.add(Calendar.DATE, 1);
        }
        return resultList;
    }

    /**
     * 获得两个日期间的周
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 周开始时间和结束时间的list<map>
     */
    public static List<Map<String, String>> getDialectWeek(String startDate, String endDate) {
        final String startString = "start";
        final String endString = "end";
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(Objects.requireNonNull(stringToDate(startDate, FULL_DATE_FORMAT)));
        c2.setTime(Objects.requireNonNull(stringToDate(endDate, FULL_DATE_FORMAT)));

        List<Map<String, String>> resultList = new ArrayList<>();
        while (c1.compareTo(c2) < 0) {
            Map<String, String> map = new HashMap<>(10);
            String date = dateToString(c1.getTime(), FULL_DATE_END_TIME_FORMAT);
            map.put(startString,
                    dateToString(
                            getDateStartTime(dateToString(stringToDate(date, FULL_DATE_TIME_FORMAT), FULL_DATE_FORMAT)),
                            FULL_TIME_FORMAT));
            c1.add(Calendar.DAY_OF_YEAR, -c1.get(Calendar.DAY_OF_WEEK) + 8);
            if (c1.compareTo(c2) > 0) {
                map.put(endString, dateToString(getDateEndTime(dateToString(
                        stringToDate(dateToString(c2.getTime(), FULL_DATE_END_TIME_FORMAT), FULL_DATE_TIME_FORMAT),
                        FULL_DATE_FORMAT)), FULL_TIME_FORMAT));
                break;
            }
            date = dateToString(c1.getTime(), FULL_DATE_END_TIME_FORMAT);
            map.put(endString,
                    dateToString(
                            getDateEndTime(dateToString(stringToDate(date, FULL_DATE_TIME_FORMAT), FULL_DATE_FORMAT)),
                            FULL_TIME_FORMAT));
            c1.setTime(nextDay(c1.getTime(), 1));
            resultList.add(map);
        }

        Calendar lastEndTime = Calendar.getInstance();
        lastEndTime
            .setTime(nextDay(stringToDate(resultList.get(resultList.size() - 1).get("end"), FULL_DATE_FORMAT), 1));
        if (lastEndTime.compareTo(c2) < 0) {
            Map<String, String> map = new HashMap<>(10);
            map.put(startString, dateToString(getDateStartTime(dateToString(
                    stringToDate(dateToString(lastEndTime.getTime(), FULL_DATE_END_TIME_FORMAT), FULL_DATE_TIME_FORMAT),
                    FULL_DATE_FORMAT)), FULL_TIME_FORMAT));
            map.put(endString,
                    dateToString(getDateEndTime(dateToString(
                            stringToDate(dateToString(c2.getTime(), FULL_DATE_END_TIME_FORMAT), FULL_DATE_TIME_FORMAT),
                            FULL_DATE_FORMAT)), FULL_TIME_FORMAT));
            resultList.add(map);
        }
        else if (lastEndTime.compareTo(c2) == 0) {
            Map<String, String> map = new HashMap<>(5);
            map.put(startString, dateToString(getDateStartTime(dateToString(
                    stringToDate(dateToString(lastEndTime.getTime(), FULL_DATE_END_TIME_FORMAT), FULL_DATE_TIME_FORMAT),
                    FULL_DATE_FORMAT)), FULL_TIME_FORMAT));
            map.put(endString, dateToString(getDateEndTime(dateToString(
                    stringToDate(dateToString(lastEndTime.getTime(), FULL_DATE_END_TIME_FORMAT), FULL_DATE_TIME_FORMAT),
                    FULL_DATE_FORMAT)), FULL_TIME_FORMAT));
            resultList.add(map);
        }
        else {
            resultList.get(resultList.size() - 1)
                .put(endString, dateToString(getDateEndTime(dateToString(
                        stringToDate(dateToString(c2.getTime(), FULL_DATE_END_TIME_FORMAT), FULL_DATE_TIME_FORMAT),
                        FULL_DATE_FORMAT)), FULL_TIME_FORMAT));
        }
        return resultList;
    }

    /**
     * 判断字符串是否是LocalDateTime格式
     * @param timeString 日期字符串
     * @return isLocalDateTime
     */
    public static boolean isLocalDateTime(String timeString) {
        boolean isLocalDateTime = true;
        DateTimeFormatter df = DateTimeFormatter.ofPattern(FULL_TIME_FORMAT);
        try {
            LocalDateTime.parse(timeString, df);
        }
        catch (Exception e) {
            isLocalDateTime = false;
        }
        return isLocalDateTime;
    }

    /**
     * 字符串转LocalDateTime
     * @param timeString 日期字符串
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(String timeString) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(FULL_TIME_FORMAT);
        return LocalDateTime.parse(timeString, df);
    }

}
