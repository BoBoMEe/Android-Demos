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

import android.os.Bundle;
import com.bobomee.android.mvp.BaseActivity;
import com.bobomee.android.mvp.R;
import com.bobomee.android.mvp.data.source.RepoDataSource;
import com.bobomee.android.mvp.data.source.local.RepoLocalSource;
import com.bobomee.android.mvp.data.source.remote.RepoRemoteSource;
import com.bobomee.android.mvp.util.ActivityUtils;

public class SplashActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.splash_act);

    SplashFragment splashFragment =
        (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

    if (splashFragment == null) {
      // Create the fragment
      splashFragment = SplashFragment.newInstance();
      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), splashFragment,
          R.id.contentFrame);
    }

    //Create the presenter
    new SplashPresenter(
        RepoDataSource.getINSTANCE(RepoLocalSource.getINSTANCE(), RepoRemoteSource.getINSTANCE()),
        splashFragment);
  }
}
