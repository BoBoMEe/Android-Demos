package com.bobomee.android.designpatterns.observer;

import java.util.HashSet;
import java.util.Set;

/**
 * Created on 16/8/20.下午11:35.
 *
 * @author bobomee.
 * @description:
 */
public class ConcreteSubject implements ISubject {

  Set<IObserver> mIObservers = new HashSet<>();

  @Override public void registerObserver(IObserver observer) {
    if (null != observer) mIObservers.add(observer);
  }

  @Override public void unregisterObserver(IObserver observer) {
    mIObservers.remove(observer);
  }

  @Override public void unregisterObserver() {
    mIObservers.clear();
  }

  @Override public void notify(Object o) {
    for (IObserver observer : mIObservers) {
      observer.update(this, o);
    }
  }

  @Override public void notify(IObserver observer, Object o) {
    observer.update(this, o);
  }
}
