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

import android.support.v4.util.ArrayMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created on 2016/12/15.下午3:23.
 *
 * @author bobomee.
 */

public class PagerCache<T> {
  private Map<Integer, Queue<T>> mCacheMap;

  public PagerCache() {
    mCacheMap = new ArrayMap<>();
  }

  /**
   * @param type item type
   * @return cache中的item，如果拿不到就返回null
   */
  public T getItem(int type) {
    Queue<T> queue = mCacheMap.get(type);
    return queue != null ? queue.poll() : null;
  }

  /**
   * @param type item's type
   */
  public void putItem(int type, T item) {
    Queue<T> queue;
    if ((queue = mCacheMap.get(type)) == null) {
      queue = new LinkedList<>();
      mCacheMap.put(type, queue);
    }
    queue.offer(item);
  }
}
