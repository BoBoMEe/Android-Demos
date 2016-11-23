package com.bobomee.android.designpatterns.factorymethod;

import android.util.Log;

/**
 * Created on 16/9/5.下午11:31.
 *
 * @author bobomee.
 * @description:
 */
public class ConcreteProductA extends Product {

  private static final String TAG = "ConcreteProductA";

  @Override public void method() {
    Log.d(TAG, "method: -->我是具体的产品A");
  }
}
