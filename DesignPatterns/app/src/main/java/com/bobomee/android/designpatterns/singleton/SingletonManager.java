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

package com.bobomee.android.designpatterns.singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2016/12/19.下午12:12.
 *
 * @author bobomee.
 */

public class SingletonManager {
  private SingletonManager() {
  }

  private static Map<String, Object> objectMap = new HashMap<>();

  public static void registerService(String key, Object instance) {
    if (!objectMap.containsKey(key)) {
      objectMap.put(key, instance);
    }
  }

  public static Object getService(String key) {
    return objectMap.get(key);
  }
}