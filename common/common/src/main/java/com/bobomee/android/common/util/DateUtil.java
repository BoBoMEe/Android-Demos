package com.bobomee.android.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期Date相关工具类
 * <p>
 * Created by zhaiyifan on 2015/8/3.
 */
public class DateUtil {
    /**
     * Default date pattern.
     */
    public final static String DEFAULT_PATTERN = "yyyy-MM-dd_HH-mm-ss.SSS";


    private final static ThreadLocal<SimpleDateFormat> sDefaultDateFormat = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DEFAULT_PATTERN);
        }
    };

    private DateUtil() {
        // static usage.
    }

    /**
     * Get current date with default pattern.
     *
     * @return Current date with default pattern.
     * @see #DEFAULT_PATTERN
     */
    public static String getDate() {
        return getDate(System.currentTimeMillis());
    }

    /**
     * Get date with default pattern of corresponding time.
     *
     * @param timeInMillis Time in milliseconds.
     * @return Date with default pattern of corresponding time.
     */
    public static String getDate(long timeInMillis) {
        return sDefaultDateFormat.get().format(new Date(timeInMillis));
    }

    /**
     * Get date with default pattern of corresponding time.
     *
     * @param pattern Date format pattern.
     * @return Date with default pattern of corresponding time.
     * @see #DEFAULT_PATTERN
     */
    public static String getDate(String pattern) {
        return getDate(pattern, System.currentTimeMillis());
    }

    /**
     * Get date with default pattern of corresponding time.
     *
     * @param pattern      Date format pattern.
     * @param timeInMillis Time in milliseconds.
     * @return Date with default pattern of corresponding time.
     */
    public static String getDate(String pattern, long timeInMillis) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(timeInMillis));
    }

    /**
     * Get the start time in milliseconds of a day.
     *
     * @param time Time within one day.
     * @return The start time of a day.
     */
    public static long getStartOfDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static String getDateDesc(Date date) {
        Date now = new Date();
        long time = now.getTime() - date.getTime();
        String str = "很久了";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String mmDate = format.format(date);
        if (time / 1000 / 60 <= 3) {
            str = "刚刚";
        } else if (time / 1000 / 60 > 3 && time / 1000 / 60 < 60) {
            str = time / 1000 / 60 + "分钟前";
        } else if (time / 1000 / 60 / 60 >= 1 && time / 1000 / 60 / 60 < 24) {
            str = time / 1000 / 60 / 60 + "小时前";
        } else if (time / 1000 / 60 / 60 / 24 >= 1
                && time / 1000 / 60 / 60 / 24 < 2) {
            str = "1天前";
        } else if (time / 1000 / 60 / 60 / 24 >= 2
                && time / 1000 / 60 / 60 / 24 < 3) {
            str = "2天前";
        } else if (time / 1000 / 60 / 60 / 24 >= 3) {
            str = mmDate;
        }
        return str;
    }
    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(
            Calendar.SECOND);
    }

    public static long subtractDate(Date dateStart, Date dateEnd) {
        return dateEnd.getTime() - dateStart.getTime();
    }

    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public static int getWeekOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        return week - 1;
    }

    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
        } else {
            day = day - 1;
        }
        return day;
    }
    public static String toDate(Date date, int add, DateFormat dateFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, add);
        return dateFormat.format(calendar.getTime());
    }

    public static Date getLastdayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date getNextdayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static boolean isTheSameDay(Date one, Date another) {
        Calendar _one = Calendar.getInstance();
        _one.setTime(one);
        Calendar _another = Calendar.getInstance();
        _another.setTime(another);
        int oneDay = _one.get(Calendar.DAY_OF_YEAR);
        int anotherDay = _another.get(Calendar.DAY_OF_YEAR);

        return oneDay == anotherDay;
    }

    /**
     * 获取当前时间的年份
     */
    public static int getYear() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前时间的月份
     */
    public static int getMonth() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 获取当前时间是哪天
     */
    public static int getDay() {
        Calendar calendar = GregorianCalendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * @param date1
     * @param date2
     * @return 1:date1大于date2；
     * -1:date1小于date2
     */
    public static int compareDate(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
