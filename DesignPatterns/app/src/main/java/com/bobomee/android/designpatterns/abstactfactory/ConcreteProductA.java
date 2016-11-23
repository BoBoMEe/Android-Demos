package com.bobomee.android.designpatterns.abstactfactory;

import android.util.Log;

/**
 * Created on 16/9/6.上午7:39.
 *
 * @author bobomee.
 * @description:
 */
public class ConcreteProductA extends AbstractProductA {

  private static final String TAG = "ConcreteProductA";

  @Override public void method() {
    Log.d(TAG, "method: --> 具体产品 A 的方法");
  }
}
