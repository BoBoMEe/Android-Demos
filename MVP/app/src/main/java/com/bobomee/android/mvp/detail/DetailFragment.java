/*
 *
 *    Copyright (C) 2016 BoBoMEe(wbwjx115@gmail.com)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.bobomee.android.mvp.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.bobomee.android.mvp.BaseFragment;
import com.bobomee.android.mvp.R;
import com.bobomee.android.mvp.data.Repo;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created on 16/6/11.上午11:12.
 *
 * @author bobomee
 */
public class DetailFragment extends BaseFragment implements DetailContract.View {

  @BindView(R.id.text) TextView text;
  private DetailContract.Presenter presenter;

  public static DetailFragment newInstance(Repo repo) {
    Bundle args = new Bundle();
    args.putSerializable("repo", repo);
    DetailFragment fragment = new DetailFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public View initFragmentView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.detail_frag, container, false);
  }

  @Override public void showDetail(String string) {
    text.setText(string);
  }

  @Override public void setPresenter(@NonNull DetailContract.Presenter presenter) {
    this.presenter = checkNotNull(presenter);
  }

  @Override public void onResume() {
    super.onResume();
    presenter.loadDetail();
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    presenter.start();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    presenter.onDestroy();
  }
}
