/*
 * Copyright (C) 2016.  BoBoMEe(wbwjx115@gmail.com)
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

package com.bobomee.android.common.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import java.util.Locale;

/**
 * Created on 16/7/10.上午12:14.
 *
 * @author bobomee.
 *         wbwjx115@gmail.com
 */
public class LanguageUtil {

  /**
   * 切换语言
   * @param context
   * @param locale
   */
  public static void switchLanguage(Context context, Locale locale) {
    Resources resources = context.getResources();// 获得res资源对象
    Configuration config = resources.getConfiguration();// 获得设置对象
    DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
    config.locale = locale; // 简体中文
    resources.updateConfiguration(config, dm);
  }


  /**
   * 获取系统当前的语言的国家
   *
   * @return CN 中国
   * EN english
   * *
   */
  public static String getCurrentLaunge(Context context) {
    // 获取系统默认的语言国家
    return context.getResources().getConfiguration().locale.getCountry();
  }

}
