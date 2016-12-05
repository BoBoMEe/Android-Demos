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

package com.bobomee.android.common.app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import com.bobomee.android.common.util.CrashHandlerUtil;
import com.bobomee.android.common.util.DisplayUtil;

/**
 * @author&#xff1a;BoBoMEe Created at 2016/1/18.
 */
public class BaseApplication extends Application {

    private static BaseApplication mApplication;
    private static Handler mMainThreadHandler;
    private static Thread mMainThread;
    private static long mMainThreadId;
    private static Looper mMainThreadLooper;


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        mMainThreadHandler = new Handler();
        mMainThreadLooper = getMainLooper();
        mMainThread = mMainThreadLooper.getThread();
        mMainThreadId = mMainThread.getId();

        //屏幕初始化
        DisplayUtil.init(this);

        //异常捕获
        CrashHandlerUtil crashHandlerUtil = CrashHandlerUtil.getInstance();
        crashHandlerUtil.init(this);
    }

    /**
     * 获取上下文 *
     */
    public static BaseApplication getBaseApplication() {
        return mApplication;
    }

    /**
     * 获取主线程Handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程 *
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程id *
     */
    public static long getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取到主线程的Looper *
     */
    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

}
