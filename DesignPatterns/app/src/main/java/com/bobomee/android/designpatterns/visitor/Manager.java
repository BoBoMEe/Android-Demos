package com.bobomee.android.designpatterns.visitor;

import java.util.Random;

/**
 * Created on 16/8/27.下午1:15.
 *
 * @author bobomee.
 * @description:
 */
public class Manager extends Staff {
  public Manager(String _name) {
    super(_name);
  }

  @Override public void accept(Visitor _visitor) {
    _visitor.visit(this);
  }

  public int getProducts() {
    return new Random().nextInt(10);
  }
}
