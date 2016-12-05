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

package com.bobomee.android.common.util;

/**
 * Singleton abstract class
 * <p/>
 * Created by zhaiyifan on 2015/7/27.
 */
public abstract class Singleton<T, P> {
  private volatile T mInstance;

  protected abstract T create(P p);

  public final T get(P p) {
    if (mInstance == null) {
      synchronized (this) {
        if (mInstance == null) {
          mInstance = create(p);
        }
      }
    }
    return mInstance;
  }
}