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
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.bobomee.android.common.R;
import com.bobomee.android.common.adapter.interfaces.PagerCache;

/**
 * @author Jack Tony
 * @date 2015/11/21
 *
 * 如果调用{@link #notifyDataSetChanged()}来更新，
 * 它会自动调用{@link #instantiateItem(ViewGroup, int)}重新new出需要的item，算是完全初始化一次。
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {

  protected T currentItem = null;

  /**
   * 这的cache的最大大小是：type * pageSize
   */
  private final PagerCache<T> mCache;

  public BasePagerAdapter() {
    mCache = new PagerCache<>();
  }

  /**
   * 注意：这里必须是view和view的比较
   */
  @Override public boolean isViewFromObject(View view, Object obj) {
    return view == getViewFromItem((T) obj, 0);
  }

  @Override public T instantiateItem(ViewGroup container, int position) {
    int type = getItemType(position);
    T item = mCache.getItem(type); // get item from type
    if (item == null) {
      item = createItem(container, position);
    }
    // 通过item得到将要被add到viewpager中的view
    View view = getViewFromItem(item, position);
    view.setTag(R.id.item_type, type);

    if (view.getParent() != null) {
      ((ViewGroup) view.getParent()).removeView(view);
    }
    container.addView(view);
    return item;
  }

  @Override public void setPrimaryItem(ViewGroup container, int position, Object object) {
    super.setPrimaryItem(container, position, object);
    if (object != currentItem) {
      // 可能是currentItem不等于null，可能是二者不同
      currentItem = (T) object;
    }
  }

  @Override public void destroyItem(ViewGroup container, int position, Object object) {
    T item = (T) object;
    View viewFromItem = getViewFromItem(item, position);
    // 现在通过item拿到其中的view，然后remove掉
    container.removeView(viewFromItem);
    int type = (int) viewFromItem.getTag(R.id.item_type);
    mCache.putItem(type, item);
  }

  @Override public int getItemPosition(Object object) {
    return POSITION_NONE;
  }

  protected int getItemType(int position) {
    return -1; // default
  }

  public T getCurrentItem() {
    return currentItem;
  }

  protected PagerCache<T> getCache() {
    return mCache;
  }

  /**
   * 这里要实现一个从item拿到view的规则
   *
   * @param item 包含view的item对象
   * @param position item所处的位置
   * @return item中的view对象
   */
  protected abstract @NonNull View getViewFromItem(T item, int position);

  /**
   * 当缓存中无法得到所需item时才会调用
   *
   * @return 需要放入容器的view
   */
  protected abstract T createItem(ViewGroup viewPager, int position);
}
