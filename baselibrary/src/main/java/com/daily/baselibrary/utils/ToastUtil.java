package com.daily.baselibrary.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.daily.baselibrary.base.BaseApp;


/**
 * description:Toast统一封装工具
 * author: dlx
 * date: 2018/5/4
 * version: 1.0
 */
public class ToastUtil {

    /**
     * 之前显示的内容
     */
    private static String oldMsg;
    /**
     * Toast对象
     */
    private static Toast toast = null;
    /**
     * 第一次时间
     */
    private static long oneTime = 0;
    /**
     * 第二次时间
     */
    private static long twoTime = 0;

    /**
     * 根据显示内容来控制显示时长
     *
     */
    public static void show(String message) {
        if (TextUtils.isEmpty(message)) return;
        int duration = message.length() > 8 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        if (toast == null) {
            toast = Toast.makeText(BaseApp.getAppContext(), message, duration);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = message;
                toast.setText(message);
                toast.setDuration(duration);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

//    public static void show(String msg){
//        if (toast == null){
//            toast = Toast.makeText(BaseApp.getAppContext(),msg,Toast.LENGTH_SHORT);
//        }else{
//            toast.setText(msg);
//        }
//        toast.show();
//    }

}
