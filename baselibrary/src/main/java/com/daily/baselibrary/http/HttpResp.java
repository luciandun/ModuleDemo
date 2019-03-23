package com.daily.baselibrary.http;

import com.google.gson.annotations.SerializedName;

/**
 * description:服务器返回参数基类
 * author: dlx
 * date: 2018/5/4
 * version: 1.0
 */
public  class HttpResp<T> {

    private int RESULT_OK = 0;
    /**
     * 错误码
     */
    @SerializedName("errCode")
    private int errCode;
    /**
     * 错误描述
     */
    @SerializedName("errMsg")
    private int errMsg;
    /**
     * 数据
     */
    @SerializedName("data")
    private T data;


    public int getErrCode() {
        return errCode;
    }

    public int getErrMsg() {
        return errMsg;
    }

    public T getData() {
        return data;
    }



    public boolean isOk() {
        return errCode == RESULT_OK;
    }

    @Override
    public String toString() {
        return "HttpResp{" +
                ", errCode=" + errCode +
                ", errMsg=" + errMsg +
                ", data=" + data +
                '}';
    }
}
