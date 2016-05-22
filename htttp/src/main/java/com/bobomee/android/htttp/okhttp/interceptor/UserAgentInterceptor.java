package com.bobomee.android.htttp.okhttp.interceptor;

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
