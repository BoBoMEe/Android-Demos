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

import java.io.Serializable;

/**
 * Created on 2016/12/19.下午12:14.
 *
 * @author bobomee.
 */

public class SerializedSingleton implements Serializable {

  private static final long serialVersionUID = -7604766932017737115L;

  private SerializedSingleton(){}

  private static class SingletonHelper{
    private static final SerializedSingleton instance = new SerializedSingleton();
  }

  public static SerializedSingleton getInstance(){
    return SingletonHelper.instance;
  }

  protected Object readResolve() {
    return getInstance();
  }

}