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

package com.bobomee.android.dagger2sample.components;

import com.bobomee.android.dagger2sample.App;
import com.bobomee.android.dagger2sample.modules.AppModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created on 16/6/8.下午9:31.
 *
 * @author bobomee
 */
@Singleton @Component(modules = {
    AppModule.class
}) public interface AppComponent {

  void inject(App app);

  final class Initializer {
    private Initializer() {
    }

    public static AppComponent init(App app) {
      return DaggerAppComponent.builder().appModule(new AppModule(app)).build();
    }
  }
}
