package com.bobomee.android.designpatterns.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 16/8/6.下午5:51.
 *
 * @author bobomee.
 * @description:
 */
public abstract class ItemAdapter<T> {

  //绑定View的数据源
  private List<T> mDatas;

  public ItemAdapter(List<T> datas) {
    if (datas == null) datas = new ArrayList<>(0);
    mDatas = datas;
  }

  public ItemAdapter(T[] datas) {
    mDatas = new ArrayList<>(Arrays.asList(datas));
  }

  // 需要用户来定义的 view ,
  protected abstract View getView(ViewGroup parent, int position, T t);

  // 获取每个Item
  protected T getItem(int position) {
    return mDatas.get(position);
  }

  // 获取数据源的大小
  protected int getCount() {
    return mDatas.size();
  }

  // 注册数据源变化的监听器
  public void registerDataSetObserver(DataSetObserver observer) {
    mDataSetObservable.registerObserver(observer);
  }

  // 移除监听器
  public void unregisterDataSetObserver(DataSetObserver observer) {
    mDataSetObservable.unregisterObserver(observer);
  }

  private final DataSetObservable mDataSetObservable = new DataSetObservable();

  // 通知数据源 更新
  public void notifyDataSetChanged() {
    mDataSetObservable.notifyChanged();
  }
}
