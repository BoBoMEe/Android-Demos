package com.bobomee.blogdemos.recycler.linear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import com.bobomee.blogdemos.recycler.BaseRecycler;
import com.bobomee.blogdemos.recycler.Datas;
import com.bobomee.blogdemos.recycler.adapter.RecyclerStringAdapter;
import com.bobomee.blogdemos.recycler.decoration.MarginDecoration;

/**
 * Created on 16/7/23.下午3:18.
 *
 * @author bobomee.
 * @description:
 */
public class LinearLayoutManagerRecycler extends BaseRecycler {

  private LinearLayoutManager mLinearLayoutManager;
  private RecyclerStringAdapter mRecyclerStringAdapter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initViews();
  }

  @Override public void onChange() {
    if (null != mLinearLayoutManager) {
      int orientation = mLinearLayoutManager.getOrientation();
      mLinearLayoutManager.setOrientation(
          orientation == LinearLayoutManager.VERTICAL ? LinearLayoutManager.HORIZONTAL
              : LinearLayoutManager.VERTICAL);
    }
  }

  private void initViews() {

    mRecyclerView.addItemDecoration(new MarginDecoration(this));
    mRecyclerView.setHasFixedSize(true);
    mLinearLayoutManager = new LinearLayoutManager(this);
    mRecyclerView.setLayoutManager(mLinearLayoutManager);

    mRecyclerStringAdapter = new RecyclerStringAdapter(Datas.getDatas());

    mRecyclerView.setAdapter(mRecyclerStringAdapter);
  }

  @Override public void onAdd() {
    if (null != mRecyclerStringAdapter && null != mRecyclerView) {
      mRecyclerStringAdapter.add();
      mRecyclerView.scrollToPosition(0);
    }
  }
}
