package com.bobomee.blogdemos.recycler.helper;

import android.support.v7.widget.RecyclerView;

/**
 * Created on 16/7/24.下午2:09.
 *
 * @author bobomee.
 * @description:
 */
public class SimpleRvItemHelper implements RvItemHelper {
  @Override public boolean onItemMove(int fromPosition, int toPosition) {
    return false;
  }

  @Override public void onItemDismiss(int position, int direction) {

  }

  @Override public void onItemSelected(RecyclerView.ViewHolder viewHolder, int actionstate) {

  }

  @Override public void onItemClear() {

  }

  @Override public boolean isItemViewSwipeEnabledLeft() {
    return false;
  }

  @Override public boolean isItemViewSwipeEnabledRight() {
    return false;
  }
}
