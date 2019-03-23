package com.daily.baselibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daily.baselibrary.utils.LogUtil;

/**
 * description:Fragment懒加载
 * author: dlx
 * date: 2018/5/4
 * version: 1.0
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "RunState_";

    /**
     * Fragment所在的Activity
     */
    protected Context mContext;

    /**
     * 是否第一次加载
     */
    private boolean isFirst = true;

    /**
     * 对用户是否可见
     */
    private boolean isVisibleToUser;

    /**
     * 控件是否初始化完成
     */
    private boolean isViewInitialized;

    /**
     * 获取布局文件id
     */
    public abstract int getLayoutResId();

    /**
     * 初始化控件
     */
    public abstract void initView(View view);

    /**
     * 初始化数据、获取网络数据
     */
    public abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, getClass().getSimpleName() + ":-------->> onCreate");
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, getClass().getSimpleName() + ":-------->> onCreateView");
        View view = inflater.inflate(getLayoutResId(), container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d(TAG, getClass().getSimpleName() + ":-------->> onActivityCreated");
        isViewInitialized = true;
        prepareInitData();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG, getClass().getSimpleName() + ":-------->> onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d(TAG, getClass().getSimpleName() + ":-------->> onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d(TAG, getClass().getSimpleName() + ":-------->> onDestroyView");
        isFirst = true; //当视图被销毁后应该重新加载
        isViewInitialized = false; //视图需要重新创建
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, getClass().getSimpleName() + ":-------->> onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d(TAG, getClass().getSimpleName() + ":-------->> onSaveInstanceState");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareInitData();
    }

    /**
     * 准备加载数据
     */
    private void prepareInitData() {
        if (isFirst && isViewInitialized && isVisibleToUser) {
            initData();
            isFirst = false;
        }
    }

}
