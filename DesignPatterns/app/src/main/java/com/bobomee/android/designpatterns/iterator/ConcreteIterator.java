package com.bobomee.android.designpatterns.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/8/26.下午9:22.
 *
 * @author bobomee.
 * @description:
 */
public class ConcreteIterator<T> implements Iterator<T> {

  private List<T> mList = new ArrayList<>();
  private int cursor = 0;

  public ConcreteIterator(List<T> _list) {
    mList = _list;
  }

  @Override public boolean hasNext() {
    return cursor != mList.size();
  }

  @Override public T next() {
    T obj = null;

    if (this.hasNext()) {
      obj = this.mList.get(cursor++);
    }
    return obj;
  }
}
