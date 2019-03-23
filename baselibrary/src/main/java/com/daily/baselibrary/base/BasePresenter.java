package com.daily.baselibrary.base;

/**
 * description:
 * author: dlx
 * date: 2018-05-30
 * version: 1.0
 */
public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    private V mBaseView;

    @Override
    public void attachView(V baseView) {
        this.mBaseView = baseView;
    }

    @Override
    public void onDestroy() {
        this.mBaseView = null;
    }

    /**
     * 获得View
     *
     * @return
     */
    public V getView() {
        return mBaseView;
    }

    /**
     * 检查View是否存在
     *
     * @return
     */
    public boolean isAttachView() {
        return mBaseView != null;
    }

    public void checkView() {
        if (!isAttachView()) {
            throw new MVPViewNullException();
        }
    }

    private static class MVPViewNullException extends RuntimeException {
        private MVPViewNullException() {
            super("please call attachView() to build connection with View before getting data!");
        }
    }
}
