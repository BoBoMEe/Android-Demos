package com.bobomee.android.designpatterns.decorator;

import android.util.Log;

/**
 * Created on 16/8/28.下午6:05.
 *
 * @author bobomee.
 * @description:
 */
public class Boy extends Person {
  private static final String TAG = "Boy";
  @Override public void dressed() {
    Log.d(TAG, "dressed: --->"+"穿了内衣内裤");
  }
}
