package com.daily.baselibrary.utils;

import android.util.Log;

/**
 * description: 日志打印工具
 * author: dlx
 * date: 2018/5/4
 * version: 1.0
 */
public class LogUtil {

    public static boolean isReleased = false;  //表示判断此APP是否已发布、发布则关闭日志打印

    public static void v(String tag, String msg) {
        print(tag, msg, LogType.TYPE_V);
    }

    public static void d(String tag, String msg) {
        print(tag, msg, LogType.TYPE_D);
    }

    public static void i(String tag, String msg) {
        print(tag, msg, LogType.TYPE_I);
    }

    public static void w(String tag, String msg) {
        print(tag, msg, LogType.TYPE_W);
    }

    public static void e(String tag, String msg) {
        print(tag, msg, LogType.TYPE_E);
    }

    /**
     * 日志打印统一调用该方法
     *
     * @param tag  日志tag
     * @param msg  日志内容
     * @param type 日志类型
     */
    private static void print(String tag, String msg, LogType type) {
        if (!isReleased) {
            switch (type) {
                case TYPE_V:
                    Log.v(tag, msg);
                    break;
                case TYPE_D:
                    Log.d(tag, msg);
                    break;
                case TYPE_I:
                    Log.i(tag, msg);
                    break;
                case TYPE_W:
                    Log.w(tag, msg);
                    break;
                case TYPE_E:
                    Log.e(tag, msg);
                    break;
                default:
                    Log.d(tag, msg);
                    break;
            }
        }
    }

    /**
     * 获取当前类名,通常可以用作日志打印tag，方便查看打印位置
     *
     * @param object
     * @return
     */
    public static String getClzName(Object object) {
        if (object != null) {
            String className = "dlx--" + object.getClass().getSimpleName();
            return className;
        }
        return "dlx--";
    }

    /**
     * 获取当前类名,通常可以用作日志打印tag，方便查看打印位置
     *
     * @return
     */
    public static String getCurrentClassName() {
        int level = 1;
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String className = stacks[level].getClassName();
        className = "dlx--" + className.substring(className.lastIndexOf(".") + 1);
        return className;
    }

    /**
     * 获取当前方法名
     *
     * @return
     */
    public static String getCurrentMethodName() {
        int level = 1;
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String methodName = stacks[level].getMethodName();
        return methodName;
    }

    /**
     * 日志打印级别
     */
    public enum LogType {
        TYPE_V,
        TYPE_D,
        TYPE_I,
        TYPE_W,
        TYPE_E
    }

}
