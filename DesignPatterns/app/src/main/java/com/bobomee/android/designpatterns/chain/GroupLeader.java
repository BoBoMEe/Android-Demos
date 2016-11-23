package com.bobomee.android.designpatterns.chain;

import android.util.Log;

/**
 * Created on 16/8/18.下午11:41.
 *
 * @author bobomee.
 * @description:
 */
public class GroupLeader extends Leader {
  private static final String TAG = "GroupLeader";
  @Override public int limit() {
    return 1000;
  }

  @Override public void handle(int money) {
    Log.d(TAG, "handle: 组长批复报销--->" + money + "元");
  }
}
