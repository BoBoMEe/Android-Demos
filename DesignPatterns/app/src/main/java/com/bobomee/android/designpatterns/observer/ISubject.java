package com.bobomee.android.designpatterns.observer;

/**
 * Created on 16/8/20.下午11:26.
 *
 * @author bobomee.
 * @description:
 */
public interface ISubject {
  //注册一个观察者
  void registerObserver(IObserver observer);
  //反注册一个观察者
  void unregisterObserver(IObserver observer);
  //反注册所有的观察者
  void unregisterObserver();
  //通知观察者
  void notify(Object o);
  //通知某个观察者
  void notify(IObserver observer,Object o);
}
