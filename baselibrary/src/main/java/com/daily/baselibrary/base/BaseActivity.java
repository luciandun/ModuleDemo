package com.daily.baselibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.daily.baselibrary.R;
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
     * 统一处理toolbar初始化
     *
     * @param title   标题
     * @param canBack 是否显示返回箭头
     */
    protected void initToolbar(String title, boolean canBack) {
        Toolbar toolbar = findView(R.id.tool_bar);
        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            //可以点击返回，则关闭当前页面
            if (canBack) {
                TextView backView = findView(R.id.tv_back);
                backView.setVisibility(View.VISIBLE);
                backView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
            TextView tv = findView(R.id.tv_title);
            tv.setText(title);
        }
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
