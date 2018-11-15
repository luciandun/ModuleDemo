package com.daily.baselibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.WindowManager;

/**
 * Created by arvin on 2016/2/2 16:42.
 * 屏幕尺寸相关方法
 */
@SuppressWarnings("all")
public class ScreenUtil {
    private static WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public static int getScreenWidth(Context context) {
        return getWindowManager(context).getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        return getWindowManager(context).getDefaultDisplay().getHeight();
    }

    public static int px2dp(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(float pxValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕顶部状态栏高度
     *
     * @return px value
     */
    public static int getStatusBarHeight(Context context) {

//        int height = 38;
//        try {
//            Class<?> cls = Class.forName("com.android.internal.R$dimen");
//            Object obj = cls.newInstance();
//            Field field = cls.getField("status_bar_height");
//            int x = Integer.parseInt(field.get(obj).toString());
//            height = context.getApplicationContext().getResources().getDimensionPixelSize(x);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return height;

        int height = 0;
        Resources resources = context.getApplicationContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 获取屏幕底部导航栏高度
     *
     * @return px value
     */
    public static int getNavigationBarHeight(Context context) {
        int height = 0;
        Resources resources = context.getApplicationContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0)
            height = resources.getDimensionPixelSize(resourceId);

        return height;
    }

//    /**
//     * 获取屏幕宽度
//     *
//     * @return px value
//     */
//    public static int getScreenWidth(Context context) {
//        return context.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
//    }
//
//    /**
//     * 获取屏幕高度
//     *
//     * @return px value
//     */
//    public static int getScreenHeight(Context context) {
//        return context.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
//    }

}
