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

package com.bobomee.android.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bobomee.android.common.util.ToastUtil;
import com.bobomee.android.common.util.UIUtil;
import com.bobomee.android.http.util.HttpNetUtil;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * fragment基类
 */
public abstract class BaseFragment extends Fragment implements HttpNetUtil.Networkreceiver {
  public BaseActivity activity;
  public View fragmentRootView;

  CompositeSubscription mCompositeSubscription;
  private Unbinder unbinder;

  public void addSubscription(Subscription s) {
    if (this.mCompositeSubscription == null) {
      this.mCompositeSubscription = new CompositeSubscription();
    }
    if (null != s) {
      this.mCompositeSubscription.add(s);
    }
  }

  public void unsubscribe() {
    if (this.mCompositeSubscription != null) {
      this.mCompositeSubscription.clear();
    }
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activity = BaseActivity.class.cast(getActivity());

    HttpNetUtil.INSTANCE.addNetWorkListener(this);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (null == fragmentRootView) {
      fragmentRootView = initFragmentView(inflater, container, savedInstanceState);
    }
    if (null != fragmentRootView) {
      ViewGroup parent = (ViewGroup) fragmentRootView.getParent();
      if (null != parent) parent.removeAllViews();
    }

    unbinder = ButterKnife.bind(this, fragmentRootView);

    return fragmentRootView;
  }

  public abstract View initFragmentView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState);

  @Override public void onDestroyView() {
    super.onDestroyView();

    if (null != unbinder) unbinder.unbind();

    unsubscribe();

    UIUtil.getMainThreadHandler().removeCallbacksAndMessages(null);
  }

  @Override public void onConnected(boolean collect) {
    if (!collect) ToastUtil.show(activity, "网络连接失败");
  }
}
