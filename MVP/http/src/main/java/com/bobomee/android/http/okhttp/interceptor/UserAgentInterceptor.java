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

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  Created by bobomee on 16/5/19.
 *
 *  设置User-Agent
 *
 */
public final class UserAgentInterceptor implements Interceptor {
  private static final String USER_AGENT_HEADER_NAME = "User-Agent";
  private final String userAgentHeaderValue;

  public UserAgentInterceptor(String userAgentHeaderValue) {
    this.userAgentHeaderValue = userAgentHeaderValue;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    final Request originalRequest = chain.request();

    final Request requestWithUserAgent = originalRequest.newBuilder()

        //移除先前默认的UA
        .removeHeader(USER_AGENT_HEADER_NAME)

        //设置UA
        .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)


        .build();
    return chain.proceed(requestWithUserAgent);
  }

  /**
   *  Created by bobomee on 16/5/19.
   *
   *  User-Agent帮助类
   */
  public enum  UAHelper {

    INSTANCE;

    private String userAgent = "UA";

    public String getUserAgent() {
      return userAgent;
    }

    public void setUserAgent(String userAgent) {
      this.userAgent = userAgent;
    }
  }
}
