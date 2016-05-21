package com.bobomee.android.htttp.okhttp.interceptor;

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
