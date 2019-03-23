package com.daily.baselibrary.utils;

import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.Collection;

/**
 * description: 日志打印工具
 * author: dlx
 * date: 2018/5/4
 * version: 1.0
 */
public class LogUtil {

    private static final int TYPE_V = 0;
    private static final int TYPE_D = 1;
    private static final int TYPE_I = 2;
    private static final int TYPE_W = 3;
    private static final int TYPE_E = 4;

    private static boolean debug = true;

    /**
     * 在application中初始化该变量
     *
     * @param debug 是否开启日志打印
     */
    public static void setDebug(boolean debug) {
        LogUtil.debug = debug;
    }

    public static void v(String tag, String msg) {
        print(tag, msg, TYPE_V);
    }

    public static void d(String tag, String msg) {
        print(tag, msg, TYPE_D);
    }

    public static void i(String tag, String msg) {
        print(tag, msg, TYPE_I);
    }

    public static void w(String tag, String msg) {
        print(tag, msg, TYPE_W);
    }

    public static void e(String tag, String msg) {
        print(tag, msg, TYPE_E);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void collection(Collection collection) {
        Logger.d(collection);
    }


    private static void print(String tag, String msg, int type) {
        if (debug) {
            switch (type) {
                case TYPE_V:
                    Logger.t(tag).v(msg);
                    break;
                case TYPE_D:
                    Logger.t(tag).d(msg);
                    break;
                case TYPE_I:
                    Logger.t(tag).i(msg);
                    break;
                case TYPE_W:
                    Logger.t(tag).w(msg);
                    break;
                case TYPE_E:
                    Logger.t(tag).e(msg);
                    break;
                default:
                    Logger.t(tag).v(msg);
                    break;
            }
        }
    }


    //辅助代码
    private static String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }


    public static void i(String msg) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();

        int methodCount = 1;
        int stackOffset = getStackOffset(trace);

        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StackTraceElement element = trace[stackIndex];

            StringBuilder builder = new StringBuilder();
            builder.append(getSimpleClassName(element.getClassName()))
                    .append(".")
                    .append(element.getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(element.getFileName())
                    .append(":")
                    .append(element.getLineNumber())
                    .append(")")
                    .append(" | ")
                    .append(msg);

            Log.i(getSimpleClassName(element.getClassName()), builder.toString());

        }
    }

    private static int getStackOffset(StackTraceElement[] trace) {
        for (int i = 2; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LogUtil.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

}
