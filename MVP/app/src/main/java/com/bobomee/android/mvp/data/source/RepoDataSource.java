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

package com.bobomee.android.mvp.data.source;

import android.support.annotation.NonNull;
import com.bobomee.android.mvp.data.Repo;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created on 16/6/10.下午6:11.
 *
 * @author bobomee
 */
public class RepoDataSource implements DataSource {

  private static RepoDataSource INSTANCE;

  private final DataSource mLocalSource;

  private final DataSource mRemoteSource;

  /**
   * repo cache ,key = name,value = repos
   */
  Map<String, List<Repo>> mCache;

  public RepoDataSource(@NonNull DataSource mLocalSource, @NonNull DataSource mRemoteSource) {
    this.mLocalSource = checkNotNull(mLocalSource);
    this.mRemoteSource = checkNotNull(mRemoteSource);
  }

  /**
   * Returns the single instance of this class, creating it if necessary.
   *
   * @param mRemoteSource the backend data source
   * @param mLocalSource the device storage data source
   * @return the {@link RepoDataSource} instance
   */

  public static RepoDataSource getINSTANCE(DataSource mLocalSource, DataSource mRemoteSource) {
    if (null == INSTANCE) INSTANCE = new RepoDataSource(mLocalSource, mRemoteSource);
    return INSTANCE;
  }

  @Override
  public void Repos(@NonNull final String name, @NonNull final LoadCallBack loadCallBack) {

    checkNotNull(loadCallBack);

    if (null != mCache && mCache.size() > 0) {

      List<Repo> repos = mCache.get(name);
      if (null != repos && repos.size() > 0) {
        loadCallBack.onDataLoaded(repos);
      } else {
        fromLocal(name, loadCallBack);
      }
    } else {
      fromLocal(name, loadCallBack);
    }
  }

  private void fromLocal(@NonNull final String name, @NonNull final LoadCallBack loadCallBack) {
    mLocalSource.Repos(name, new LoadCallBack() {
      @Override public void onDataNotAvailable() {
        fromRemote(name, loadCallBack);
      }

      @Override public void onDataLoaded(List<Repo> repos) {
        refreshCache(name, repos);
        loadCallBack.onDataLoaded(repos);
      }
    });
  }

  @Override public void refreshCache() {
    if (null == mCache) return;
    mCache.clear();
  }

  @Override public void refreshLocal() {
    mLocalSource.refreshLocal();
    refreshCache();
  }

  @Override public void saveRepos(List<Repo> repos) {
    checkNotNull(repos);

    mLocalSource.saveRepos(repos);
    mRemoteSource.saveRepos(repos);
  }

  private void refreshCache(String name, List<Repo> repos) {
    if (null == mCache) {
      mCache = new LinkedHashMap<>();
    }
    mCache.clear();
    mCache.put(name, repos);
  }

  private void fromRemote(final String name, final LoadCallBack loadCallBack) {
    mRemoteSource.Repos(name, new LoadCallBack() {
      @Override public void onDataNotAvailable() {
        loadCallBack.onDataNotAvailable();
      }

      @Override public void onDataLoaded(List<Repo> repos) {
        refreshCache(name, repos);
        saveRepos(repos);
        loadCallBack.onDataLoaded(repos);
      }
    });
  }
}
