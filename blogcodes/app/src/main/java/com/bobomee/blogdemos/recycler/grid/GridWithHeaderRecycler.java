package com.bobomee.blogdemos.recycler.grid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.recycler.BaseRecycler;
import com.bobomee.blogdemos.recycler.Datas;
import com.bobomee.blogdemos.recycler.adapter.RecyclerWithHeaderAdapter;
import com.bobomee.blogdemos.recycler.decoration.MarginDecoration;

/**
 * Created on 16/7/23.下午7:15.
 *
 * @author bobomee.
 * @description:
 */
public class GridWithHeaderRecycler extends BaseRecycler {

  private GridLayoutManager mGridLayoutManager;
  private RecyclerWithHeaderAdapter mRecyclerWithHeaderAdapter;

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

  private void initViews() {

    mRecyclerView.addItemDecoration(new MarginDecoration(this));
    mRecyclerView.setHasFixedSize(true);
    mGridLayoutManager = new GridLayoutManager(this, 3);
    mRecyclerView.setLayoutManager(mGridLayoutManager);

    View header = LayoutInflater.from(this).inflate(R.layout.recycler_header, mRecyclerView, false);
    mRecyclerWithHeaderAdapter = new RecyclerWithHeaderAdapter(Datas.getDatas(), header);
    mRecyclerView.setAdapter(mRecyclerWithHeaderAdapter);

    mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        return mRecyclerWithHeaderAdapter.isHeader(position) ? mGridLayoutManager.getSpanCount() : 1;
      }
    });
  }

  @Override public void onAdd() {
    if (null != mRecyclerWithHeaderAdapter && null != mRecyclerView) {
      mRecyclerWithHeaderAdapter.add();
      mRecyclerView.scrollToPosition(1);
    }
  }
}
