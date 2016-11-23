package com.bobomee.android.designpatterns.bridge;

import android.util.Log;

/**
 * Created on 16/9/4.下午10:33.
 *
 * @author bobomee.
 * @description:
 */
public class SmallCoffee extends Coffee {
  private static final String TAG = "SmallCoffee";
  public SmallCoffee(CoffeeAdditives _impl) {
    super(_impl);
  }

  @Override public void makeCoffee() {
    Log.d(TAG, "makeCoffee: -->小杯的"+impl.addSomething()+"咖啡");
  }
}
