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

package com.bobomee.android.mvp.splash;

import android.support.annotation.NonNull;
import com.bobomee.android.mvp.data.Repo;
import com.bobomee.android.mvp.data.source.DataSource;
import com.bobomee.android.mvp.data.source.RepoDataSource;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created on 16/6/10.下午5:09.
 *
 * @author bobomee
 */
public class SplashPresenter implements SplashContract.Presenter, DataSource.LoadCallBack {

  private RepoDataSource dataSource;
  private SplashContract.View splashView;

  public SplashPresenter(@NonNull RepoDataSource dataSource, SplashContract.View splashView) {
    this.dataSource = checkNotNull(dataSource);
    this.splashView = checkNotNull(splashView);
    this.splashView.setPresenter(this);
  }

  @Override public void repos(String name) {
    if (null != splashView) {
      splashView.setLoadingIndicator(true);
    }
    dataSource.Repos(name, this);
  }

  @Override public void onDestroy() {
    splashView = null;
  }

  @Override public void start() {
    splashView.setLoadingIndicator(false);
  }

  @Override public void onDataNotAvailable() {
    if (null != splashView) {
      splashView.setUsernameError();
      splashView.setLoadingIndicator(false);
    }
  }

  @Override public void onDataLoaded(List<Repo> repos) {
    checkNotNull(repos);
    if (null != splashView) {
      splashView.setLoadingIndicator(false);
      splashView.showUserRepositories();
    }
  }
}
