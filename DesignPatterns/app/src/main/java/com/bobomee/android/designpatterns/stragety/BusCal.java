package com.bobomee.android.designpatterns.stragety;

/**
 * Created on 16/8/16.下午11:18.
 *
 * @author bobomee.
 * @description:
 */
public class BusCal implements Calculate {
  @Override public int calculate(int km) {
    return 10*km;
  }
}
