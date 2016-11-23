package com.bobomee.android.designpatterns.observer;

import android.content.Context;
import android.widget.Toast;

/**
 * Created on 16/8/20.下午11:41.
 *
 * @author bobomee.
 * @description:
 */
public class ConcreteObserver2 implements IObserver {
  private final Context context;

  public ConcreteObserver2(Context _context) {
    this.context = _context;
  }

  @Override public void update(ISubject subject, Object o) {
    Toast.makeText(context, "ConcreteObserver2 from -- >"+subject +"update -- >"+o, Toast.LENGTH_SHORT).show();
  }
}
