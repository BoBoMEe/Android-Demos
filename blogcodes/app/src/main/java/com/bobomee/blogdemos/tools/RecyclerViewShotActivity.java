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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.common.adapter.CommonRcvAdapter;
import com.bobomee.android.common.adapter.interfaces.AdapterItem;
import com.bobomee.android.common.util.ScreenUtil;
import com.bobomee.blogdemos.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2017/1/18.下午11:47.
 *
 * @author bobomee.
 */

public class RecyclerViewShotActivity extends AppCompatActivity {

  private List<Integer> mIntegerList = new ArrayList<Integer>(Arrays.asList(new Integer[] {
      1, 2, 3, 4, 5, 6, 7, 8, 9
  }));
  private android.support.v7.widget.RecyclerView recyclerview;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.screen_shot_recycler_layout);
    ButterKnife.bind(this);
    this.recyclerview = (RecyclerView) findViewById(R.id.recycler_view);
    recyclerview.setLayoutManager(new LinearLayoutManager(this));
    recyclerview.setAdapter(new CommonRcvAdapter<Integer>(mIntegerList) {
      @NonNull @Override public AdapterItem<Integer> createItem(int type) {
        return new AdapterItem<Integer>() {
          @Override public int getLayoutResId() {
            return R.layout.screen_shot_item;
          }

          @Override public void bindViews(View root) {

          }

          @Override public void setViews(Integer pInteger) {

          }

          @Override public void handleData(Integer pInteger, int position) {

          }
        };
      }
    });
  }

  @OnClick(R.id.click)
  void onClick() {
    Bitmap lBitmap = ScreenUtil.shotRecyclerView(recyclerview);
    Utils.showDialog(this,lBitmap);
  }
}
