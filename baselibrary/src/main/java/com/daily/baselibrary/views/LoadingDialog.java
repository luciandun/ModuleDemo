package com.daily.baselibrary.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;

/**
 * description:封装简易加载等待框
 * author: dlx
 * date: 2018/06/05
 * version: 1.0
 */
public class LoadingDialog {

    private static ProgressDialog mDialog;

    public static void show(Activity activity, String msg) {
        show(activity, msg, false);
    }

    public static void show(Activity activity, String msg, boolean cancelable) {
        if (activity == null) return;
        if (mDialog == null) {
            mDialog = new ProgressDialog(activity);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        mDialog.setMessage(TextUtils.isEmpty(msg) ? "加载中^_^..." : msg);
        mDialog.setCancelable(cancelable);
        if (!mDialog.isShowing())
            mDialog.show();
    }

    public static void dismiss() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
        mDialog = null;
    }

}
