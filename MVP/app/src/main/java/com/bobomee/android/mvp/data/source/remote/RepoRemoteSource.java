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

package com.bobomee.android.mvp.data.source.remote;

import android.support.annotation.NonNull;
import com.bobomee.android.mvp.api.GithubService;
import com.bobomee.android.mvp.data.Repo;
import com.bobomee.android.mvp.data.source.DataSource;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 16/6/10.下午6:04.
 *
 * @author bobomee
 */
public class RepoRemoteSource implements DataSource {

  private static RepoRemoteSource INSTANCE;

  public static RepoRemoteSource getINSTANCE() {
    if (null == INSTANCE) INSTANCE = new RepoRemoteSource();
    return INSTANCE;
  }

  // Prevent direct instantiation.
  private RepoRemoteSource() {
  }

  @Override public void Repos(@NonNull String name, @NonNull final LoadCallBack loadCallBack) {

    Call<List<Repo>> listCall = GithubService.INSTANCE.getGithubApi().listRepos(name);
    listCall.enqueue(new Callback<List<Repo>>() {
      @Override public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
        List<Repo> body = response.body();
        if (null != body && body.size() > 0) loadCallBack.onDataLoaded(body);
      }

      @Override public void onFailure(Call<List<Repo>> call, Throwable t) {
        loadCallBack.onDataNotAvailable();
      }
    });
  }

  @Override public void refreshCache() {
    // empty
  }

  @Override public void refreshLocal() {
    // empty
  }

  @Override public void saveRepos(@NonNull List<Repo> repos) {
    // empty
  }
}
