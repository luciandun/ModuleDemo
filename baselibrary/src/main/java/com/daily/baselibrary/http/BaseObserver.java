package com.daily.baselibrary.http;

import com.daily.baselibrary.utils.ToastUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * description:对数据做底层统一处理
 * author: dlx
 * date: 2018/5/4
 * version: 1.0
 */
public abstract class BaseObserver<T> implements Observer<HttpResp<T>> {

    public abstract void next(T t);

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(HttpResp<T> tHttpResp) {
        if (tHttpResp.isOk()) {
            next(tHttpResp.getData());
        } else {
            ToastUtil.show("(" + tHttpResp.getErrCode() + "," + tHttpResp.getErrMsg() + ")");
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof JsonParseException ||
                e instanceof JSONException ||
                e instanceof ParseException) {
            onException(ExceptionType.PARSE_ERROR);
        } else if (e instanceof HttpException ||
                e instanceof ConnectException ||
                e instanceof UnknownHostException ||
                e instanceof UnknownServiceException ||
                e instanceof InterruptedIOException) {
            onException(ExceptionType.NETWORK_ERROR);
        } else {
            onException(ExceptionType.UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * 异常统一处理
     *
     * @param et
     */
    public void onException(ExceptionType et) {
        switch (et) {
            case PARSE_ERROR:
                ToastUtil.show("数据解析错误");
                break;
            case NETWORK_ERROR:
                ToastUtil.show("网络连接错误");
                break;
            case UNKNOWN_ERROR:
            default:
                ToastUtil.show("未知错误");
                break;
        }
    }

    /**
     * 异常定义
     */
    private enum ExceptionType {

        /**
         * 解析错误
         */
        PARSE_ERROR,
        /**
         * 网络错误
         */
        NETWORK_ERROR,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
