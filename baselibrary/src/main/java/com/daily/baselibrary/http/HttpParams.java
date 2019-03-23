package com.daily.baselibrary.http;

/**
 * description: http请求参数基类
 * author: dlx
 * date: 2018/5/4
 * version: 1.0
 */
public abstract class HttpParams {

    protected static int DEFAULT_PAGE_SIZE = 10;
    protected static int HTTP_TIME_OUT = 15;

    public abstract String getBaseUrl();


}
