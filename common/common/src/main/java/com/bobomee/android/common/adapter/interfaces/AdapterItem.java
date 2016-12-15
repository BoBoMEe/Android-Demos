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

package com.bobomee.android.common.adapter.interfaces;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Adapter的所有item必须实现的接口.<br>
 *
 * 通过{@link #getLayoutResId()}初始化view;<br>
 * 在{@link #bindViews(View)}中就初始化item的内部视图<br>
 * 在{@link #handleData(T, int)}中处理每一行的数据<p>
 *
 * @author Jack Tony
 * @date 2015/5/15
 */
public interface AdapterItem<T> {

  /**
   * @return item布局文件的layoutId
   */
  @LayoutRes int getLayoutResId();

  /**
   * 初始化views
   */
  void bindViews(final View root);

  /**
   * 设置view的参数
   */
  void setViews(T t);

  /**
   * 根据数据来设置item的内部views
   *
   * @param t 数据list内部的model
   * @param position 当前adapter调用item的位置
   */
  void handleData(T t, int position);
}