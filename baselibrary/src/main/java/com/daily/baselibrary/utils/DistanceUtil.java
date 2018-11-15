package com.daily.baselibrary.utils;

import java.util.Locale;

/**
 * description:距离转换工具
 * author: dlx
 * date: 2018/06/26
 * version: 1.0
 */
public class DistanceUtil {


    /**
     * 将距离转换成 m  km形式
     *
     * @param distance
     * @return
     */
    public static String formatDistance(double distance) {
        if (distance < 1000) return Double.valueOf(distance).intValue() + "m";
        else {
            double dist = distance / 1000;
            return String.format(Locale.CHINESE, "%.2fkm", dist);
        }
    }
}
