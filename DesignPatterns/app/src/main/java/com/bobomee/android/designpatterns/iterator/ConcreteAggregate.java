package com.bobomee.android.designpatterns.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/8/26.下午9:29.
 *
 * @author bobomee.
 * @description:
 */
public class ConcreteAggregate<T> implements Aggregate<T> {
  private List<T> mList = new ArrayList<>();

  @Override public void add(T _t) {
    mList.add(_t);
  }

  @Override public void remove(T _t) {
    mList.remove(_t);
  }

  @Override public Iterator<T> iterator() {
    return new ConcreteIterator<T>(mList);
  }
}
