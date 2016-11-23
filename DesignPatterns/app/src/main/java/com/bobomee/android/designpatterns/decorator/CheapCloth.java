package com.bobomee.android.designpatterns.decorator;

import android.util.Log;

/**
 * Created on 16/8/28.下午6:10.
 *
 * @author bobomee.
 * @description:
 */
public class CheapCloth extends PersonCloth {
  private static final String TAG = "CheapCloth";

  public CheapCloth(Person _person) {
    super(_person);
  }

  private void dressShorts() {
    Log.d(TAG, "dressShorts: -->" + "穿条短裤");
  }

  @Override public void dressed() {
    super.dressed();
    dressShorts();
  }
}
