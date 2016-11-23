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

import com.bobomee.android.mvp.BasePresenter;
import com.bobomee.android.mvp.BaseView;
import com.bobomee.android.mvp.data.Repo;
import java.util.List;

/**
 * Created on 16/6/10.上午9:06.
 *
 * @author bobomee
 */
public interface ListContract {

  interface View extends BaseView<Presenter> {
    void setLoadingIndicator(boolean active);

    void showList(List<Repo> repos);

    void showEmpty();

    void toDetail(Repo repo);
  }

  interface Presenter extends BasePresenter {

    void findRepos();

    void openRepo(int positon);

    // destroy the view
    void onDestroy();

    //clear cache
    void refresh();

    //clear database
    void removedatabse();
  }
}
