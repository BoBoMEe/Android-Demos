package com.bobomee.android.retrofit2demo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;

/**
 * Created by bobomee on 16/5/14.
 */
public class AppUtil {

    public static boolean isNetworkReachable(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return mNetworkInfo != null && mNetworkInfo.isAvailable();
        }
        return false;
    }


    public static File getAvailableCacheDir(Context context) {

        return FileUtil.getCacheDir(context);
    }
}
