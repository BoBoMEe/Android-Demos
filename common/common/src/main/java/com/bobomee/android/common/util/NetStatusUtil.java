package com.bobomee.android.common.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 获取网络状态链接
 */
public class NetStatusUtil {

  // 判断网络连接状态
  public static boolean isNetworkConnected(Context context) {
    if (context != null) {
      ConnectivityManager mConnectivityManager =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null) {
        return mNetworkInfo.isAvailable();
      }
    }
    return false;
  }

  // 判断wifi状态
  public static boolean isWifiConnected(Context context) {
    if (context != null) {
      // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
      ConnectivityManager manager =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      // 获取NetworkInfo对象
      NetworkInfo networkInfo = manager.getActiveNetworkInfo();
      //判断NetworkInfo对象是否为空 并且类型是否为WIFI
      if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
        return networkInfo.isAvailable();
      }
    }
    return false;
  }

  // 判断移动网络
  public static boolean isMobileConnected(Context context) {
    if (context != null) {
      //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
      ConnectivityManager manager =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      //获取NetworkInfo对象
      NetworkInfo networkInfo = manager.getActiveNetworkInfo();
      //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
      if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
        return networkInfo.isAvailable();
      }
    }
    return false;
  }

  /**
   * 设置网络
   */
  public static void startToSettings(Context paramContext) {
    if (paramContext == null) return;
    Intent intent;
    //判断手机系统的版本  即API大于13 就是3.2或以上版本

    if (android.os.Build.VERSION.SDK_INT > 13) {
      intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
    }
    if (android.os.Build.VERSION.SDK_INT > 10) {
      intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
    } else {
      intent = new Intent();
      ComponentName component =
          new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
      intent.setComponent(component);
      intent.setAction("android.intent.action.VIEW");
    }
    if (intent.resolveActivity(paramContext.getPackageManager()) != null) {
      paramContext.startActivity(intent);
    } else {
      paramContext.startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
    }
  }

  // 获取连接类型
  public static int getConnectedType(Context context) {
    if (context != null) {
      ConnectivityManager mConnectivityManager =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
      if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
        return mNetworkInfo.getType();
      }
    }
    return -1;
  }
}
