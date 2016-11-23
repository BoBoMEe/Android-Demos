/*
 *
 *    Copyright (C) 2016 BoBoMEe(wbwjx115@gmail.com)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.bobomee.android.mvp.api;

import com.bobomee.android.http.okhttp.interceptor.OfflineCacheControlInterceptor;
import com.bobomee.android.http.util.CacheUtil;
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
