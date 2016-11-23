package com.bobomee.android.designpatterns.mediator;

/**
 * Created on 16/8/27.下午6:08.
 *
 * @author bobomee.
 * @description:
 */
public abstract class Colleague {

  protected Mediator mMediator;//每一个同事都该知道其中介者

  public Colleague(Mediator _mediator){
    this.mMediator = _mediator;
  }
}
