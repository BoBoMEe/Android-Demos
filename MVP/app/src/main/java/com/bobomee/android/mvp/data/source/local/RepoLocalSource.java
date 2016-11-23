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

package com.bobomee.android.mvp.data.source.local;

import android.support.annotation.NonNull;
import com.bobomee.android.mvp.data.Owner;
import com.bobomee.android.mvp.data.Repo;
import com.bobomee.android.mvp.data.source.DataSource;
import com.bobomee.android.mvp.util.LiteOrmUtil;
import com.litesuits.orm.db.assit.QueryBuilder;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created on 16/6/10.下午6:07.
 *
 * @author bobomee
 */
public class RepoLocalSource implements DataSource {

  private static RepoLocalSource INSTANCE;

  public static RepoLocalSource getINSTANCE() {
    if (null == INSTANCE) INSTANCE = new RepoLocalSource();
    return INSTANCE;
  }

  // Prevent direct instantiation.

  private RepoLocalSource() {
  }

  @Override public void Repos(@NonNull String name, @NonNull LoadCallBack loadCallBack) {

    QueryBuilder<Repo> queryBuilder = new QueryBuilder<>(Repo.class).whereEquals("owner", name);

    List<Repo> repos = LiteOrmUtil.INSTANCE.query(queryBuilder);

    if (null != repos && repos.size() > 0) {
      loadCallBack.onDataLoaded(repos);
    } else {
      loadCallBack.onDataNotAvailable();
    }
  }

  @Override public void refreshCache() {
    //empty
  }

  @Override public void refreshLocal() {
    LiteOrmUtil.INSTANCE.delete(Repo.class);
    LiteOrmUtil.INSTANCE.delete(Owner.class);
  }

  @Override public void saveRepos(List<Repo> repos) {
    checkNotNull(repos);

    LiteOrmUtil.INSTANCE.saveAll(repos);

    Owner owner = repos.get(0).owner;
    owner.repos = repos;

    LiteOrmUtil.INSTANCE.save(owner);
  }
}
