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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bobomee.android.common.R;
import com.bobomee.android.common.adapter.interfaces.AdapterItem;
import com.bobomee.android.common.adapter.interfaces.IAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/11/29
 */
public abstract class CommonPagerAdapter<T> extends BasePagerAdapter<View> implements IAdapter<T> {

  private List<T> mDataList;

  private LayoutInflater mInflater;

  private boolean mIsLazy = false;

  public CommonPagerAdapter() {
    this(null);
  }

  public CommonPagerAdapter(@Nullable List<T> data) {
    this(data, false);
  }

  public CommonPagerAdapter(@Nullable List<T> data, boolean isLazy) {
    this.mDataList = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
    mIsLazy = isLazy;
  }

  @Override public int getCount() {
    return mDataList.size();
  }

  @NonNull @Override protected View getViewFromItem(View item, int pos) {
    return item;
  }

  @Override public View instantiateItem(ViewGroup container, int position) {
    View view = super.instantiateItem(container, position);
    if (!mIsLazy) {
      initItem(position, view);
    }
    return view;
  }

  @Override public void setPrimaryItem(ViewGroup container, int position, @NonNull Object object) {
    if (mIsLazy && object != currentItem) {
      initItem(position, ((View) object));
    }
    super.setPrimaryItem(container, position, object);
  }

  private void initItem(int position, View view) {
    final AdapterItem item = (AdapterItem) view.getTag(R.id.tag_item);
    item.handleData(mDataList.get(position), position);
  }

  @Override protected View createItem(ViewGroup viewPager, int position) {
    if (mInflater == null) {
      mInflater = LayoutInflater.from(viewPager.getContext());
    }
    AdapterItem item = createItem(getItemType(position));
    View view = mInflater.inflate(item.getLayoutResId(), null);
    view.setTag(R.id.tag_item, item);
    item.bindViews(view);
    item.setViews(mDataList.get(position));
    return view;
  }

  public void setIsLazy(boolean isLazy) {
    mIsLazy = isLazy;
    notifyDataSetChanged();
  }

  /**
   * instead by {@link #getItemType(Object)}
   */
  @Deprecated protected int getItemType(int position) {
    return getItemType(mDataList.get(position));
  }

  /**
   * 强烈建议返回string,int,bool类似的基础对象做type
   */
  @Override public int getItemType(T t) {
    return -1; // default
  }

  @Override public void setData(@NonNull List<T> data) {
    mDataList = new ArrayList<T>(data);
    notifyDataSetChanged();
  }

  @Override public List<T> getData() {
    return mDataList;
  }
}
