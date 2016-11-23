/*
 * Android_Common. lastModified by bobomee at 2016.5.16 11:24
 *
 * Copyright (C) 2016 bobomee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bobomee.android.common.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;

import com.bobomee.android.common.app.BaseApplication;


/**
 * UI 工具包 *
 */
public class UIUtil {

    /**
     * 获取到上下文对象 *
     */
    public static Context getContext() {
        return BaseApplication.getBaseApplication();
    }

    /**
     * 获取到主线程Handler对象 *
     */
    public static Handler getMainThreadHandler() {
        return BaseApplication.getMainThreadHandler();
    }

    /**
     * 获取主线程的ID *
     */
    public static long getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    /**
     * 获取主线程 *
     */
    public static Thread getMainThread() {
        return BaseApplication.getMainThread();
    }

    /**
     * 获取主线程 Looper *
     */
    public static Looper getMainThreadLooper() {
        return BaseApplication.getMainThreadLooper();
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getMainThreadHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getMainThreadHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getMainThreadHandler().removeCallbacks(runnable);
    }

    /***
     * 移除所有 的Message And CallBack
     */
    public static  void removeAllMessageAndCallBacks(Object token){
        getMainThreadHandler().removeCallbacksAndMessages(token);
    }

    /**
     * 填充相应id的布局 *
     */
    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    public static int[] getIntegerArray(int resId) {
        return getResources().getIntArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    /**
     * 判断当前的线程是不是在主线程 *
     */
    public static boolean isUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
    /**
     * 在主线程执行 *
     */
    public static void runInMainThread(Runnable runnable) {
        if (isUIThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }



    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final int resId) {
        showToastSafe(getString(resId));
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final String str) {
        if (isUIThread()) {
            showToast(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }

    private static void showToast(String str) {
        ToastUtil.show(getContext(),str);
    }

}
