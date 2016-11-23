package com.bobomee.android.designpatterns.chain;

/**
 * Created on 16/8/18.下午11:37.
 *
 * @author bobomee.
 * @description:
 */
public abstract class Leader {

  protected Leader nextHandler;

  public final void handleRequest(int money) {
    if (money < limit()) {
      handle(money);
    } else {
      if (null != nextHandler) {
        nextHandler.handleRequest(money);
      }
    }
  }

  public abstract int limit();

  public abstract void handle(int money);
}
