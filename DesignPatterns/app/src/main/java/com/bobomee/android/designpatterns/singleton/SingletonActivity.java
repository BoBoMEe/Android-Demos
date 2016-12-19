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

package com.bobomee.android.designpatterns.singleton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Created on 2016/12/19.下午12:17.
 *
 * @author bobomee.
 */

public class SingletonActivity extends BaseActivity {

  @BindView(R.id.btn_click) Button mBtnClick;
  private static final String TAG = "InterpreterActivity";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_click) public void setBtnClick() {
    try {
      SerializedSingleton instanceOne = SerializedSingleton.getInstance();
      ObjectOutput out = new ObjectOutputStream(new FileOutputStream("filename.ser"));
      out.writeObject(instanceOne);
      out.close();

      //deserailize from file to object
      ObjectInput in = new ObjectInputStream(new FileInputStream("filename.ser"));
      SerializedSingleton instanceTwo = (SerializedSingleton) in.readObject();
      in.close();

      System.out.println("instanceOne hashCode=" + instanceOne.hashCode());
      System.out.println("instanceTwo hashCode=" + instanceTwo.hashCode());
    } catch (Exception ignored) {
    }
  }
}
