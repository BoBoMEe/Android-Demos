package com.bobomee.android.designpatterns.factorymethod;

import android.util.Log;

/**
 * Created on 16/9/5.下午11:41.
 *
 * @author bobomee.
 * @description:
 */
public class ConcreteProductB extends Product {
  private static final String TAG = "ConcreteProductB";
  @Override public void method() {
    Log.d(TAG, "method: -->我是具体的产品B");
  }
}
