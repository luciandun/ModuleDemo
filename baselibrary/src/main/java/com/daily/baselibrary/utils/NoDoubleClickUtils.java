package com.daily.baselibrary.utils;

/**
 * Created by Administrator on 2018/5/21.
 */

public class NoDoubleClickUtils {

    private static long lastClickTime = 0;
    private final static int SPACE_TIME = 300;

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        if (currentTime - lastClickTime > SPACE_TIME) {
            lastClickTime = currentTime;
            isClick2 = false;
        } else {
            isClick2 = true;
        }
        return isClick2;
    }
}
