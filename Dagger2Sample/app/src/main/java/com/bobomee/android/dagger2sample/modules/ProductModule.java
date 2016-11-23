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

package com.bobomee.android.dagger2sample.modules;

import com.bobomee.android.dagger2sample.models.Product;
import com.bobomee.android.dagger2sample.qualifier.ProductLevel;
import com.bobomee.android.dagger2sample.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created on 16/6/8.下午9:49.
 *
 * @author bobomee
 */
@Module public class ProductModule {

  @ActivityScope @Provides Product provideProduct() {
    return new Product("this is a product");
  }

  @ActivityScope @ProductLevel("good") @Provides Product provideGoodProduct() {
    return new Product("good product");
  }

  @ActivityScope @ProductLevel("bad") @Provides Product provideBadProduct() {
    return new Product("bad product");
  }
}
