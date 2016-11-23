package com.bobomee.android.retrofit2demo.util;

import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by bobomee on 16/5/21.
 */
public class WebViewSetttings {

    public static void basic(WebView webView) {
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webView.requestFocusFromTouch();
    }
}
