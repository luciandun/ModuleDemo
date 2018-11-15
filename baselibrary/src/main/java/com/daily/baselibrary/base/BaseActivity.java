package com.daily.baselibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.daily.baselibrary.views.LoadingDialog;

/**
 * description:Activity基类
 * author: dlx
 * date: 2018/09/14
 * version: 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        mContext = this;
        initView();
        initData();
    }


    /**
     * Activity跳转底层方法
     */
    public void gotoActivity(Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void gotoActivity(Class<? extends Activity> clazz, Bundle bundle, boolean finish) {
        gotoActivity(clazz, bundle);
        if (finish)
            finish();
    }

    /**
     * 代替findViewById(id)方法
     */
    public <T extends View> T findView(int resId) {
        return (T) findViewById(resId);
    }

    /**
     * 显示进度框
     */
    protected void showLoading(String message) {
        LoadingDialog.show(this, message);
    }

    /**
     * 隐藏进度框
     */
    protected void cancelLoading() {
        LoadingDialog.dismiss();
    }
}
