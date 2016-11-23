package com.bobomee.android.designpatterns.visitor;

import java.util.Random;

/**
 * Created on 16/8/27.下午1:11.
 *
 * @author bobomee.
 * @description:
 */
public abstract class Staff {

  public String name;
  public int kpi;

  public Staff(String _name) {
    name = _name;
    kpi = new Random().nextInt(10);
  }

  public abstract void accept(Visitor _visitor);
}
