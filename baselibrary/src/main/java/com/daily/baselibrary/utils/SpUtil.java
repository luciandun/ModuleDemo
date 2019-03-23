package com.daily.baselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.daily.baselibrary.base.BaseApp;

/**
 * SharedPreference utils here
 * for manage lite data
 * <p>
 * warning:
 * only for lite data saving,otherwise you should use sqLite database
 */
public class SpUtil {


    private static SharedPreferences getSp() {
        return BaseApp.getAppContext()
                .getSharedPreferences("Shared_Preference", Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getSpEditor() {
        return getSp().edit();
    }

    public static void saveString(String key, String value) {
        getSpEditor().putString(key, value).apply();
    }

    public static String getString(String key) {
        return getSp().getString(key, null);
    }


    public static void saveInt(String key, int value) {
        getSpEditor().putInt(key, value).apply();
    }

    public static int getInt(String key) {
        return getSp().getInt(key, 0);
    }


    public static void saveLong(String key, long value) {
        getSpEditor().putLong(key, value).apply();
    }

    public static long getLong(String key) {
        return getSp().getLong(key, 0L);
    }


    public static void saveFloat(String key, float value) {
        getSpEditor().putFloat(key, value).apply();
    }

    public static float getFloat(String key) {
        return getSp().getFloat(key, 0F);
    }


    public static void saveBoolean(String key, boolean value) {
        getSpEditor().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        return getSp().getBoolean(key, false);
    }


    public static void clearData() {
        getSpEditor().clear();
    }
}
