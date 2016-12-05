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

package com.bobomee.android.common.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 16/6/4.下午8:12.
 *
 * @author bobomee
 */
public class ObservableManager<Param, Result>
    implements ObservableInterface<Function, Param, Result> {

  private static final String TAG = "ObservableManager";
  private HashMap<String, Set<Function>> _mapping;
  private final Object _lockObj = new Object();
  private static ObservableManager _instance;

  public ObservableManager() {
    this._mapping = new HashMap<>();
  }

  public static ObservableManager newInstance() {
    if (_instance == null) _instance = new ObservableManager();
    return _instance;
  }

  @Override public void registerObserver(String name, Function observer) {
    synchronized (_lockObj) {
      Set<Function> observers;
      if (!_mapping.containsKey(name)) {
        observers = new HashSet<>();
        _mapping.put(name, observers);
      } else {
        observers = _mapping.get(name);
      }
      observers.add(observer);
    }
  }

  @Override public void removeObserver(String name) {
    synchronized (_lockObj) {
      _mapping.remove(name);
    }
  }

  @Override public void removeObserver(Function observer) {
    synchronized (_lockObj) {
      for (String key : _mapping.keySet()) {
        Set<Function> observers = _mapping.get(key);
        observers.remove(observer);
      }
    }
  }

  @Override public void removeObserver(String name, Function observer) {
    synchronized (_lockObj) {
      if (_mapping.containsKey(name)) {
        Set<Function> observers = _mapping.get(name);
        observers.remove(observer);
      }
    }
  }

  @Override public Set<Function> getObserver(String name) {
    Set<Function> observers = null;
    synchronized (_lockObj) {
      if (_mapping.containsKey(name)) {
        observers = _mapping.get(name);
      }
    }
    return observers;
  }

  @Override public void clear() {
    synchronized (_lockObj) {
      _mapping.clear();
    }
  }

  public Result notify(String name, Param... param) {
    synchronized (_lockObj) {
      if (_mapping.containsKey(name)) {
        Set<Function> observers = _mapping.get(name);
        for (Function o : observers) {
          return (Result) o.function(param);
        }
      }
      return null;
    }
  }
}
