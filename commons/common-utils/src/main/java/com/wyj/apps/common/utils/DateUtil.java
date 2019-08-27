package com.wyj.apps.common.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Created
 * Author: wyj
 * Date: 2019/8/27
 */
public class DateUtil {

    /**
     * 判断两个时间是否是同一天
     * @return 是否是同一天
     */
    public static boolean inSameDay(long d1, long d2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.clear();
        calendar1.setTimeInMillis(d1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.clear();
        calendar2.setTimeInMillis(d2);

        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 取零时的时间戳
     * @return 零时时间戳
     */
    public static long zeroTimestamp(long d) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.clear();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.clear();
        calendar2.setTimeInMillis(d);

        calendar1.set(Calendar.YEAR, calendar2.get(Calendar.YEAR));
        calendar1.set(Calendar.MONTH, calendar2.get(Calendar.MONTH));
        calendar1.set(Calendar.DAY_OF_MONTH, calendar2.get(Calendar.DAY_OF_MONTH));
        return calendar1.getTimeInMillis();
    }

    /**
     * transfer {@link LocalDateTime} to {@link Date}
     */
    public static Date toDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * transfer {@link Date} to {@link LocalDateTime}
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * 获取当前时间
     */
    public static Date now() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return toDate(localDateTime);
    }

    /**
     * 使用属于线程的sdf 格式化date，如果没有则创建
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = ThreadContextUtils.get(format);
        if (sdf == null) {
            sdf = new SimpleDateFormat(format);
            ThreadContextUtils.put(format, sdf);
        }
        return sdf.format(date);
    }

    /**
     * 格式化为 yyyy-MM-dd HH:mm:ss
     * <br> 使用 属于线程的sdf
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 使用 立即创建立即销毁的sdf 格式化date
     */
    public static String formatDateSimple(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 格式化为 yyyy-MM-dd HH:mm:ss
     * <br> 使用 立即创建立即销毁的sdf
     */
    public static String formatDateSimple(Date date) {
        return formatDateSimple(date, "yyyy-MM-dd HH:mm:ss");
    }

}
