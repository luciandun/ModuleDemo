package com.daily.baselibrary.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by arvin on 2016/2/2 16:40.
 * 时间转换相关方法
 */
@SuppressWarnings("ALL")
public class TimeUtil {
    private static final long minute = 60 * 1000; //分钟
    private static final long hour = 60 * minute; //小时
    private static final long day = 24 * hour;    //天
    private static final long week = 7 * day;     //周
    private static final long month = 31 * day;   //月
    private static final long year = 12 * month;  //年

    public static long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * 将time转换为 1970-1-1 00:00 格式的时间
     */
    public static String getAllTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date(time);
        return format.format(now);
    }

    public static long getAllTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static long getDayMonthTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = format.parse(time);
            return Long.valueOf(dateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 将time转换为 1970-1-1 格式的时间
     */
    public static String getYearTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 1970-1-1 格式的时间
     */
    public static String getYMdTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 1-1 格式的时间
     */
    public static String getMDTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 1-1 格式的时间
     */
    public static String getMDHmTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd\nHH:mm");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 00:00 格式的时间
     */
    public static String getHmTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 将time转换为 00:00 格式的时间
     */
    public static String getMsTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        Date now = new Date(time);
        return format.format(now);
    }


    public static int getHours(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH");
        Date now = new Date(time);
        return Integer.valueOf(format.format(now));
    }

    public static String getWeekDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("EEEE", Locale.CHINA);
        Date now = new Date(time);
        return format.format(now);
    }

    /**
     * 获取最近时间根据差值确定时间显示的样式
     */
    public static String getRecentlyTime(long time) {
        if (time <= 0) {
            return null;
        }
        long diff = new Date().getTime() - time;
        long r = 0;
        if (diff > year) {
            return getYMdTime(time);
        }
        if (diff > day) {
            if (diff - day >= 1 && diff - day < 2) {
                return "昨天";
            }
            return getMDTime(time);
        }
        if (diff > hour) {
            if (diff - hour <= 3) {
                return getHmTime(time);
            }
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    public static String formatInt(int i) {
        return i < 10 ? ("0" + i) : (i + "");
    }

    /**
     * 获取年纪
     */
    public static int getAge(long time) {
        if (time <= 0) {
            return 0;
        }
        Date birthday = new Date();
        birthday.setTime(time);
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }


    public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_HMS2 = "yyyy年MM月dd日 HH:mm:ss";
    public static final String YMD_HM = "yyyy-MM-dd HH:mm";
    public static final String YMD_HM2 = "yyyy年MM月dd日 HH:mm";
    public static final String YMD = "yyyy-MM-dd";
    public static final String YMD2 = "yyyy年MM月dd日";
    public static final String HMS = "HH:mm:ss";
    public static final String HM = "HH:mm";

    /**
     * 消息发送的时间格式,今日的仅显示时分秒，非今日的显示年月日时分秒
     *
     * @param time 传入yyyy-MM-dd HH:mm:ss格式
     * @return
     */
    public static String getMessageTime(String time) {
        String fToday = HMS;
        String fOtherDay = YMD_HMS;
        String result = "";
        SimpleDateFormat format1 = new SimpleDateFormat(fOtherDay, Locale.CHINA);
        SimpleDateFormat format2 = new SimpleDateFormat(fToday, Locale.CHINA);

        Calendar calendarToday = Calendar.getInstance();  //今日
        calendarToday.setTime(new Date());
        Calendar calendarOtherDay = Calendar.getInstance(); //传入的日期
        try {
            Date date = format1.parse(time);
            calendarOtherDay.setTime(date);
            //如果是今天
            if (calendarOtherDay.get(Calendar.YEAR) == calendarToday.get(Calendar.YEAR) &&
                    calendarOtherDay.get(Calendar.MONTH) == calendarToday.get(Calendar.MONTH) &&
                    calendarOtherDay.get(Calendar.DAY_OF_MONTH) == calendarToday.get(Calendar.DAY_OF_MONTH)) {
                result = format2.format(date);
            } else {  //不是今天
                result = format1.format(date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 将秒转换成  09:30:33 时分秒格式
     *
     * @param seconds
     * @return
     */
    public static String formatTime(int seconds) {
        StringBuilder sb = new StringBuilder();
        //先获得小时
        final int HOUR = 60 * 60;
        final int MINUTE = 60;
        int hour = seconds / HOUR;
        if (hour < 10)
            sb.append(0).append(hour).append(":");
        else
            sb.append(hour).append(":");
        //在获得分钟数
        int minute = seconds % HOUR / MINUTE;
        if (minute < 10)
            sb.append(0).append(minute).append(":");
        else
            sb.append(minute).append(":");
        //最终获得秒
        int second = seconds % MINUTE;
        if (second < 10)
            sb.append(0).append(second);
        else
            sb.append(second);
        return sb.toString();
    }

    public static boolean isToday(long ms) {
        SimpleDateFormat format = new SimpleDateFormat(YMD);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(new Date());
        String todayStr = format.format(calendar.getTime());
        String other = format.format(new Date(ms));
        return TextUtils.equals(todayStr, other);
    }

}
