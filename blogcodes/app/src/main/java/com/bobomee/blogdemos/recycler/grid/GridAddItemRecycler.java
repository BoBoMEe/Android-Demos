package com.bobomee.blogdemos.recycler.grid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.bobomee.blogdemos.recycler.BaseRecycler;
import com.bobomee.blogdemos.recycler.Datas;
import com.bobomee.blogdemos.recycler.decoration.MarginDecoration;
import com.bobomee.blogdemos.recycler.helper.RvItemTouchHelperCallback;
import com.bobomee.blogdemos.recycler.helper.SimpleRvItemHelper;
import com.bobomee.blogdemos.recycler.adapter.RecyclerStringAdapter;

/**
 * Created on 16/7/23.下午7:15.
 *
 * @author bobomee.
 * @description:
 */
public class GridAddItemRecycler extends BaseRecycler {

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

  private void initViews() {

    mRecyclerView.addItemDecoration(new MarginDecoration(this));
    mRecyclerView.setHasFixedSize(true);
    mGridLayoutManager = new GridLayoutManager(this, 3);
    mRecyclerView.setLayoutManager(mGridLayoutManager);
    mRecyclerStringAdapter = new RecyclerStringAdapter(Datas.getDatas());
    mRecyclerView.setAdapter(mRecyclerStringAdapter);

    /////////////////////////////////remove///////////////////////
    ItemTouchHelper.Callback callback = new RvItemTouchHelperCallback(new SimpleRvItemHelper() {
      @Override public boolean isItemViewSwipeEnabledLeft() {
        return true;
      }

      @Override public boolean isItemViewSwipeEnabledRight() {
        return true;
      }

      @Override public void onItemDismiss(int position, int direction) {
        mRecyclerStringAdapter.removeItem(position);
      }

      @Override public boolean onItemMove(int fromPosition, int toPosition) {
        mRecyclerStringAdapter.swap(fromPosition, toPosition);
        return true;
      }
    });
    ItemTouchHelper helper = new ItemTouchHelper(callback);
    helper.attachToRecyclerView(mRecyclerView);
  }

  @Override public void onAdd() {
    if (null != mRecyclerStringAdapter && null != mRecyclerView)
    mRecyclerStringAdapter.add();
    mRecyclerView.scrollToPosition(0);
  }
}
