package com.bobomee.android.designpatterns.template;

import android.util.Log;

/**
 * Created on 16/8/26.下午11:51.
 *
 * @author bobomee.
 * @description:
 */
public class MilitaryComputer extends AbsComputer {

  private static final String TAG = "MilitaryComputer";

  @Override protected void login() {
    Log.d(TAG, "login: --->"+ "军用计算机 进入系统");
  }
}
