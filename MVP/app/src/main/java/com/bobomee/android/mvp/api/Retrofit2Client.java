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

import com.bobomee.android.http.retrofit2.converfactory.StringConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bobomee on 2016/5/19.
 */
public enum Retrofit2Client {

  INSTANCE;

  private final Retrofit.Builder retrofitBuilder;

  // @formatter:off
  final Gson gson =
      new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();
  // @formatter:on

  Retrofit2Client() {
    retrofitBuilder = new Retrofit.Builder()
        //设置OKHttpClient
        .client(okHttp.INSTANCE.getOkHttpClient())

        //Rx
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

        //String转换器
        .addConverterFactory(StringConverterFactory.create())

        //gson转化器
        .addConverterFactory(GsonConverterFactory.create(gson));
  }

  public Retrofit.Builder getRetrofitBuilder() {
    return retrofitBuilder;
  }

}
