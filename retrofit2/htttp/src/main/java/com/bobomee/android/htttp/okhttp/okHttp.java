package com.bobomee.android.htttp.okhttp;

import com.bobomee.android.htttp.okhttp.interceptor.OfflineCacheControlInterceptor;
import com.bobomee.android.htttp.util.CacheUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by bobomee on 16/5/18.
 */
public enum okHttp {

    INSTANCE;

    private final OkHttpClient okHttpClient;

    private static final int TIMEOUT_READ = 15;
    private static final int TIMEOUT_CONNECTION = 15;

    private Interceptor cacheInterceptor = new OfflineCacheControlInterceptor();

    okHttp() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                //打印日志
                .addInterceptor(interceptor)

                //设置Cache目录
                .cache(CacheUtil.getCache())

                //设置缓存
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)

                //失败重连
                .retryOnConnectionFailure(true)

                //time out
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)

                .build()

        ;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

}
