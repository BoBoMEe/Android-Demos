package com.bobomee.android.designpatterns.abstactfactory;

import android.util.Log;

/**
 * Created on 16/9/6.上午7:41.
 *
 * @author bobomee.
 * @description:
 */
public class ConcreteProductB extends AbstractProductB {
  private static final String TAG = "ConcreteProductB";
  @Override public void method() {
    Log.d(TAG, "method: --> 具体产品 B 的方法");
  }
}
