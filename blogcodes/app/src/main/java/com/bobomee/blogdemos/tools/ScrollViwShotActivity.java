/*
 * Copyright (C) 2017.  BoBoMEe(wbwjx115@gmail.com)
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

package com.bobomee.blogdemos.tools;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.common.util.ScreenUtil;
import com.bobomee.blogdemos.R;

/**
 * Created on 2017/1/18.下午11:47.
 *
 * @author bobomee.
 */

public class ScrollViwShotActivity extends AppCompatActivity {

  private android.widget.ScrollView root;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.screen_shot_scroll_layout);
    ButterKnife.bind(this);
    this.root = (ScrollView) findViewById(R.id.root);
  }

  @OnClick(R.id.click)
  void onClick() {
    Bitmap lBitmap = ScreenUtil.shotScrollView(root);
    Utils.showDialog(this,lBitmap);
  }
}
