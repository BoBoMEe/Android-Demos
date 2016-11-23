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

package com.bobomee.android.mvp.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.OnClick;
import com.bobomee.android.common.util.ToastUtil;
import com.bobomee.android.mvp.BaseFragment;
import com.bobomee.android.mvp.R;
import com.bobomee.android.mvp.list.ReposActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created on 16/6/10.下午5:00.
 *
 * @author bobomee
 */
public class SplashFragment extends BaseFragment implements SplashContract.View {

  @BindView(R.id.user) EditText user;
  @BindView(R.id.progress) ProgressBar progress;
  private SplashContract.Presenter presenter;

  public SplashFragment() {
  }

  public static SplashFragment newInstance() {
    Bundle args = new Bundle();
    SplashFragment fragment = new SplashFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @OnClick(R.id.start) public void start() {
    presenter.repos(user.getText().toString().trim());
  }

  @Override public View initFragmentView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.splash_frag, container, false);
  }

  @Override public void setLoadingIndicator(boolean active) {
    progress.setVisibility(active ? View.VISIBLE : View.GONE);
  }

  @Override public void setUsernameError() {
    ToastUtil.show(activity, "请求出错");
  }

  @Override public void showUserRepositories() {
    ReposActivity.start(activity, user.getText().toString().trim());
  }

  @Override public void setPresenter(@NonNull SplashContract.Presenter presenter) {
    this.presenter = checkNotNull(presenter);
  }

  @Override public void onResume() {
    super.onResume();
    presenter.start();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    presenter.onDestroy();
  }
}
