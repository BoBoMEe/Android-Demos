package com.bobomee.android.designpatterns.visitor;

import java.util.Random;

/**
 * Created on 16/8/27.下午1:14.
 *
 * @author bobomee.
 * @description:
 */
public class Engineer extends Staff {
  public Engineer(String _name) {
    super(_name);
  }

  @Override public void accept(Visitor _visitor) {
      _visitor.visit(this);
  }

  public int getCoceLines(){
    return new Random().nextInt(10*10000);
  }
}
