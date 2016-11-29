package com.bobomee.android.common.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by bobomee on 2016/5/20.
 *
 * <pre>
 *
 *   1. 为了让webview点击link能够下载，需要添加 {@link android.webkit.DownloadListener}
 *   2. WebView 重定向判断可以用 {@link android.webkit.WebView#getHitTestResult()}
 *   3. HTTPS 页面中的 HTTP 元素不显示，如图片，可以用 {@link android.webkit.WebSettings#setMixedContentMode(int)}
 *   4. Webview 开启硬件加速会出现各种坑，可以在 {@link WebViewClient#onPageStarted(android.webkit.WebView, java.lang.String, android.graphics.Bitmap)}中关闭硬件加速，在
 *   {@link WebViewClient#onPageFinished(android.webkit.WebView, java.lang.String)}中开启硬件加速
 *
 * </pre>
 */
public class WebViewUtil {

  @SuppressLint("SetJavaScriptEnabled") public static void basicSetting(WebView webView) {
    WebSettings webSettings = webView.getSettings();

    webSettings.setJavaScriptEnabled(true);
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    webSettings.setDefaultTextEncodingName("utf-8");
    // 图片和DOM分别加载
    webSettings.setBlockNetworkImage(false);
    webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

    webView.requestFocusFromTouch();
    webSettings.setDomStorageEnabled(true);
    webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

    //http,https 混合使用
    // http://developer.android.com/reference/android/webkit/WebSettings.html#setMixedContentMode(int)
    if (Build.VERSION.SDK_INT >= 21) {
      webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }
  }

  public static void wideViewPortSetting(WebView webView) {

    basicSetting(webView);

    WebSettings webSettings = webView.getSettings();

    //设置此属性，可任意比例缩放
    webSettings.setUseWideViewPort(true);
    webSettings.setLoadWithOverviewMode(true);
    // 支持双指缩放
    webSettings.setBuiltInZoomControls(true);
  }

  public static void downloadListener(WebView webView, DownloadListener downloadListener) {
    webView.setDownloadListener(downloadListener);
  }

  // 智能开启硬件加速
  public static void refreshHardwareAccelerationSetting(WebView _webView) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
        && _webView.getHeight() < 4096
        && _webView.getWidth() < 4096) {
      _webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    } else {
      _webView.setLayerType(View.LAYER_TYPE_NONE, null);
    }
  }

  // 关闭硬件加速
  public static void closeHardwareAcceleration(WebView _webView) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      _webView.setLayerType(View.LAYER_TYPE_NONE, null);
    }
  }

  public static void releaseWebViewResource(@Nullable WebView webView) {
    if (webView != null) {
      webView.removeAllViews();
      ((ViewGroup) webView.getParent()).removeView(webView);
      webView.setTag(null);
      webView.clearHistory();
      webView.destroy();
      webView = null;
    }
  }

  public static void onPageStarted(WebView _webView) {
    closeHardwareAcceleration(_webView);
  }

  public static void onPageFinished(WebView _webView) {
    refreshHardwareAccelerationSetting(_webView);
  }

  public static void onDestory(WebView _webView) {
    releaseWebViewResource(_webView);
  }
}
