package com.bobomee.blogdemos.js_native;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.base.BaseActivity;


/**
 * @author：BoBoMEe Created at 2016/1/5.
 */
public class JsJavaInteractive extends BaseActivity {
    private WebView mWebView;
    private Button mButton;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.js_native_layout);
        mWebView = (WebView) findViewById(R.id.webview);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:android_call_js_no_parameter()");
            }

        });
        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mWebView.evaluateJavascript("button2click()",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {
                                    mWebView.loadUrl("javascript:android_call_js_parameter('" + value + "')");
                                }
                            });
                } else {
                    mWebView.loadUrl("javascript:button2click1()");
                }
            }
        });
        webViewSetting();
    }

    private void webViewSetting() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.loadUrl("file:///android_asset/js.html");


        mWebView.addJavascriptInterface(this, "js");
    }

    @JavascriptInterface
    public void comeplete() {
        finish();
    }

    @JavascriptInterface
    public void showResult(String s){
        Toast.makeText(this, s + "", Toast.LENGTH_LONG).show();
    }
}
