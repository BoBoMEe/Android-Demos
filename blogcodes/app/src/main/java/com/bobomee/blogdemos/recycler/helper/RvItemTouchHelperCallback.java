package com.bobomee.blogdemos.recycler.helper;

import android.graphics.Canvas;
import android.os.Build;
import android.support.v7.widget.RecyclerView;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_IDLE;
import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;
import static android.support.v7.widget.helper.ItemTouchHelper.Callback;
import static android.support.v7.widget.helper.ItemTouchHelper.DOWN;
import static android.support.v7.widget.helper.ItemTouchHelper.END;
import static android.support.v7.widget.helper.ItemTouchHelper.START;
import static android.support.v7.widget.helper.ItemTouchHelper.UP;

public class RvItemTouchHelperCallback extends Callback {
  private final RvItemHelper mRvItemHelper;
  private final boolean isItemViewSwipeEnabledLeft;
  private final boolean isItemViewSwipeEnabledRight;

  public RvItemTouchHelperCallback(RvItemHelper rvItemHelper) {
    this.mRvItemHelper = rvItemHelper;
    this.isItemViewSwipeEnabledLeft = rvItemHelper.isItemViewSwipeEnabledLeft();
    this.isItemViewSwipeEnabledRight = rvItemHelper.isItemViewSwipeEnabledRight();
  }

  @Override public boolean isItemViewSwipeEnabled() {
    return (isItemViewSwipeEnabledLeft || isItemViewSwipeEnabledRight);
  }

  @Override
  public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    final int dragFlags = UP | DOWN | START | END;
    final int swipeFlags;
    if (isItemViewSwipeEnabledLeft && isItemViewSwipeEnabledRight) {
      swipeFlags = START | END;
    } else if (isItemViewSwipeEnabledRight) {
      swipeFlags = START;
    } else {
      swipeFlags = END;
    }

    return Callback.makeMovementFlags(dragFlags, swipeFlags);
  }

  @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source,
      RecyclerView.ViewHolder target) {
    // Notify the adapter of the move
    return mRvItemHelper.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
  }

  @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    mRvItemHelper.onItemDismiss(viewHolder.getAdapterPosition(), direction);
  }

  @Override
  public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
      float dX, float dY, int actionState, boolean isCurrentlyActive) {
    if (actionState == ACTION_STATE_SWIPE) {
      // Fade out the view as it is swiped out of the parent's bounds
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        viewHolder.itemView.setTranslationX(dX);
      }
    } else {
      super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
  }

  @Override public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
    // We only want the active item to change
    if (actionState != ACTION_STATE_IDLE) {
      // Let the view holder know that this item is being moved or dragged
      mRvItemHelper.onItemSelected(viewHolder, actionState);
    }

    super.onSelectedChanged(viewHolder, actionState);
  }

  @Override public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current,
      RecyclerView.ViewHolder target) {
    return current.getItemViewType() == target.getItemViewType();
  }

  @Override public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    super.clearView(recyclerView, viewHolder);

    // Tell the view holder it's time to restore the idle state
    mRvItemHelper.onItemClear();
  }
}
