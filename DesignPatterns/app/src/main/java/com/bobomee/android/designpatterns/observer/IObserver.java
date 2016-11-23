package com.bobomee.android.designpatterns.observer;

/**
 * Created on 16/8/20.下午11:30.
 *
 * @author bobomee.
 * @description:
 */
public interface IObserver {
  //被观察者更新
  void  update(ISubject subject,Object o);
}
