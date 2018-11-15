package com.daily.baselibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * description:网络检测工具
 * author: dlx
 * date: 2018/09/29
 * version: 1.0
 */
public class NetworkUtil {


    /**
     * 是否是移动数据网络
     */
    public static boolean isMobileNet(Context context) {
        if (context != null) {
            ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conn != null) {
                NetworkInfo networkInfo = conn.getActiveNetworkInfo();
                if (networkInfo != null) {
                    if (networkInfo.isAvailable()) {
                        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查是否连接到牙窥镜wifi
     */
    public static boolean isConnectedRHMG(Context context) {
        if (context != null) {
            ConnectivityManager conn = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conn != null) {
                NetworkInfo networkInfo = conn.getActiveNetworkInfo();
                if (networkInfo != null) {
                    if (networkInfo.isAvailable()) {
                        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                            if (wifiManager != null) {
                                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                                if (wifiInfo != null) {
                                    return isRHMGWifi(wifiInfo.getSSID());
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检查网络是否可用，即是否开启网络
     */
    public static boolean isNetworkEnable(Context context) {
        if (context != null) {
            ConnectivityManager conn = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conn != null) {
                NetworkInfo networkInfo = conn.getActiveNetworkInfo();
                if (networkInfo != null) {
                    return networkInfo.isAvailable() && !isConnectedRHMG(context);
                }
            }
        }
        return false;
    }


    /**
     * 检测是不是牙窥镜的Wifi
     */
    private static boolean isRHMGWifi(String wifiId) {
        if (wifiId == null) return false;
        wifiId = wifiId.trim();
        String regex = "^RHMG+[0-9]{6}[A-Z]{5}";
        Log.i("wifi name", wifiId + " wifi length:" + wifiId.length());
        String cutString = wifiId;
        if (wifiId.length() == 17) {
            cutString = wifiId.substring(1, wifiId.length() - 1);
            Log.i("wifi name", cutString + " wifi length:" + cutString.length());
        }
        return cutString.matches(regex);
    }
}
