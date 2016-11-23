package com.bobomee.android.designpatterns.chain;

import android.util.Log;

/**
 * Created on 16/8/18.下午11:42.
 *
 * @author bobomee.
 * @description:
 */
public class Director extends Leader {
  private static final String TAG = "Director";
  @Override public int limit() {
    return 5000;
  }

  @Override public void handle(int money) {
    Log.d(TAG, "handle: 主管批复报销--->"+ money + "元");
  }
}
