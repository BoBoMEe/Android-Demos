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

/**
 * Created on 2016/12/19.下午12:12.
 *
 * @author bobomee.
 */

public class LockSingleton{
  private volatile static LockSingleton singleton;
  private LockSingleton(){}

  //http://www.ibm.com/developerworks/cn/java/j-dcl.html
  public static LockSingleton getInstance(){
    if(singleton==null){
      synchronized(LockSingleton.class){
        if(singleton==null){
          singleton=new LockSingleton(); //非原子性操作
        }
      }
    }
    return singleton;
  }

}
