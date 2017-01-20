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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created on 2017/1/20.下午10:01.
 *
 * @author bobomee.
 */

public class NavigateActivity extends AppCompatActivity {

  private android.widget.Button VideoEnabledWebView;
  private android.widget.Button JsInjectVideoEnabledWebView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.navigate_activity_layout);
    this.JsInjectVideoEnabledWebView = (Button) findViewById(R.id.JsInjectVideoEnabledWebView);
    this.VideoEnabledWebView = (Button) findViewById(R.id.VideoEnabledWebView);

    VideoEnabledWebView.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        start(MainActivity.class);
      }
    });

    JsInjectVideoEnabledWebView.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        start(JsInjectActivity.class);
      }
    });
  }

  private void start(Class pClass) {
    Intent lIntent = new Intent(this, pClass);
    startActivity(lIntent);
  }
}
