package com.bobomee.blogdemos.recycler.staggered;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import com.bobomee.android.common.util.ToastUtil;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.recycler.BaseRecycler;
import com.bobomee.blogdemos.recycler.Datas;
import com.bobomee.blogdemos.recycler.adapter.RecyclerStaggeredAdapter;
import com.bobomee.blogdemos.recycler.decoration.MarginDecoration;
import com.bobomee.blogdemos.recycler.helper.RvItemClickListener;
import com.bobomee.blogdemos.recycler.helper.RvItemTouchHelperCallback;
import com.bobomee.blogdemos.recycler.helper.SimpleRvItemHelper;

/**
 * Created on 16/7/23.下午8:12.
 *
 * @author bobomee.
 * @description:
 */
public class StaggeredSelectRecycler extends BaseRecycler {

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

    // /////////////select/////////////////////////////////
    ItemTouchHelper.Callback callback = new RvItemTouchHelperCallback(new SimpleRvItemHelper() {

      @Override public void onItemSelected(RecyclerView.ViewHolder viewHolder, int actionstate) {
        if (actionMode == null) {
          actionMode = startSupportActionMode(actionModeCallback);
        }
        int position = viewHolder.getAdapterPosition();
        toggleSelection(position);
      }
    });
    ItemTouchHelper helper = new ItemTouchHelper(callback);
    helper.attachToRecyclerView(mRecyclerView);

    mRecyclerView.addOnItemTouchListener(new RvItemClickListener(mRecyclerView) {
      @Override public void onItemClick(RecyclerView.ViewHolder vh) {
        ToastUtil.show(StaggeredSelectRecycler.this, "onItemClick");
        int position = vh.getAdapterPosition();
        if (actionMode != null) {
          toggleSelection(position);
        } else {
          mRecyclerStaggeredAdapter.removeItem(position);
        }
      }

      @Override public void onItemLongClick(RecyclerView.ViewHolder vh) {
        ToastUtil.show(StaggeredSelectRecycler.this, "onItemLongClick");
      }
    });
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

  /////////////////////////////////
  private ActionModeCallback actionModeCallback = new ActionModeCallback();
  private ActionMode actionMode;

  /**
   * Toggle the selection state of an item.
   *
   * If the item was the last one in the selection and is unselected, the selection is stopped.
   * Note that the selection must already be started (actionMode must not be null).
   *
   * @param position Position of the item to toggle the selection state
   */
  private void toggleSelection(int position) {
    mRecyclerStaggeredAdapter.toggleSelection(position);
    int count = mRecyclerStaggeredAdapter.getSelectedItemCount();

    if (count == 0) {
      actionMode.finish();
    } else {
      actionMode.setTitle(String.valueOf(count));
      actionMode.invalidate();
    }
  }

  private class ActionModeCallback implements ActionMode.Callback {

    @Override public boolean onCreateActionMode(ActionMode mode, Menu menu) {
      mode.getMenuInflater().inflate(R.menu.recyclerview_delete, menu);
      return true;
    }

    @Override public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
      return false;
    }

    @Override public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
      switch (item.getItemId()) {
        case R.id.menu_remove:
          mRecyclerStaggeredAdapter.removeItems(mRecyclerStaggeredAdapter.getSelectedItems());
          mode.finish();
          return true;

        default:
          return false;
      }
    }

    @Override public void onDestroyActionMode(ActionMode mode) {
      mRecyclerStaggeredAdapter.clearSelection();
      actionMode = null;
    }
  }
}
