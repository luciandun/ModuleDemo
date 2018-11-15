package com.daily.baselibrary.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * description: 获取App相关信息
 * author: dlx
 * date: 2018/5/4
 * version: 1.0
 */
public class AppInfo {
    /**
     * 获取包名 com.test.app
     *
     * @param context
     * @return 包名
     */
    public static String getPackageName(Context context) {
        return context.getApplicationContext().getPackageName();
    }

    /**
     * 获取版本名  2.0.22
     *
     * @param context
     * @return String版本名
     */
    public static String getVersionName(Context context) {
        String verName = "";
        PackageManager pm = context.getPackageManager();
        try {
            verName = pm.getPackageInfo(getPackageName(context), 0).versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
            return verName;
        }
        return verName;
    }

    /**
     * 获取版本号 213
     *
     * @param context
     * @return int值版本号
     */
    public static int getVersionCode(Context context) {
        int verCode = -1;
        PackageManager pm = context.getPackageManager();
        try {
            verCode = pm.getPackageInfo(getPackageName(context), 0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取APP名称
     *
     * @param context
     * @return AppName
     */
    public static String getAppName(Context context) {
        Resources res = context.getResources();
        String pkgName = getPackageName(context);
        String verName = res.getText(res.getIdentifier("app_name", "string", pkgName)).toString();
        return verName;
    }

    /**
     * 获取APP图标
     *
     * @param context
     * @return app图标Drawable
     */
    public static Drawable getAppIcon(Context context) {
        Drawable icon = null;
        PackageManager pm = context.getApplicationContext().getPackageManager();
        try {
            icon = pm.getPackageInfo(getPackageName(context), 0).applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        }
        return icon;
    }

    /**
     * 获取初次安装日期
     *
     * @param context
     * @return 初次安装日期(毫秒值)
     */
    public static long getFirstInstallTime(Context context) {
        long fit = 0L;
        try {
            fit = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        }
        return fit;
    }

    /**
     * 获取上次更新日期
     *
     * @param context
     * @return 上一次更新日期(毫秒值)
     */
    public static long getLastUpdateTime(Context context) {
        long fit = 0L;
        try {
            fit = context.getApplicationContext().getPackageManager().getPackageInfo(getPackageName(context), 0).lastUpdateTime;
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        }
        return fit;

    }

}
