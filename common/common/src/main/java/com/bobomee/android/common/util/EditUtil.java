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

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.reflect.Field;

/**
 * Created on 16/8/12.下午12:29.
 *
 * @author bobomee.
 * @description:
 */
public class EditUtil {

  /**
   * 密码形式的EditTextView显示
   */
  public static void passEditText(EditText password) {
    password.setTypeface(Typeface.DEFAULT);
    password.setTransformationMethod(new PasswordTransformationMethod());
  }

  /**
   * 密码的显示与隐藏
   */
  public static void visible(TextView textView, boolean show) {

    textView.setTransformationMethod(show ? HideReturnsTransformationMethod.getInstance()
        : PasswordTransformationMethod.getInstance());

  }
  /**
   * EditText 背景着色
   */
  public static void tintEditText(EditText editText, int color) {

    final Drawable originalDrawable = editText.getBackground();
    final Drawable wrappedDrawable = tintDrawable(originalDrawable, ColorStateList.valueOf(color));
    editText.setBackgroundDrawable(wrappedDrawable);
  }

  private static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
    final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
    DrawableCompat.setTintList(wrappedDrawable, colors);
    return wrappedDrawable;
  }

  public static void tintCursorDrawable(EditText editText, int color) {
    try {
      Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
      fCursorDrawableRes.setAccessible(true);
      int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
      Field fEditor = TextView.class.getDeclaredField("mEditor");
      fEditor.setAccessible(true);
      Object editor = fEditor.get(editText);
      Class<?> clazz = editor.getClass();
      Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
      fCursorDrawable.setAccessible(true);

      if (mCursorDrawableRes <= 0) {
        return;
      }

      Drawable cursorDrawable = editText.getContext().getResources().getDrawable(mCursorDrawableRes);
      if (cursorDrawable == null) {
        return;
      }

      Drawable tintDrawable  = tintDrawable(cursorDrawable, ColorStateList.valueOf(color));
      Drawable[] drawables = new Drawable[] {tintDrawable, tintDrawable};
      fCursorDrawable.set(editor, drawables);
    } catch (Throwable ignored) {
    }
  }

}
