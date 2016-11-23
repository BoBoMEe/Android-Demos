package com.bobomee.android.designpatterns.facade;

import android.util.Log;

/**
 * Created on 16/9/1.下午11:48.
 *
 * @author bobomee.
 * @description:
 */
public class PhoneImpl implements Phone {

  private static final String TAG = "PhoneImpl";

  @Override public void dail() {
    Log.d(TAG, "dail: --> 打电话");
  }

  @Override public void hangup() {
    Log.d(TAG, "hangup: --> 挂断");
  }
}
