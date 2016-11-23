package com.bobomee.blogdemos.recycler.helper;

import android.support.v7.widget.RecyclerView;

/**
 * Created on 16/7/24.下午2:06.
 *
 * @author bobomee.
 * @description:
 */
public interface RvItemHelper {

  /**
   * Called when an item has been dragged far enough to trigger a move. This is called every time
   * an item is shifted, and <strong>not</strong> at the end of a "drop" event.<br/>
   * <br/>
   * Implementations should call RecyclerView.Adapter#notifyItemMoved(int, int) after
   * adjusting the underlying data to reflect this move.
   *
   * @param fromPosition The start position of the moved item.
   * @param toPosition Then resolved position of the moved item.
   * @return True if the item was moved to the new adapter position.
   */
  boolean onItemMove(int fromPosition, int toPosition);

  /**
   * Called when an item has been dismissed by a swipe.<br/>
   * <br/>
   * Implementations should call RecyclerView.Adapter#notifyItemRemoved(int) after
   * adjusting the underlying data to reflect this removal.
   *
   * @param position The position of the item dismissed.
   * @param direction the direction
   */
  void onItemDismiss(int position, int direction);

  /**
   * Called when the ItemTouchHelper first registers an item as being moved or swiped.
   * Implementations should update the item view to indicate it's active state.
   *
   * @param actionstate the actionstate
   */
  void onItemSelected(RecyclerView.ViewHolder viewHolder,int actionstate);

  /**
   * Called when the ItemTouchHelper has completed the move or swipe, and the active item
   * state should be cleared.
   */
  void onItemClear();

  /**
   * if item can swipe from left|start
   * @return
   */
  boolean isItemViewSwipeEnabledLeft();

  /**
   * if item can swipe from right|end
   */
  boolean isItemViewSwipeEnabledRight();

}
