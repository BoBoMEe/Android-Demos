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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.dagger2sample.components.ProductComponent;
import com.bobomee.android.dagger2sample.components.UserComponent;
import com.bobomee.android.dagger2sample.models.Product;
import com.bobomee.android.dagger2sample.models.UserModel;
import com.bobomee.android.dagger2sample.qualifier.ProductLevel;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.text) TextView text;

  @Inject UserModel userModel;
  @BindView(R.id.text1) TextView text1;
  @BindView(R.id.text3) TextView text3;
  @BindView(R.id.text4) TextView text4;
  @Inject Product product;
  @Inject @ProductLevel("good") Product product1;
  @Inject @ProductLevel("bad") Product product2;
  @BindView(R.id.text5) TextView text5;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    //inject dependencies
    ProductComponent.Initializer.init(UserComponent.Initializer.init()).inject(this);

    text.setText(userModel.getName());
    text1.setText(App.get().toString());

    text3.setText(product.getProductQualifier());
    text4.setText(product1.getProductQualifier());
    text5.setText(product2.getProductQualifier());
  }
}
