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

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created on 2017/1/20.下午3:52.
 *
 * @author bobomee.
 */

public class WindowManagerUtils {

  public static void fullScreen(Activity pActivity) {
    WindowManager.LayoutParams attrs = pActivity.getWindow().getAttributes();
    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
    pActivity.getWindow().setAttributes(attrs);
    if (android.os.Build.VERSION.SDK_INT >= 14) {
      //noinspection all
      pActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }
  }

  public static void smallScreen(Activity pActivity) {
    WindowManager.LayoutParams attrs = pActivity.getWindow().getAttributes();
    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
    pActivity.getWindow().setAttributes(attrs);
    if (android.os.Build.VERSION.SDK_INT >= 14) {
      //noinspection all
      pActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }
  }
}
