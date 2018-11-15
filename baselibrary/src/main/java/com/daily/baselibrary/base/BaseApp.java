package com.daily.baselibrary.base;

import android.app.Application;
import android.content.Context;

/**
 * description:底层Application，提供常用工具集Context对象
 * author: dlx
 * date: 2018/11/15
 * version: 1.0
 */
public abstract class BaseApp extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this.getApplicationContext();
    }

    /**
     * 拿到AppContext
     */
    public static Context getAppContext() {
        return appContext;
    }
}
