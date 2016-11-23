package com.bobomee.android.designpatterns.iterator;

/**
 * Created on 16/8/26.下午9:19.
 *
 * @author bobomee.
 * @description:
 */
public interface Iterator<T> {

  /**
   * 是否还有下一个元素
   */
  boolean hasNext();

  /**
   * 返回当前位置的元素并将位置移至下一步
   */
  T next();
}
