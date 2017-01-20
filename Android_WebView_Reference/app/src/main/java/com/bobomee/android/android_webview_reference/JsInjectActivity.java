/*
 * Copyright (C) 2017.  BoBoMEe(wbwjx115@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.bobomee.android.android_webview_reference;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created on 2017/1/20.下午10:07.
 *
 * @author bobomee.
 */

public class JsInjectActivity extends AppCompatActivity {
  private WebView webView;

  @SuppressLint({ "SetJavaScriptEnabled", "AddJavascriptInterface" }) @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.js_inject_activity);
    this.webView = (WebView) findViewById(R.id.webView);

    WebSettings lSettings = webView.getSettings();
    lSettings.setJavaScriptEnabled(true);

    webView.loadUrl("http://v.qq.com/iframe/player.html?vid=o0318tp1ddw&tiny=0&auto=0");

    webView.setWebViewClient(new WebViewClient() {
      @Override public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        view.loadUrl(BrowserJsInject.fullScreenByJs(url));
      }
    });

    webView.addJavascriptInterface(new Object() {

      @JavascriptInterface public void playing() {
        Toast.makeText(JsInjectActivity.this, "点击了缩放按钮", Toast.LENGTH_SHORT).show();
      }
    }, "local_obj");
  }

  public static class BrowserJsInject {

    /**
     * Js注入
     *
     * @param url 加载的网页地址
     * @return 注入的js内容，若不是需要适配的网址则返回空javascript
     */
    public static String fullScreenByJs(String url) {
      String refer = referParser(url);
      if (null != refer) {
        return "javascript:document.getElementsByClassName('"
            + referParser(url)
            + "')[0].addEventListener('click',function(){local_obj.playing();return false;});";
      } else {
        return "javascript:";
      }
    }

    /**
     * 对不同的视频网站分析相应的全屏控件
     *
     * @param url 加载的网页地址
     * @return 相应网站全屏按钮的class标识
     */
    public static String referParser(String url) {
      if (url.contains("letv")) {
        return "hv_ico_screen";               //乐视Tv
      } else if (url.contains("youku")) {
        return "x-zoomin";                    //优酷
      } else if (url.contains("bilibili")) {
        return "icon-widescreen";             //bilibili
      } else if (url.contains("qq")) {
        return "tvp_fullscreen_button";       //腾讯视频
      }

      return null;
    }
  }
}
