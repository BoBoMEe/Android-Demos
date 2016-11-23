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
import java.util.List;

/**
 * Created on 16/6/10.下午5:22.
 *
 * @author bobomee
 */
public interface DataSource {

  /**
   * get the data
   */

  void Repos(@NonNull String name, @NonNull LoadCallBack loadCallBack);

  /**
   * clear the cache
   */
  void refreshCache();

  /**
   * clear the database
   */
  void refreshLocal();

  /**
   * save the data
   */
  void saveRepos(@NonNull List<Repo> repos);

  /**
   * load callback
   */

  interface LoadCallBack {
    void onDataNotAvailable();

    void onDataLoaded(List<Repo> repos);
  }
}
