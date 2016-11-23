/*
 *  Copyright (C) 2016 BoBoMEe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.bobomee.android.common.manager;

import java.util.Set;

/**
 * Created on 16/6/4.下午7:09.
 *
 * @author bobomee
 */
public interface ObservableInterface<T, P, R> {

  /**
   * 根据名称注册观察者
   */
  void registerObserver(String name, T observer);

  /**
   * 根据名称反注册观察者
   */
  void removeObserver(String name);

  /**
   * 根据观察者反注册
   */
  void removeObserver(T observer);

  /**
   * 根据名称和观察者反注册
   */
  void removeObserver(String name, T observer);

  /**
   * 根据名称获取观察者
   */
  Set<T> getObserver(String name);

  /**
   * 清除观察者
   */
  void clear();

  /**
   * 通知观察者
   *
   * @param name 名称
   * @param p 参数
   * @return 返回值
   */

  R notify(String name, P... p);
}
