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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.bobomee.android.mvp.BaseActivity;
import com.bobomee.android.mvp.R;
import com.bobomee.android.mvp.data.source.RepoDataSource;
import com.bobomee.android.mvp.data.source.local.RepoLocalSource;
import com.bobomee.android.mvp.data.source.remote.RepoRemoteSource;
import com.bobomee.android.mvp.util.ActivityUtils;

/**
 * Created on 16/6/11.上午11:10.
 *
 * @author bobomee
 */
public class ReposActivity extends BaseActivity {

  public static void start(Context context, String name) {
    Intent intent = new Intent(context, ReposActivity.class);
    intent.putExtra("user", name);
    context.startActivity(intent);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_act);

    String name = getIntent().getStringExtra("user");

    ReposFragment reposFragment =
        (ReposFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

    if (reposFragment == null) {
      reposFragment = ReposFragment.newInstance();

      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), reposFragment,
          R.id.contentFrame);
    }

    //Create the presenter
    new ListPresenter(name,
        RepoDataSource.getINSTANCE(RepoLocalSource.getINSTANCE(), RepoRemoteSource.getINSTANCE()),
        reposFragment);
  }
}
