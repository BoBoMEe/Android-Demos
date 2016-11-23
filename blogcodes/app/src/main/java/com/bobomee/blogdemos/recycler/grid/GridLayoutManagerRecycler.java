package com.bobomee.blogdemos.recycler.grid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import com.bobomee.blogdemos.recycler.BaseRecycler;
import com.bobomee.blogdemos.recycler.Datas;
import com.bobomee.blogdemos.recycler.decoration.MarginDecoration;
import com.bobomee.blogdemos.recycler.adapter.RecyclerStringAdapter;

/**
 * Created on 16/7/23.下午7:15.
 *
 * @author bobomee.
 * @description:
 */
public class GridLayoutManagerRecycler extends BaseRecycler {

  private GridLayoutManager mGridLayoutManager;
  private RecyclerStringAdapter mRecyclerStringAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initViews();
  }

  @Override public void onChange() {

    if (null != mGridLayoutManager) {
      int orientation = mGridLayoutManager.getOrientation();
      mGridLayoutManager.setOrientation(
          orientation == GridLayoutManager.VERTICAL ? GridLayoutManager.HORIZONTAL
              : GridLayoutManager.VERTICAL);
    }
  }

  @Override public void onAdd() {
    if (null != mRecyclerStringAdapter&& null != mRecyclerStringAdapter){
      mRecyclerStringAdapter.add();
      mRecyclerView.scrollToPosition(0);
    }
  }

  private void initViews() {

    mRecyclerView.addItemDecoration(new MarginDecoration(this));
    mRecyclerView.setHasFixedSize(true);
    mGridLayoutManager = new GridLayoutManager(this, 3);
    mRecyclerView.setLayoutManager(mGridLayoutManager);
    mRecyclerStringAdapter = new RecyclerStringAdapter(Datas.getDatas());
    mRecyclerView.setAdapter(mRecyclerStringAdapter);
  }
}
