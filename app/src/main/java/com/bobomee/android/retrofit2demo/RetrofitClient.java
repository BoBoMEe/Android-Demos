package com.bobomee.android.retrofit2demo;

import android.content.Context;

import com.bobomee.android.retrofit2demo.github.GithubApi;
import com.bobomee.android.retrofit2demo.util.AppUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bobomee on 16/5/14.
 */
public class RetrofitClient {

    private Context sContext;

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
    private Retrofit retrofit;

    private Cache cache() {
        //设置缓存路径
        final File baseDir = AppUtil.getAvailableCacheDir(sContext);
        final File cacheDir = new File(baseDir, "HttpResponseCache");
        //设置缓存 10M
        return new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }

    private Interceptor cacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!AppUtil.isNetworkReachable(sContext)) {
                    request = request.newBuilder()
                            //强制使用缓存
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                Response response = chain.proceed(request);

                if (AppUtil.isNetworkReachable(sContext)) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    String cacheControl = request.cacheControl().toString();
                    Logger.i("has network ,cacheControl=" + cacheControl);
                    return response.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    Logger.i("network error ,maxStale="+maxStale);
                    return response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale="+maxStale)
                            .removeHeader("Pragma")
                            .build();
                }

            }
        };
    }

    private final Interceptor loggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Logger.i(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Logger.i(String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    };

    private OkHttpClient client() {

        //Log
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(/*httpLoggingInterceptor*/loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(cacheInterceptor())
                .addInterceptor(cacheInterceptor())
                .cache(cache())
                .build();

        return client;
    }


    // @formatter:off
    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();
    // @formatter:on


    public RetrofitClient() {

        this.sContext = App.sContext;

        retrofit = new Retrofit.Builder()
                .baseUrl(GithubApi.BASE_URL)
                .client(client())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }


    public Retrofit getRetrofit() {
        return retrofit;
    }
}
