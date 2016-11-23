package com.bobomee.android.retrofit2demo;

import com.bobomee.android.common.app.BaseApplication;
import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.Logger;

/**
 * Created by bobomee on 16/5/12.
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger
                .init("_Retrofit2.0_")                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
//                .hideThreadInfo()               // default shown
//                .logLevel(LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(2)                // default 0
                .logTool(new AndroidLogTool()); // custom log tool, optional
    }
}
