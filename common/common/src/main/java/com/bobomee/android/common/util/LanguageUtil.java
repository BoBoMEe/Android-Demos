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
