/*
 *  Copyright (C) 2016 BoBoMEe
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

package com.bobomee.blogdemos.activityfragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.activityfragment.functions.Function;
import com.bobomee.blogdemos.activityfragment.observable.ObservableManager;
import java.util.Arrays;

public class ActivityFragment extends AppCompatActivity implements Function {

  public static final String FUNCTION_WITH_PARAM_AND_RESULT = "FUNCTION_WITH_PARAM_AND_RESULT_ACTIVITY";
  @BindView(R.id.activity_result) TextView activityResult;
  @BindView(R.id.activity_fragment) Button activityFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    ObservableManager.newInstance().registerObserver(FUNCTION_WITH_PARAM_AND_RESULT, this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    ObservableManager.newInstance().removeObserver(this);
  }

  @Override public Object function(Object[] data) {
    String text = activityResult.getText().toString() + "\n\n\n";

    activityResult.setText(text + "fragment调用我成功,获取到参数 :" + Arrays.asList(data));

    return "我是activity的返回结果";
  }

  @OnClick(R.id.activity_fragment) public void setActivityFragment() {
    Object notify = ObservableManager.newInstance()
        .notify(MainFragment.FUNCTION_WITH_PARAM_AND_RESULT, "我是activity传到fragment的参数1", activityFragment,
            activityResult);
    String text = activityResult.getText().toString() + "\n\n\n";
    activityResult.setText(text + "调用fragment成功,获取返回值 : " + notify.toString());
  }
}
