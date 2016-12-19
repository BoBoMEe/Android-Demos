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
 * Created on 2016/12/19.下午12:11.
 *
 * @author bobomee.
 */

public class Singleton {

  // 1.一个私有的指向自己的静态变量
  private static Singleton instance;

  // 2.私有的构造方法,保证不能从外部创建对象
  private Singleton(){}

  // 3.公开的静态工厂方法,返回该类的唯一实例(当发现没有实例没有初始化的时候才初始化)
  public static synchronized Singleton getInstance(){
    if(instance == null){
      instance = new Singleton();
    }
    return instance;
  }

}