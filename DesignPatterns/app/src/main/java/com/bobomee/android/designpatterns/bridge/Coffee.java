package com.bobomee.android.designpatterns.bridge;

/**
 * Created on 16/9/4.下午10:27.
 *
 * @author bobomee.
 * @description:
 */
public abstract class Coffee {

  protected CoffeeAdditives impl;

  public Coffee(CoffeeAdditives _impl) {
    impl = _impl;
  }

  public abstract void makeCoffee();
}
