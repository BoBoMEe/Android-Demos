/*
 *
 *    Copyright (C) 2016 BoBoMEe(wbwjx115@gmail.com)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.bobomee.android.androidorm;

import com.bobomee.android.common.app.BaseApplication;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;

/**
 * Created on 16/6/10.下午9:13.
 *
 * @author bobomee
 */
public class App extends BaseApplication {

  private static final String DB_NAME = "liteOrm.db";
  public static LiteOrm sDb;

  @Override public void onCreate() {
    super.onCreate();
    sDb = LiteOrm.newCascadeInstance(config());

    // 与非级联交叉使用：
    //db.cascade().save(user);//级联操作：保存[当前对象]，以及该对象所有的[关联对象]以及它们的[映射关系]，超贱！
    //db.single().save(user);//非级联操作：仅保存[当前对象]，高效率。
  }

  private DataBaseConfig config() {

    DataBaseConfig config = new DataBaseConfig(this);
    config.dbName = DB_NAME;//数据库名,可以制定路径
    config.dbVersion = 1; // version
    config.onUpdateListener = null; // set database update listener
    config.debugged = BuildConfig.DEBUG; //open the log

    return config;
  }
}
