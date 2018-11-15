package com.daily.baselibrary.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * description:常用工具集
 * author: dlx
 * date: 2018/11/15
 * version: 1.0
 */
public class Utils {

    /**
     * 申明权限 <uses-permission android:name="android.permission.READ_PHONE_STATE"></uses-permission>
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        if (context == null) return null;
        TelephonyManager phoneManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (phoneManager != null) {
            return phoneManager.getImei();
        }
        return null;
    }

}
