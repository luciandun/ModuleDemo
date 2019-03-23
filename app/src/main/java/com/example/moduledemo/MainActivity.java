package com.example.moduledemo;

import android.util.Log;
import android.view.View;

import com.daily.baselibrary.base.BaseActivity;
import com.daily.baselibrary.utils.LogUtil;
import com.orhanobut.logger.Logger;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initToolbar("Home", false);
        findView(R.id.item1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItem1Click(v);
            }
        });
    }

    @Override
    public void initData() {

    }


    public void onItem1Click(View view) {
        List<String> list = Arrays.asList("applie", "doisdalk", "orange", "pear");
        LogUtil.collection(list);
        Logger.d(list);
        Log.i(TAG, "onItem1Click: " + list.toString());
    }
}
