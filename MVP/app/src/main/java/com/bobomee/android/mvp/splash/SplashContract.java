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

import com.bobomee.android.mvp.BasePresenter;
import com.bobomee.android.mvp.BaseView;

/**
 * Created on 16/6/10.上午9:06.
 *
 * @author bobomee
 */
public interface SplashContract {

  interface View extends BaseView<Presenter> {

    //show loading progress or not
    void setLoadingIndicator(boolean active);

    // check if the user is empty
    void setUsernameError();

    //navigate to repositories list
    void showUserRepositories();
  }

  interface Presenter extends BasePresenter {

    //request
    void repos(String name);

    // destroy the view
    void onDestroy();
  }
}
