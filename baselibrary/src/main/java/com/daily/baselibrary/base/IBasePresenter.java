package com.daily.baselibrary.base;

/**
 * description:Presenter的基类
 * author: dlx
 * date: 2018/5/5
 * version: 1.0
 */
public interface IBasePresenter<V extends IBaseView> {

    void attachView(V baseView);

    void onDestroy();
}
