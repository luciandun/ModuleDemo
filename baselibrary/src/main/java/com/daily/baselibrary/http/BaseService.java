package com.daily.baselibrary.http;

import android.support.v4.util.ArrayMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * description: 实际开发该文件应该写在app里面而不是module里
 * author: dlx
 * date: 2018-05-09
 * version: 1.0
 */
public interface BaseService {

    @POST("res/test/update")
    Observable<HttpResp<String>> testUpdate(@Body ArrayMap params);
}
