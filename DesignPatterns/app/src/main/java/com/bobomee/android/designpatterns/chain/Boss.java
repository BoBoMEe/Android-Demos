package com.bobomee.android.designpatterns.chain;

import android.util.Log;

/**
 * Created on 16/8/18.下午11:44.
 *
 * @author bobomee.
 * @description:
 */
public class Boss extends Leader {
  private static final String TAG = "Boss";
  @Override public int limit() {
    return Integer.MAX_VALUE;
  }

  @Override public void handle(int money) {
    Log.d(TAG, "handle: 老板批复报销--->"+ money + "元");
  }
}
