package me.temoa.baseutils;

import android.annotation.SuppressLint;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by lai
 * on 2018/3/19.
 */

@SuppressWarnings("WeakerAccess")
public class DateUtils {

    public static final int MILLIS = 1;
    public static final int SEC = 1000;
    public static final int MIN = 60000;
    public static final int HOUR = 3600000;
    public static final int DAY = 86400000;

    @IntDef({MILLIS, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String date2String(final Date date) {
        return DEFAULT_FORMAT.format(date);
    }

    @Nullable
    public static Date string2Date(final String timeStr) {
        try {
            return DEFAULT_FORMAT.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    public static boolean isSameDay(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DATE) == calendar2.get(Calendar.DATE);
    }

    public static boolean isToday(final Date date) {
        Date today = new Date();
        return isSameDay(date, today);
    }

    public static long getTimeSpan(final Date date1, final Date date2, @Unit final int unit) {
        long millis = Math.abs(date1.getTime() - date2.getTime());
        return millis / unit;
    }

    public static long getTimeSpanByNow(final Date date, @Unit final int unit) {
        Date today = new Date();
        return getTimeSpan(date, today, unit);
    }

    public static String getFriendlyTimeSpanByNow(final Date date) {
        long now = System.currentTimeMillis();
        long millis = date.getTime();
        long span = now - millis;
        if (span < 0) {
            return String.format("%tc", millis);
        }

        if (span < 1000) {
            return "刚刚";
        } else if (span < MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / SEC);
        } else if (span < HOUR) {
            return String.format(Locale.getDefault(), "%d分钟前", span / MIN);
        } else if (span < DAY) {
            return String.format(Locale.getDefault(), "%d小时前", span / HOUR);
        } else if (span < DAY * 30L) {
            return String.format(Locale.getDefault(), "%d天前", span / DAY);
        } else if (span < DAY * 365L) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);
            Calendar nowCalendar = Calendar.getInstance();
            int result = nowCalendar.get(Calendar.MONTH) - calendar.get(Calendar.MONTH);
//            int month = (nowCalendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)) * 12;
            return String.format(Locale.getDefault(), "%d个月前", Math.abs(result));
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);
            Calendar nowCalendar = Calendar.getInstance();
            int result = nowCalendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
            return String.format(Locale.getDefault(), "%d年前", result);
        }

//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//        long today = cal.getTimeInMillis();
//        if (millis >= today) {
//            return String.format("今天%tR", millis);
//        } else if (millis > today - DAY) {
//            return String.format("昨天%tR", millis);
//        } else {
//            return String.format("%tF", millis);
//        }
    }
}
