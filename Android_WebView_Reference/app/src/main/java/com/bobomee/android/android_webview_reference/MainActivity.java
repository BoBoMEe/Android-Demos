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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.bobomee.android.videoenabledwebview.ToggledFullscreenCallback;
import com.bobomee.android.videoenabledwebview.VideoEnabledWebChromeClient;
import com.bobomee.android.videoenabledwebview.VideoEnabledWebChromeClientConfig;
import com.bobomee.android.videoenabledwebview.VideoEnabledWebView;

public class MainActivity extends AppCompatActivity {

  private VideoEnabledWebView webView;
  private VideoEnabledWebChromeClient webChromeClient;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_example);

    // Save the web view
    webView = (VideoEnabledWebView) findViewById(R.id.webView);

    webChromeClient = prepareWebChromeClient();

    webView.setWebChromeClient(webChromeClient);
    // Call private class InsideWebViewClient
    webView.setWebViewClient(new InsideWebViewClient());

    // Navigate anywhere you want, but consider that this classes have only been tested on YouTube's mobile site
    webView.loadUrl("http://www.iqiyi.com/");
  }

  private VideoEnabledWebChromeClient prepareWebChromeClient() {

    // Initialize the VideoEnabledWebChromeClient and set event handlers
    View nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own view, read class comments
    ViewGroup videoLayout =
        (ViewGroup) findViewById(R.id.videoLayout); // Your own view, read class comments
    //noinspection all
    View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video,
        null); // Your own view, read class comments

    VideoEnabledWebChromeClientConfig lVideoEnabledWebChromeClientConfig =
        VideoEnabledWebChromeClientConfig.newBuilder()
            .mNonVideoLayout(nonVideoLayout)
            .mVideoLayout(videoLayout)
            .mLoadingView(loadingView)
            .mWebView(webView)
            .mToggledFullscreenCallback(mToggledFullscreenCallback)
            .build();

    VideoEnabledWebChromeClient webChromeClient = new VideoEnabledWebChromeClient(
        lVideoEnabledWebChromeClientConfig) // See all available constructors...
    {
      // Subscribe to standard events, such as onProgressChanged()...
      @Override public void onProgressChanged(WebView view, int progress) {
        // Your code...
      }
    };

    return webChromeClient;
  }

  private class InsideWebViewClient extends WebViewClient {
    @Override
    // Force links to be opened inside WebView and not in Default Browser
    // Thanks http://stackoverflow.com/a/33681975/1815624
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
      view.loadUrl(url);
      return true;
    }
  }

  @Override public void onBackPressed() {
    // Notify the VideoEnabledWebChromeClient, and handle it ourselves if it doesn't handle it
    if (!webChromeClient.onBackPressed()) {
      if (webView.canGoBack()) {
        webView.goBack();
      } else {
        // Standard back button implementation (for example this could close the app)
        super.onBackPressed();
      }
    }
  }

  private ToggledFullscreenCallback mToggledFullscreenCallback = new ToggledFullscreenCallback() {
    @Override public void toggledFullscreen(boolean fullscreen) {
      // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
      if (fullscreen) {
        WindowManagerUtils.fullScreen(MainActivity.this);
      } else {
        WindowManagerUtils.smallScreen(MainActivity.this);
      }
    }
  };
}
