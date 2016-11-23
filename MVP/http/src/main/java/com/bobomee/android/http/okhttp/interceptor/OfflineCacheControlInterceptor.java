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

package com.bobomee.android.http.okhttp.interceptor;

import com.bobomee.android.http.util.HttpNetUtil;
import java.io.IOException;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by bobomee on 16/5/15.
 *
 * 离线读取本地缓存，在线获取最新数据(读取单个请求的请求头，亦可统一设置)
 */
public class OfflineCacheControlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!HttpNetUtil.INSTANCE.isConnected()) {
            request = request.newBuilder()
                    //强制使用缓存
                    .cacheControl(CacheControl.FORCE_CACHE).build();
        }

        Response response = chain.proceed(request);

        if (HttpNetUtil.INSTANCE.isConnected()) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")//移除干扰信息
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale="+maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
    }
}

