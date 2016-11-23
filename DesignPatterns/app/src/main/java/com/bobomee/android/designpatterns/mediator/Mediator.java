package com.bobomee.android.designpatterns.mediator;

/**
 * Created on 16/8/27.下午6:06.
 *
 * @author bobomee.
 * @description:
 */
public abstract class Mediator {
  /**
   * 同事对象改变时通知中介者的方法
   * 在同事对象改变时由中介者去通知其他同事对象
   */
  public abstract void changed(Colleague _colleague);
}
