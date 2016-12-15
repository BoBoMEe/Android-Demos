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

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/11/29
 * 通用的adapter必须实现的接口，作为方法名统一的一个规范
 */
public interface IAdapter<T> {

  /**
   * @param data 设置数据源
   */
  void setData(@NonNull List<T> data);

  List<T> getData();

  /**
   * @param t list中的一条数据
   */
  int getItemType(T t);

  /**
   * 当缓存中无法得到所需item时才会调用
   *
   * @param type 通过{@link #getItemType(T)}得到的type
   * @return 任意类型的 AdapterItem
   */
  @Keep @NonNull AdapterItem<T> createItem(int type);
}
