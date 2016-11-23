package com.bobomee.android.designpatterns.template;

import android.util.Log;

/**
 * Created on 16/8/26.下午11:43.
 *
 * @author bobomee.
 * @description:
 */
public class CoderCumputer extends AbsComputer {

  private static final String TAG = "CoderCumputer";
  @Override protected void login() {
    Log.d(TAG, "login: "+ "程序员登录系统");
  }
}
