package com.example.moduledemo;

import android.content.Context;

import com.daily.baselibrary.base.BaseApp;
import com.daily.baselibrary.utils.LogUtil;

public class ThisApp extends BaseApp {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.setDebug(BuildConfig.DEBUG);
    }
}
