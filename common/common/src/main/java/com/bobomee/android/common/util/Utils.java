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

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created on 2016/12/28.下午1:13.
 *
 * @author bobomee.
 */

public class Utils {

  private Utils() {
  }

  /**
   * API 11
   *
   * @see Build.VERSION_CODES#HONEYCOMB
   */
  public static boolean hasHoneycomb() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  /**
   * API 14
   *
   * @see Build.VERSION_CODES#ICE_CREAM_SANDWICH
   */
  public static boolean hasIceCreamSandwich() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
  }

  /**
   * API 16
   *
   * @see Build.VERSION_CODES#JELLY_BEAN
   */
  public static boolean hasJellyBean() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
  }

  /**
   * API 19
   *
   * @see Build.VERSION_CODES#KITKAT
   */
  public static boolean hasKitkat() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
  }

  /**
   * API 21
   *
   * @see Build.VERSION_CODES#LOLLIPOP
   */
  public static boolean hasLollipop() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
  }

  /**
   * API 23
   *
   * @see Build.VERSION_CODES#M
   */
  public static boolean hasMarshmallow() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
  }

  /**
   * Adjusts the alpha of a color.
   *
   * @param color the color
   * @param alpha the alpha value we want to set 0-255
   * @return the adjusted color
   */
  public static int adjustAlpha(@ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
    return (alpha << 24) | (color & 0x00ffffff);
  }
  /**
   * Show Soft Keyboard with new Thread
   */
  public static void hideSoftInput(final Activity activity) {
    if (activity.getCurrentFocus() != null) {
      new Runnable() {
        public void run() {
          InputMethodManager imm =
              (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
      }.run();
    }
  }

  /**
   * Hide Soft Keyboard from Dialogs with new Thread
   */
  public static void hideSoftInputFrom(final Context context, final View view) {
    new Runnable() {
      @Override public void run() {
        InputMethodManager imm =
            (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      }
    }.run();
  }

  /**
   * Show Soft Keyboard with new Thread
   */
  public static void showSoftInput(final Context context, final View view) {
    new Runnable() {
      @Override public void run() {
        InputMethodManager imm =
            (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
      }
    }.run();
  }
}
