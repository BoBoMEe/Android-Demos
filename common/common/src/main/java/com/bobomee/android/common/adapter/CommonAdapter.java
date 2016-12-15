/*
 * Copyright (C) 2016.  BoBoMEe(wbwjx115@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.bobomee.android.common.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.bobomee.android.common.R;
import com.bobomee.android.common.adapter.interfaces.AdapterItem;
import com.bobomee.android.common.adapter.interfaces.IAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/5/15
 */
public abstract class CommonAdapter<T> extends BaseAdapter implements IAdapter<T> {

  protected List<T> mDataList;

  private int mViewTypeCount = 1;

  private LayoutInflater mInflater;

  protected SparseArray<AdapterItem<T>> mItemsCache;

  public CommonAdapter() {
    this(null);
  }

  public CommonAdapter(List<T> _dataList) {
    this(_dataList, 1);
  }

  public CommonAdapter(int _viewTypeCount) {
    this(null, _viewTypeCount);
  }

  public CommonAdapter(@Nullable List<T> data, int viewTypeCount) {
    this.mDataList = data == null ? new ArrayList<T>() : new ArrayList<>(data);
    mViewTypeCount = viewTypeCount;
    mItemsCache = new SparseArray<>();
  }

  @Override public int getCount() {
    return mDataList.size();
  }

  @Override public void setData(@NonNull List<T> data) {
    mDataList = new ArrayList<>(data);
    notifyDataSetChanged();
  }

  public void setViewTypeCount(int _viewTypeCount) {
    this.mViewTypeCount = _viewTypeCount;
    notifyDataSetChanged();
  }

  @Override public List<T> getData() {
    return mDataList;
  }

  @Override public long getItemId(int position) {
    return position;
  }

  /**
   * 通过数据得到obj的类型的type
   *
   * instead by{@link #getItemType(Object)}
   */
  @Override @Deprecated public int getItemViewType(int position) {
    // 如果不写这个方法，会让listView更换dataList后无法刷新数据
    return getItemType(mDataList.get(position));
  }

  @Override public int getItemType(T t) {
    return 0; // default
  }

  @Override public int getViewTypeCount() {
    return mViewTypeCount;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    if (mInflater == null) {
      mInflater = LayoutInflater.from(parent.getContext());
    }

    final AdapterItem<T> item;
    if (convertView == null) {
      item = createItem(getItemViewType(position));
      convertView = mInflater.inflate(item.getLayoutResId(), parent, false);
      convertView.setTag(R.id.tag_item, item); // get item

      item.bindViews(convertView);
      item.setViews(getItem(position));
      mItemsCache.put(mItemsCache.size(), item);
    } else {
      item = (AdapterItem<T>) convertView.getTag(R.id.tag_item); // save item
    }
    item.handleData(getItem(position), position);
    return convertView;
  }

  @Override public T getItem(int position) {
    return mDataList.get(position);
  }
}
