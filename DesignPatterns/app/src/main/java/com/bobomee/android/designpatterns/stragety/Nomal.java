package com.bobomee.android.designpatterns.stragety;

/**
 * Created on 16/8/16.下午11:10.
 *
 * @author bobomee.
 * @description:
 */
public class Nomal {

  private static final int BUS = 1;
  private static final int SUBWAY = 2;

  public static void main(String[] args) {
    Nomal nomal = new Nomal();

    System.out.println("BUS--->" + nomal.calculate(3, BUS));
    System.out.println("SUBWAY--->" + nomal.calculate(3, SUBWAY));
  }

  private int bus(int km) {
    return 10 * km;
  }

  private int subWay(int km) {
    return 5 * km;
  }

  int calculate(int km, int type) {
    if (type == BUS) {
      return bus(km);
    } else {
      return subWay(km);
    }
  }
}
