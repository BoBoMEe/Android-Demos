package com.bobomee.blogdemos.recycler.helper;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created on 16/7/24.下午1:46.
 *
 * @author bobomee.
 * @description:
 */
public abstract class RvItemClickListener implements RecyclerView.OnItemTouchListener {

  private GestureDetectorCompat mGestureDetector;
  private RecyclerView recyclerView;

  /**
   * Called when an item is clicked
   */
  public abstract void onItemClick(RecyclerView.ViewHolder vh);

  /**
   * Called when an item is longClicked
   */
  public abstract void onItemLongClick(RecyclerView.ViewHolder vh);

  public RvItemClickListener(RecyclerView recyclerView) {
    this.recyclerView = recyclerView;
    mGestureDetector =
        new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
  }

  @Override public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
    mGestureDetector.onTouchEvent(e);
    return false;
  }

  @Override public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    mGestureDetector.onTouchEvent(e);
  }

  @Override public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
  }

  private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
    @Override public boolean onSingleTapUp(MotionEvent e) {
      View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
      if (child != null) {
        RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
        onItemClick(vh);
      }
      return true;
    }

    @Override public void onLongPress(MotionEvent e) {
      View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
      if (child != null) {
        RecyclerView.ViewHolder vh = recyclerView.getChildViewHolder(child);
        onItemLongClick(vh);
      }
    }
  }
}
