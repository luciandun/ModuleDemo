package com.daily.baselibrary.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * description:字符串类处理工具
 * author: dlx
 * date: 2018/5/4
 * version: 1.0
 */
public class StringUtil {


    /**
     * 是否为电话号码
     *
     * @param number 待检测号码
     * @return 是否
     */
    public static boolean isPhoneNum(String number) {
        return !TextUtils.isEmpty(number) &&
                number.startsWith("1") &&
                number.length() == 11;
    }

    /**
     * 判断是否为数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
