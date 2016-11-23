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

package com.bobomee.android.mvp.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import com.bobomee.android.mvp.BaseFragment;
import com.bobomee.android.mvp.R;
import com.bobomee.android.mvp.data.Repo;
import com.bobomee.android.mvp.detail.DetailActivity;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created on 16/6/11.上午11:10.
 *
 * @author bobomee
 */
public class ReposFragment extends BaseFragment implements ListContract.View {

  @BindView(R.id.filteringLabel) TextView filteringLabel;
  @BindView(R.id.repos_list) ListView reposList;
  @BindView(R.id.progress) ProgressBar progress;
  @BindView(R.id.empty) TextView empty;
  private ListContract.Presenter presenter;
  private QuickAdapter<Repo> quickAdapter;

  public static ReposFragment newInstance() {
    Bundle args = new Bundle();
    ReposFragment fragment = new ReposFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onResume() {
    super.onResume();
    presenter.findRepos();
  }

  @Override public View initFragmentView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.list_frag, container, false);
  }

  @Override public void setLoadingIndicator(boolean active) {
    progress.setVisibility(active ? View.VISIBLE : View.GONE);
  }

  @Override public void showList(List<Repo> repos) {
    filteringLabel.setText(repos.get(0).owner.login);

    quickAdapter.replaceAll(repos);
  }

  @Override public void showEmpty() {
    empty.setVisibility(View.VISIBLE);
  }

  @Override public void toDetail(Repo repo) {
    DetailActivity.actionStart(activity, repo);
  }

  @Override public void setPresenter(@NonNull ListContract.Presenter presenter) {
    this.presenter = checkNotNull(presenter);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    presenter.start();
    setHasOptionsMenu(true);
    reposList.setAdapter(quickAdapter = new QuickAdapter<Repo>(activity, R.layout.list_item) {
      @Override protected void convert(final BaseAdapterHelper helper, Repo item) {
        helper.setText(R.id.list_item, item.name);

        helper.getView().setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            presenter.openRepo(helper.getPosition());
          }
        });
      }
    });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    presenter.onDestroy();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_refresh:
        presenter.refresh();
        break;
      case R.id.menu_refreshlocal:
        presenter.removedatabse();
        break;
    }
    return true;
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.list_menu, menu);
  }
}
