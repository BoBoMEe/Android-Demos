/*
 *  Copyright (C) 2016 BoBoMEe(wbwjx115@gmail.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.bobomee.android.dagger2sample;

import android.app.Application;
import com.bobomee.android.dagger2sample.components.AppComponent;
import javax.inject.Inject;

/**
 * Created on 16/6/8.下午9:29.
 *
 * @author bobomee
 */
public class App extends Application {

  private AppComponent appComponent;

  @Inject static App app;

  public static App get() {
    return app;
  }

  @Override public void onCreate() {
    super.onCreate();

    buildComponent();
  }

  private void buildComponent() {
    appComponent = AppComponent.Initializer.init(this);
    appComponent.inject(this);
  }

  public AppComponent component() {
    return appComponent;
  }
}
