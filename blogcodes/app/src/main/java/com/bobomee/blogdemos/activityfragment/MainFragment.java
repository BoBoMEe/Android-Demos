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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.activityfragment.functions.Function;
import com.bobomee.blogdemos.activityfragment.observable.ObservableManager;
import java.util.Arrays;

/**
 * Created on 16/6/3.下午10:27.
 *
 * @author bobomee
 */
public class MainFragment extends Fragment implements Function {
  public static final String FUNCTION_WITH_PARAM_AND_RESULT = "FUNCTION_WITH_PARAM_AND_RESULT_FRAGMENT";

  @BindView(R.id.fragment_activity) Button fragmentActivity;
  @BindView(R.id.fragment_result) TextView fragmentResult;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ObservableManager.newInstance().registerObserver(FUNCTION_WITH_PARAM_AND_RESULT, this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    ObservableManager.newInstance().removeObserver(this);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @OnClick(R.id.fragment_activity) public void setFragmentActivity() {

    Object notify = ObservableManager.newInstance()
        .notify(ActivityFragment.FUNCTION_WITH_PARAM_AND_RESULT, "我是fragment传到activity的参数1", fragmentActivity,
            fragmentResult);
    String text = fragmentResult.getText().toString() + "\n\n\n";
    fragmentResult.setText(text + "调用activity成功,获取返回值 : " + notify.toString());
  }

  @Override public Object function(Object[] data) {
    String text = fragmentResult.getText().toString() + "\n\n\n";

    fragmentResult.setText(text + "activity调用我成功,获取到参数 :" + Arrays.asList(data));

    return "我是fragment的返回结果";
  }
}
