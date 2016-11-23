package com.bobomee.android.designpatterns.bridge;

import android.util.Log;

/**
 * Created on 16/9/4.下午10:31.
 *
 * @author bobomee.
 * @description:
 */
public class LargeCoffee extends Coffee {

  private static final String TAG = "LargeCoffee";

  public LargeCoffee(CoffeeAdditives _impl) {
    super(_impl);
  }

  @Override public void makeCoffee() {
    Log.d(TAG, "makeCoffee: --> 大杯的"+impl.addSomething()+"咖啡");
  }
}
