package com.daily.baselibrary.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * description: 尺寸转换工具
 * author: dlx
 * date: 2018/06/09
 * version: 1.0
 */
public class DimenUtil {

    public static float px2dp(Context context, int pxValue) {
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (pxValue / scale);
    }

    public static int dp2px(Context context, int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                context.getApplicationContext().getResources().getDisplayMetrics());
    }

    public static float px2sp(Context context, int pxValue) {
        return (pxValue / context.getApplicationContext().getResources().getDisplayMetrics().scaledDensity);
    }

    public static int sp2px(Context context, int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                context.getApplicationContext().getResources().getDisplayMetrics());
    }

}
