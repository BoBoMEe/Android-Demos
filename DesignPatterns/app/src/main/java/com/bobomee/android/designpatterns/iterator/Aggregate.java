package com.bobomee.android.designpatterns.iterator;

/**
 * Created on 16/8/26.下午9:27.
 *
 * @author bobomee.
 * @description:
 */
public interface Aggregate<T> {
  /**
   * 添加一个元素
   * @param _t
   */
  void add(T _t);

  /**
   * 移除一个元素
   * @param _t
   */
  void remove(T _t);

  /**
   * 获取容器的迭代器
   * @return
   */
  Iterator<T> iterator();
}
