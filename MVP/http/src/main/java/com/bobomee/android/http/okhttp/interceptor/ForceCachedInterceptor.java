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

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by bobomee on 16/5/15.
 *
 * 读取请求头的cacheControl，否则执行默认cacheControl
 */
public class ForceCachedInterceptor implements Interceptor {

    private static final int MAX_AGE = 60;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        /**
         * 读取请求头中配置的CacheControl
         *
         *  @Headers("Cache-Control: max-age=640000")
         *  @GET("widget/list")
         *  Call<List<Widget>> widgetList();
         *
         */

        String cacheControl = request.cacheControl().toString();

        if (TextUtils.isEmpty(cacheControl)) {
            cacheControl = "public, max-age=" + MAX_AGE;
        }

        Response response = chain.proceed(request);

        //将缓存设置到响应中
        return response.newBuilder()
                .header("Cache-Control", cacheControl)
                .removeHeader("Pragma") //移除干扰信息
                .build();
    }
}
