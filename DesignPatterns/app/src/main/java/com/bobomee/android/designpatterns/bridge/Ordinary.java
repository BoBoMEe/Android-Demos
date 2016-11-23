package com.bobomee.android.designpatterns.bridge;

/**
 * Created on 16/9/4.下午10:36.
 *
 * @author bobomee.
 * @description:
 */
public class Ordinary extends CoffeeAdditives {
  @Override public String addSomething() {
    return "原味";
  }
}
