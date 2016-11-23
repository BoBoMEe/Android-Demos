package com.bobomee.blogdemos.recycler.staggered;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.bobomee.blogdemos.recycler.BaseRecycler;
import com.bobomee.blogdemos.recycler.Datas;
import com.bobomee.blogdemos.recycler.adapter.RecyclerStaggeredAdapter;
import com.bobomee.blogdemos.recycler.decoration.MarginDecoration;

/**
 * Created on 16/7/23.下午8:12.
 *
 * @author bobomee.
 * @description:
 */
public class StaggeredRecycler extends BaseRecycler {

  private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
  private RecyclerStaggeredAdapter mRecyclerStaggeredAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initViews();
  }

  private void initViews() {
    mRecyclerView.addItemDecoration(new MarginDecoration(this));
    mRecyclerView.setHasFixedSize(true);
    mStaggeredGridLayoutManager =
        new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
    mRecyclerStaggeredAdapter = new RecyclerStaggeredAdapter(Datas.getDatas());
    mRecyclerView.setAdapter(mRecyclerStaggeredAdapter);
  }

  @Override public void onChange() {
    if (null != mStaggeredGridLayoutManager) {
      int orientation = mStaggeredGridLayoutManager.getOrientation();
      mStaggeredGridLayoutManager.setOrientation(
          orientation == StaggeredGridLayoutManager.VERTICAL ? StaggeredGridLayoutManager.HORIZONTAL
              : StaggeredGridLayoutManager.VERTICAL);
    }
  }

  @Override public void onAdd() {
    if (null != mRecyclerStaggeredAdapter && null != mRecyclerView) {
      mRecyclerStaggeredAdapter.add();
      mRecyclerView.scrollToPosition(0);
    }
  }
}
