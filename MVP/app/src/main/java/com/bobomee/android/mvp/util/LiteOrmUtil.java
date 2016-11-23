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

package com.bobomee.android.mvp.util;

import com.bobomee.android.mvp.App;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import java.util.List;

/**
 * Created on 16/6/10.下午9:14.
 *
 * @author bobomee
 */
public enum LiteOrmUtil {
  INSTANCE;

  private LiteOrm mLiteOrm;

  LiteOrmUtil() {
    this.mLiteOrm = App.sDb;
  }

  public void save(Object o) {
    if (o == null) {
      return;
    }

    mLiteOrm.save(o);
  }

  public <T> void saveAll(List<T> collection) {
    if (CommonUtil.isEmpty(collection)) {
      return;
    }

    mLiteOrm.save(collection);
  }

  public <T> void delete(Class<T> tClass) {
    if (tClass == null) {
      return;
    }

    mLiteOrm.delete(tClass);
  }

  public <T> List<T> queryAll(Class<T> tClass) {
    if (tClass == null) {
      return null;
    }

    return mLiteOrm.query(tClass);
  }

  public <T> List<T> query(QueryBuilder<T> builder) {
    if (builder == null) {
      return null;
    }

    return mLiteOrm.query(builder);
  }


}
