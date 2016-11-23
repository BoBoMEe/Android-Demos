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

package com.bobomee.android.mvp.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.bobomee.android.mvp.BaseActivity;
import com.bobomee.android.mvp.R;
import com.bobomee.android.mvp.data.Repo;
import com.bobomee.android.mvp.util.ActivityUtils;

/**
 * Created on 16/6/11.上午11:12.
 *
 * @author bobomee
 */
public class DetailActivity extends BaseActivity {

  public static void actionStart(Context context, Repo repo) {
    Intent intent = new Intent(context, DetailActivity.class);
    intent.putExtra("repo", repo);
    context.startActivity(intent);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.splash_act);

    Repo repo = (Repo) getIntent().getSerializableExtra("repo");

    DetailFragment detailFragment =
        (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

    if (detailFragment == null) {
      detailFragment = DetailFragment.newInstance(repo);

      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), detailFragment,
          R.id.contentFrame);
    }

    new DetailPresenter(repo, detailFragment);
  }
}
