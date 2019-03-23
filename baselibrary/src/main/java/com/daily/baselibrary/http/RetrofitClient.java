package com.daily.baselibrary.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.daily.baselibrary.base.BaseApp;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * description:
 * author: dlx
 * date: 2018-05-09
 * version: 1.0
 */
public class RetrofitClient {

    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;
    private static BaseService apiSrvice;

    private RetrofitClient() {

    }

    private static final class RetrofitHolder {
        private static final RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient instance() {
        return RetrofitHolder.INSTANCE;
    }


    public RetrofitClient initRetrofit(String baseUrl) {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(createOKHttpClient())
                    .build();
        }
        return this;
    }

    private OkHttpClient createOKHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(HttpParams.HTTP_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(HttpParams.HTTP_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(HttpParams.HTTP_TIME_OUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true) //默认1次
                    .addInterceptor(createHeadInterceptor())
                    .addInterceptor(createLogInterceptor())
                    .addInterceptor(createOffLineCacheInterceptor())
                    .addNetworkInterceptor(createOnLineCacheInterceptor())
                    .cache(new Cache(new File("cachePath"), 1024 * 1024 * 100)) //100M
                    .build();
        }
        return mOkHttpClient;
    }

    /**
     * 创建在线缓存策略
     *
     * @return
     */
    private Interceptor createOnLineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                int aliveTime = 30; //30s存活时间
                Request.Builder builder = chain.request().newBuilder();
                builder.removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + aliveTime);
                return chain.proceed(builder.build());
            }
        };
    }

    /**
     * 创建离线缓存策略
     *
     * @return
     */
    private Interceptor createOffLineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                if (!isNetworkOk(BaseApp.getAppContext())){
                    int aliveTime = 7 * 24 * 60 * 60; //1周有效期
                    CacheControl control = new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(aliveTime,TimeUnit.SECONDS)
                            .build();
                    builder.cacheControl(control);
                }
                return chain.proceed(builder.build());
            }
        };
    }

    /**
     * 创建HTTP头部信息
     *
     * @return
     */
    private Interceptor createHeadInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                String params1 = null;
                String params2 = null;
                String params3 = null;

                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("params1Name", params1);
                builder.addHeader("params2Name", params2);
                builder.addHeader("params3Name", params3);
                return chain.proceed(builder.build());
            }
        };
        return headerInterceptor;
    }

    /**
     * 创建日志打印拦截器
     *
     * @return
     */
    private HttpLoggingInterceptor createLogInterceptor() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logInterceptor;
    }

    @Deprecated
    public RetrofitClient changeHost(String host) {

        return this;
    }

    @Deprecated
    public RetrofitClient addHttpHeader(String key, String value) {
        return this;
    }


    public BaseService createService() {
        return mRetrofit.create(BaseService.class);
    }

    /**
     * 检查网络需要使用权限“ACCESS_NETWORK_STATE”
     *
     * @param context
     * @return
     */
    private boolean isNetworkOk(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
