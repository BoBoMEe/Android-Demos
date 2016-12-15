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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bobomee.android.common.adapter.interfaces.AdapterItem;
import com.bobomee.android.common.adapter.interfaces.IAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public abstract class CommonRcvAdapter<T> extends RecyclerView.Adapter implements IAdapter<T> {

  private List<T> mDataList;

  public CommonRcvAdapter() {
    this(null);
  }

  public CommonRcvAdapter(@Nullable List<T> data) {
    this.mDataList = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
  }

  @Override public int getItemCount() {
    return mDataList.size();
  }

  @Override public void setData(@NonNull List<T> data) {
    mDataList = new ArrayList<>(data);
    notifyDataSetChanged();
  }

  @Override public List<T> getData() {
    return mDataList;
  }

  @Override public long getItemId(int position) {
    return position;
  }

  /**
   * instead by{@link #getItemType(Object)}
   *
   * 通过数据得到obj的类型的type
   */
  @Deprecated @Override public int getItemViewType(int position) {
    return getItemType(mDataList.get(position));
  }

  @Override public int getItemType(T t) {
    return 0; // default
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new RcvAdapterItemHolder(parent.getContext(), parent, createItem(viewType));
  }

  @SuppressWarnings("unchecked") @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    RcvAdapterItemHolder rcvAdapterItemHolder = (RcvAdapterItemHolder) holder;
    rcvAdapterItemHolder.getItem().setViews(mDataList.get(position));
    rcvAdapterItemHolder.getItem().handleData(mDataList.get(position), position);
  }

  public void addData(@NonNull List<T> items) {
    int previousDataSize = this.mDataList.size();
    this.mDataList.addAll(items);
    notifyItemRangeInserted(previousDataSize, items.size());
  }

  public void insert(int insert, @NonNull List<T> items) {
    this.mDataList.addAll(insert, items);
    notifyItemRangeInserted(insert, items.size());
  }

  public void remove(int start, int end) {
    List<T> ts = this.mDataList.subList(start, end);
    this.mDataList.removeAll(ts);
    notifyItemRangeRemoved(start, end);
  }

  public void insert(int insert, @NonNull T _t) {
    this.mDataList.add(insert, _t);
    notifyItemInserted(insert);
  }

  public void clearData() {
    mDataList.clear();
    notifyDataSetChanged();
  }

  ///////////////////////////////////////////////////////////////////////////
  // 内部用到的viewHold
  ///////////////////////////////////////////////////////////////////////////

  private class RcvAdapterItemHolder extends RecyclerView.ViewHolder {

    protected AdapterItem<T> item;

    protected RcvAdapterItemHolder(Context context, ViewGroup parent, AdapterItem<T> item) {
      super(LayoutInflater.from(context).inflate(item.getLayoutResId(), parent, false));
      this.item = item;
      this.item.bindViews(itemView);
    }

    public AdapterItem<T> getItem() {
      return item;
    }
  }
}
