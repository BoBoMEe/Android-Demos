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

package com.bobomee.android.mvp.list;

import android.support.annotation.NonNull;
import com.bobomee.android.mvp.data.Repo;
import com.bobomee.android.mvp.data.source.DataSource;
import com.bobomee.android.mvp.data.source.RepoDataSource;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created on 16/6/11.上午11:11.
 *
 * @author bobomee
 */
public class ListPresenter implements ListContract.Presenter {

  private RepoDataSource dataSource;
  private ListContract.View listView;
  private List<Repo> repos;
  private String name;

  public ListPresenter(@NonNull String name, @NonNull RepoDataSource dataSource,
      ListContract.View listView) {
    this.name = checkNotNull(name);
    this.dataSource = checkNotNull(dataSource);
    this.listView = checkNotNull(listView);
    listView.setPresenter(this);
  }

  @Override public void findRepos() {
    if (null != listView) {
      listView.setLoadingIndicator(true);
    }

    dataSource.Repos(name, new DataSource.LoadCallBack() {
      @Override public void onDataNotAvailable() {
        if (null != listView) {
          listView.setLoadingIndicator(false);
          listView.showEmpty();
        }
      }

      @Override public void onDataLoaded(List<Repo> repos) {
        checkNotNull(repos);
        if (null != listView) {
          listView.setLoadingIndicator(false);
          ListPresenter.this.repos = repos;
          listView.showList(repos);
        }
      }
    });
  }

  @Override public void openRepo(int positon) {
    if (null != listView) listView.toDetail(repos.get(positon));
  }

  @Override public void onDestroy() {
    listView = null;
  }

  @Override public void refresh() {
    dataSource.refreshCache();
  }

  @Override public void removedatabse() {
    dataSource.refreshLocal();
  }

  @Override public void start() {
    if (null != listView) {
      listView.setLoadingIndicator(false);
    }
  }
}
