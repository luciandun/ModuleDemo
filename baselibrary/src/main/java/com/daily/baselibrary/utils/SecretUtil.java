package com.daily.baselibrary.utils;

import java.security.MessageDigest;

/**
 * description:
 * author: dlx
 * date: 2018/07/18
 * version: 1.0
 */
public class SecretUtil {

    public static String generate(String password, String phone) {
        return md5Hex(password + phone);
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    private static String md5Hex(String src) {

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = src.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

//    public static void main(String[] args) {
//        String pwd = "123456";
//        String phone = "18482001195";
//        System.out.println(generate(pwd,phone));
//    }

}
