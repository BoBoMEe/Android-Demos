package com.bobomee.android.designpatterns.chain;

import android.util.Log;

/**
 * Created on 16/8/18.下午11:43.
 *
 * @author bobomee.
 * @description:
 */
public class Manager extends Leader {
  private static final String TAG = "Manager";
  @Override public int limit() {
    return 10000;
  }

  @Override public void handle(int money) {
    Log.d(TAG, "handle: 经理批复报销--->" + money + "元");
  }
}
