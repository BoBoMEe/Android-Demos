/*
 * Copyright (C) 2016.  BoBoMEe(wbwjx115@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.bobomee.android.mortar_flow_samples;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.bobomee.android.mortar_flow_samples.dagger2.HelloDagger2Activity;
import com.bobomee.android.mortar_flow_samples.flow.BasicSampleActivity;
import com.bobomee.android.mortar_flow_samples.mortar.MortarBasicActivity;

/**
 * Created on 2016/12/20.下午7:59.
 *
 * @author bobomee.
 */

public class Mainactivity extends AppCompatActivity implements View.OnClickListener {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    findViewById(R.id.mortar_basic).setOnClickListener(this);
    findViewById(R.id.mortar_dagger2).setOnClickListener(this);
    findViewById(R.id.flow_basic).setOnClickListener(this);
  }

  @Override public void onClick(View _view) {
    switch (_view.getId()) {
      case R.id.mortar_basic:
        start(MortarBasicActivity.class);
        break;
      case R.id.mortar_dagger2:
        start(HelloDagger2Activity.class);
        break;
      case R.id.flow_basic:
        start(BasicSampleActivity.class);
        break;
    }
  }

  private void start(Class _class) {
    Intent intent = new Intent(this, _class);
    startActivity(intent);
  }
}
